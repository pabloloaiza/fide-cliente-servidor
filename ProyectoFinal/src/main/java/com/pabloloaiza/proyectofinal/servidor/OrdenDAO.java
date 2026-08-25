package com.pabloloaiza.proyectofinal.servidor;

import com.pabloloaiza.proyectofinal.modelo.LineaOrden;
import com.pabloloaiza.proyectofinal.modelo.Orden;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Pablo Loaiza
 */
public class OrdenDAO {

    // Inserto la cabecera y el detalle dentro de una transaccion: si algo falla
    // a mitad de camino se deshace todo y no queda una orden sin productos.
    public Orden insertar(Orden orden) throws SQLException {
        Connection conexion = null;
        try {
            conexion = ConexionBD.conectar();
            conexion.setAutoCommit(false);

            String sqlOrden = "INSERT INTO ordenes (id_cliente, nombre_cliente, estado, fecha, total) "
                    + "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement sentencia =
                    conexion.prepareStatement(sqlOrden, Statement.RETURN_GENERATED_KEYS)) {
                sentencia.setInt(1, orden.getIdCliente());
                sentencia.setString(2, orden.getNombreCliente());
                sentencia.setString(3, Orden.PENDIENTE);
                sentencia.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                sentencia.setDouble(5, orden.calcularTotal());
                sentencia.executeUpdate();
                try (ResultSet claves = sentencia.getGeneratedKeys()) {
                    if (claves.next()) {
                        orden.setId(claves.getInt(1));
                    }
                }
            }

            String sqlDetalle = "INSERT INTO orden_detalle "
                    + "(id_orden, id_producto, nombre_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement sentencia = conexion.prepareStatement(sqlDetalle)) {
                for (LineaOrden linea : orden.getLineas()) {
                    sentencia.setInt(1, orden.getId());
                    sentencia.setInt(2, linea.getIdProducto());
                    sentencia.setString(3, linea.getNombreProducto());
                    sentencia.setInt(4, linea.getCantidad());
                    sentencia.setDouble(5, linea.getPrecioUnitario());
                    sentencia.addBatch();
                }
                sentencia.executeBatch();
            }

            conexion.commit();
            orden.setTotal(orden.calcularTotal());
            orden.setEstado(Orden.PENDIENTE);
            return orden;
        } catch (SQLException ex) {
            if (conexion != null) {
                conexion.rollback();
            }
            throw ex;
        } finally {
            if (conexion != null) {
                conexion.close();
            }
        }
    }

    public ArrayList<Orden> listarPendientes() throws SQLException {
        return listarConFiltro("WHERE estado = '" + Orden.PENDIENTE + "'", 0);
    }

    public ArrayList<Orden> listarPorCliente(int idCliente) throws SQLException {
        return listarConFiltro("WHERE id_cliente = ?", idCliente);
    }

    public Orden buscarPorId(int idOrden) throws SQLException {
        ArrayList<Orden> resultado = listarConFiltro("WHERE id = ?", idOrden);
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    // Un solo metodo armado con el WHERE que le pasen, para no repetir el mismo mapeo 3 veces.
    // Si el filtro trae un ? le paso el parametro; si no, mando 0 y no se usa.
    private ArrayList<Orden> listarConFiltro(String filtro, int parametro) throws SQLException {
        ArrayList<Orden> ordenes = new ArrayList<>();
        String sql = "SELECT * FROM ordenes " + filtro + " ORDER BY id";
        try (Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            if (filtro.contains("?")) {
                sentencia.setInt(1, parametro);
            }
            try (ResultSet fila = sentencia.executeQuery()) {
                while (fila.next()) {
                    ordenes.add(new Orden(fila.getInt("id"), fila.getInt("id_cliente"),
                            fila.getString("nombre_cliente"), fila.getString("estado"),
                            String.valueOf(fila.getTimestamp("fecha")), fila.getDouble("total"),
                            new ArrayList<>()));
                }
            }
        }
        for (Orden orden : ordenes) {
            orden.getLineas().addAll(cargarDetalle(orden.getId()));
        }
        return ordenes;
    }

    private ArrayList<LineaOrden> cargarDetalle(int idOrden) throws SQLException {
        ArrayList<LineaOrden> lineas = new ArrayList<>();
        try (Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(
                        "SELECT * FROM orden_detalle WHERE id_orden = ? ORDER BY id")) {
            sentencia.setInt(1, idOrden);
            try (ResultSet fila = sentencia.executeQuery()) {
                while (fila.next()) {
                    lineas.add(new LineaOrden(fila.getInt("id_producto"), fila.getString("nombre_producto"),
                            fila.getInt("cantidad"), fila.getDouble("precio_unitario")));
                }
            }
        }
        return lineas;
    }

    // Caso de uso del cocinero. Solo cambia el estado si todavia esta PENDIENTE,
    // asi dos cocineros no completan la misma orden al mismo tiempo.
    public boolean completar(int idOrden) throws SQLException {
        String sql = "UPDATE ordenes SET estado = ? WHERE id = ? AND estado = ?";
        try (Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, Orden.COMPLETADA);
            sentencia.setInt(2, idOrden);
            sentencia.setString(3, Orden.PENDIENTE);
            return sentencia.executeUpdate() == 1;
        }
    }
}
