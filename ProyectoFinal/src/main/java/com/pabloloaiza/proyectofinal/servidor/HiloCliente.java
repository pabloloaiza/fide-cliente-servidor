package com.pabloloaiza.proyectofinal.servidor;

import com.pabloloaiza.proyectofinal.modelo.Factura;
import com.pabloloaiza.proyectofinal.modelo.LineaOrden;
import com.pabloloaiza.proyectofinal.modelo.Orden;
import com.pabloloaiza.proyectofinal.modelo.Producto;
import com.pabloloaiza.proyectofinal.modelo.Usuario;
import com.pabloloaiza.proyectofinal.red.Peticion;
import com.pabloloaiza.proyectofinal.red.Respuesta;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

/**
 *
 * @author Pablo Loaiza
 */
// Atiende a UN cliente durante toda su sesion. Como implementa Runnable, el
// Servidor lo mete en un Thread y puede atender a muchos clientes en paralelo.
public class HiloCliente implements Runnable {

    private final Socket socket;
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final OrdenDAO ordenDAO = new OrdenDAO();
    private final FacturaDAO facturaDAO = new FacturaDAO();

    private Usuario usuarioSesion;

    public HiloCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        String nombreHilo = Thread.currentThread().getName();
        System.out.println("[" + nombreHilo + "] conectado desde " + socket.getInetAddress());

        // El de salida se crea primero para que el otro lado no se quede esperando la cabecera
        try (ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())) {

            boolean seguir = true;
            while (seguir) {
                Peticion peticion = (Peticion) entrada.readObject();
                if (peticion.getAccion() == com.pabloloaiza.proyectofinal.red.Accion.CERRAR_SESION) {
                    seguir = false;
                    salida.writeObject(Respuesta.ok("Sesion cerrada."));
                } else {
                    Respuesta respuesta = procesar(peticion);
                    salida.writeObject(respuesta);
                }
                salida.flush();
                // reset evita que ObjectOutputStream reenvie objetos viejos cacheados
                salida.reset();
            }
        } catch (Exception ex) {
            System.out.println("[" + nombreHilo + "] termino: " + ex.getMessage());
        } finally {
            Servidor.registrarDesconexion();
            System.out.println("[" + nombreHilo + "] desconectado. Activos: " + Servidor.getClientesConectados());
        }
    }

    // Aqui decido que DAO llamar segun la accion que pidio el cliente
    private Respuesta procesar(Peticion peticion) {
        try {
            switch (peticion.getAccion()) {
                case INICIAR_SESION:
                    return iniciarSesion(peticion);
                case REGISTRAR_USUARIO:
                    return registrarUsuario(peticion);
                case LISTAR_PRODUCTOS:
                    return Respuesta.ok("", productoDAO.listar(false));
                case LISTAR_PRODUCTOS_DISPONIBLES:
                    return Respuesta.ok("", productoDAO.listar(true));
                case LISTAR_ARTICULOS:
                    return Respuesta.ok("", productoDAO.listarArticulos());
                case CREAR_PRODUCTO:
                    productoDAO.insertar((Producto) peticion.objetoEn(0));
                    return Respuesta.ok("Producto guardado.");
                case EDITAR_PRODUCTO:
                    productoDAO.actualizar((Producto) peticion.objetoEn(0));
                    return Respuesta.ok("Producto actualizado.");
                case CAMBIAR_DISPONIBILIDAD:
                    productoDAO.cambiarDisponibilidad(peticion.enteroEn(0), peticion.booleanoEn(1));
                    return Respuesta.ok(peticion.booleanoEn(1) ? "Producto disponible." : "Producto marcado como agotado.");
                case CREAR_ORDEN:
                    return crearOrden(peticion);
                case LISTAR_ORDENES_PENDIENTES:
                    return Respuesta.ok("", ordenDAO.listarPendientes());
                case COMPLETAR_ORDEN:
                    boolean completada = ordenDAO.completar(peticion.enteroEn(0));
                    return completada
                            ? Respuesta.ok("Orden completada.")
                            : Respuesta.error("Esa orden ya fue completada por otro cocinero.");
                case LISTAR_ORDENES_CLIENTE:
                    return Respuesta.ok("", ordenDAO.listarPorCliente(peticion.enteroEn(0)));
                case GENERAR_FACTURA:
                    return generarFactura(peticion);
                default:
                    return Respuesta.error("Accion no reconocida.");
            }
        } catch (SQLException ex) {
            return Respuesta.error("Error de base de datos: " + ex.getMessage());
        } catch (RuntimeException ex) {
            return Respuesta.error("Datos invalidos: " + ex.getMessage());
        }
    }

    private Respuesta iniciarSesion(Peticion peticion) throws SQLException {
        Usuario usuario = usuarioDAO.buscarPorCredenciales(peticion.textoEn(0), peticion.textoEn(1));
        if (usuario == null) {
            return Respuesta.error("Usuario o contrasena incorrectos.");
        }
        usuarioSesion = usuario;
        System.out.println("[" + Thread.currentThread().getName() + "] sesion de " + usuario.getNombre());
        return Respuesta.ok("Bienvenido, " + usuario.getNombre(), usuario);
    }

    private Respuesta registrarUsuario(Peticion peticion) throws SQLException {
        Usuario nuevo = (Usuario) peticion.objetoEn(0);
        if (usuarioDAO.existeNombre(nuevo.getNombre())) {
            return Respuesta.error("El usuario \"" + nuevo.getNombre() + "\" ya existe.");
        }
        usuarioSesion = usuarioDAO.insertar(nuevo);
        return Respuesta.ok("Usuario creado correctamente.", usuarioSesion);
    }

    // Antes de guardar reviso que ningun producto se haya agotado mientras el
    // cliente armaba su pedido. Esto es importante porque otro cajero pudo
    // marcarlo agotado en paralelo desde otra conexion.
    private Respuesta crearOrden(Peticion peticion) throws SQLException {
        Orden orden = (Orden) peticion.objetoEn(0);
        if (orden.getLineas().isEmpty()) {
            return Respuesta.error("La orden no tiene productos.");
        }
        for (LineaOrden linea : orden.getLineas()) {
            String agotado = productoDAO.nombreSiNoEstaDisponible(linea.getIdProducto());
            if (agotado != null) {
                return Respuesta.error("No se puede ordenar: \"" + agotado + "\" no esta disponible.");
            }
        }
        Orden guardada = ordenDAO.insertar(orden);
        return Respuesta.ok("Orden #" + guardada.getId() + " registrada.", guardada);
    }

    private Respuesta generarFactura(Peticion peticion) throws SQLException {
        Orden orden = ordenDAO.buscarPorId(peticion.enteroEn(0));
        if (orden == null) {
            return Respuesta.error("La orden no existe.");
        }
        Factura existente = facturaDAO.buscarPorOrden(orden);
        if (existente != null) {
            return Respuesta.ok("Esta orden ya tenia factura.", existente);
        }
        return Respuesta.ok("Factura generada.", facturaDAO.generar(orden));
    }
}
