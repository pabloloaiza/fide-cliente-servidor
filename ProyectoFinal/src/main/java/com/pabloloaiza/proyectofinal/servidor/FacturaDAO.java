package com.pabloloaiza.proyectofinal.servidor;

import com.pabloloaiza.proyectofinal.modelo.Factura;
import com.pabloloaiza.proyectofinal.modelo.Orden;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 *
 * @author Pablo Loaiza
 */
public class FacturaDAO {

    // Devuelve la factura ya existente de una orden, o null si aun no se genero.
    // Asi el cliente no puede facturar dos veces la misma orden.
    public Factura buscarPorOrden(Orden orden) throws SQLException {
        String sql = "SELECT * FROM facturas WHERE id_orden = ?";
        try (Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setInt(1, orden.getId());
            try (ResultSet fila = sentencia.executeQuery()) {
                if (!fila.next()) {
                    return null;
                }
                return new Factura(fila.getInt("id"), orden.getId(), fila.getString("nombre_cliente"),
                        fila.getDouble("subtotal"), fila.getDouble("impuesto"), fila.getDouble("total"),
                        String.valueOf(fila.getTimestamp("fecha")), orden.getLineas());
            }
        }
    }

    public Factura generar(Orden orden) throws SQLException {
        double subtotal = orden.getTotal();
        double impuesto = redondear(subtotal * Factura.PORCENTAJE_IMPUESTO);
        double total = redondear(subtotal + impuesto);
        Timestamp fecha = new Timestamp(System.currentTimeMillis());

        String sql = "INSERT INTO facturas (id_orden, nombre_cliente, subtotal, impuesto, total, fecha) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            sentencia.setInt(1, orden.getId());
            sentencia.setString(2, orden.getNombreCliente());
            sentencia.setDouble(3, subtotal);
            sentencia.setDouble(4, impuesto);
            sentencia.setDouble(5, total);
            sentencia.setTimestamp(6, fecha);
            sentencia.executeUpdate();
            int id = 0;
            try (ResultSet claves = sentencia.getGeneratedKeys()) {
                if (claves.next()) {
                    id = claves.getInt(1);
                }
            }
            return new Factura(id, orden.getId(), orden.getNombreCliente(),
                    subtotal, impuesto, total, String.valueOf(fecha), orden.getLineas());
        }
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
