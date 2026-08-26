package com.pabloloaiza.proyectofinal.modelo;

import com.pabloloaiza.proyectofinal.modelo.Orden;
import com.pabloloaiza.proyectofinal.modelo.Producto;
import com.pabloloaiza.proyectofinal.modelo.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author Pablo Loaiza
 */
// CLASE EN DESUSO. Era la persistencia con archivos .extension de la entrega
// anterior; ahora todo se guarda en MySQL a traves de los DAO del servidor.
// La dejo solo como referencia de la version con serializacion.
@Deprecated
public class GestorArchivos {

    public static final String ARCHIVO_USUARIOS = "Usuarios.extension";
    public static final String ARCHIVO_PRODUCTOS = "Productos.extension";
    public static final String ARCHIVO_ORDENES = "Ordenes.extension";

    @SuppressWarnings("unchecked")
    private static <T> ArrayList<T> leerLista(String ruta) {
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivo))) {
            return (ArrayList<T>) entrada.readObject();
        } catch (Exception ex) {
            System.out.println("Error leyendo " + ruta + ": " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    private static <T> void guardarLista(String ruta, ArrayList<T> lista) {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ruta))) {
            salida.writeObject(lista);
        } catch (Exception ex) {
            System.out.println("Error guardando " + ruta + ": " + ex.getMessage());
        }
    }

    public static ArrayList<Usuario> cargarUsuarios() {
        return leerLista(ARCHIVO_USUARIOS);
    }

    public static void guardarUsuarios(ArrayList<Usuario> usuarios) {
        guardarLista(ARCHIVO_USUARIOS, usuarios);
    }

    public static ArrayList<Producto> cargarProductos() {
        return leerLista(ARCHIVO_PRODUCTOS);
    }

    public static void guardarProductos(ArrayList<Producto> productos) {
        guardarLista(ARCHIVO_PRODUCTOS, productos);
    }

    public static ArrayList<Orden> cargarOrdenes() {
        return leerLista(ARCHIVO_ORDENES);
    }

    public static void guardarOrdenes(ArrayList<Orden> ordenes) {
        guardarLista(ARCHIVO_ORDENES, ordenes);
    }
}
