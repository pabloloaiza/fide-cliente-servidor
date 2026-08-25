package com.pabloloaiza.proyectofinal.servidor;

import com.pabloloaiza.proyectofinal.modelo.Articulo;
import com.pabloloaiza.proyectofinal.modelo.Combo;
import com.pabloloaiza.proyectofinal.modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Pablo Loaiza
 */
public class ProductoDAO {

    // soloDisponibles = true lo usa el cliente, para no ver productos agotados
    public ArrayList<Producto> listar(boolean soloDisponibles) throws SQLException {
        ArrayList<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos" + (soloDisponibles ? " WHERE disponible = TRUE" : "") + " ORDER BY id";
        try (Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet fila = sentencia.executeQuery()) {
            while (fila.next()) {
                if ("COMBO".equals(fila.getString("tipo"))) {
                    Combo combo = new Combo(fila.getInt("id"), fila.getString("nombre"),
                            fila.getDouble("precio"), fila.getBoolean("disponible"),
                            new ArrayList<>(), new ArrayList<>());
                    productos.add(combo);
                } else {
                    productos.add(new Articulo(fila.getInt("id"), fila.getString("nombre"),
                            fila.getDouble("precio"), fila.getString("descripcion"),
                            fila.getBoolean("disponible")));
                }
            }
        }
        // Los combos necesitan una segunda consulta para saber que articulos traen
        for (Producto producto : productos) {
            if (producto instanceof Combo combo) {
                cargarArticulosDelCombo(combo);
            }
        }
        return productos;
    }

    public ArrayList<Articulo> listarArticulos() throws SQLException {
        ArrayList<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE tipo = 'ARTICULO' ORDER BY nombre";
        try (Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet fila = sentencia.executeQuery()) {
            while (fila.next()) {
                articulos.add(new Articulo(fila.getInt("id"), fila.getString("nombre"),
                        fila.getDouble("precio"), fila.getString("descripcion"),
                        fila.getBoolean("disponible")));
            }
        }
        return articulos;
    }

    private void cargarArticulosDelCombo(Combo combo) throws SQLException {
        String sql = "SELECT p.id, p.nombre FROM combo_articulos ca "
                + "JOIN productos p ON p.id = ca.id_articulo WHERE ca.id_combo = ?";
        try (Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setInt(1, combo.getId());
            try (ResultSet fila = sentencia.executeQuery()) {
                ArrayList<Integer> ids = new ArrayList<>();
                ArrayList<String> nombres = new ArrayList<>();
                while (fila.next()) {
                    ids.add(fila.getInt("id"));
                    nombres.add(fila.getString("nombre"));
                }
                combo.setIdsArticulos(ids);
                combo.setNombresArticulos(nombres);
            }
        }
    }

    public Producto insertar(Producto producto) throws SQLException {
        String sql = "INSERT INTO productos (nombre, precio, tipo, descripcion, disponible) VALUES (?, ?, ?, ?, ?)";
        try (Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            sentencia.setString(1, producto.getNombre());
            sentencia.setDouble(2, producto.getPrecio());
            sentencia.setString(3, producto.getTipo());
            sentencia.setString(4, producto instanceof Articulo articulo ? articulo.getDescripcion() : null);
            sentencia.setBoolean(5, producto.isDisponible());
            sentencia.executeUpdate();
            try (ResultSet claves = sentencia.getGeneratedKeys()) {
                if (claves.next()) {
                    producto.setId(claves.getInt(1));
                }
            }
        }
        if (producto instanceof Combo combo) {
            guardarArticulosDelCombo(combo);
        }
        return producto;
    }

    // Caso de uso: el cajero edita un producto ya creado para mantener el menu al dia
    public void actualizar(Producto producto) throws SQLException {
        String sql = "UPDATE productos SET nombre = ?, precio = ?, descripcion = ?, disponible = ? WHERE id = ?";
        try (Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, producto.getNombre());
            sentencia.setDouble(2, producto.getPrecio());
            sentencia.setString(3, producto instanceof Articulo articulo ? articulo.getDescripcion() : null);
            sentencia.setBoolean(4, producto.isDisponible());
            sentencia.setInt(5, producto.getId());
            sentencia.executeUpdate();
        }
        if (producto instanceof Combo combo) {
            borrarArticulosDelCombo(combo.getId());
            guardarArticulosDelCombo(combo);
        }
    }

    // Caso de uso: marcar producto como agotado / disponible de nuevo
    public void cambiarDisponibilidad(int idProducto, boolean disponible) throws SQLException {
        String sql = "UPDATE productos SET disponible = ? WHERE id = ?";
        try (Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setBoolean(1, disponible);
            sentencia.setInt(2, idProducto);
            sentencia.executeUpdate();
        }
    }

    // Verificacion que hace el servidor antes de aceptar una orden
    public String nombreSiNoEstaDisponible(int idProducto) throws SQLException {
        String sql = "SELECT nombre, disponible FROM productos WHERE id = ?";
        try (Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setInt(1, idProducto);
            try (ResultSet fila = sentencia.executeQuery()) {
                if (!fila.next()) {
                    return "producto inexistente";
                }
                return fila.getBoolean("disponible") ? null : fila.getString("nombre");
            }
        }
    }

    private void guardarArticulosDelCombo(Combo combo) throws SQLException {
        String sql = "INSERT INTO combo_articulos (id_combo, id_articulo) VALUES (?, ?)";
        try (Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            for (Integer idArticulo : combo.getIdsArticulos()) {
                sentencia.setInt(1, combo.getId());
                sentencia.setInt(2, idArticulo);
                sentencia.addBatch();
            }
            sentencia.executeBatch();
        }
    }

    private void borrarArticulosDelCombo(int idCombo) throws SQLException {
        try (Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia =
                        conexion.prepareStatement("DELETE FROM combo_articulos WHERE id_combo = ?")) {
            sentencia.setInt(1, idCombo);
            sentencia.executeUpdate();
        }
    }
}
