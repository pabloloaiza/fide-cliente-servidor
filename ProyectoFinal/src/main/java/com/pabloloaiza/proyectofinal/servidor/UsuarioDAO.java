package com.pabloloaiza.proyectofinal.servidor;

import com.pabloloaiza.proyectofinal.modelo.Cajero;
import com.pabloloaiza.proyectofinal.modelo.Cliente;
import com.pabloloaiza.proyectofinal.modelo.Cocinero;
import com.pabloloaiza.proyectofinal.modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Pablo Loaiza
 */
// DAO = clase que traduce entre objetos Java y filas de la tabla usuarios.
public class UsuarioDAO {

    public Usuario buscarPorCredenciales(String nombre, String contrasena) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE nombre = ? AND contrasena = ?";
        try (Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, nombre);
            sentencia.setString(2, contrasena);
            try (ResultSet fila = sentencia.executeQuery()) {
                return fila.next() ? construirUsuario(fila) : null;
            }
        }
    }

    public boolean existeNombre(String nombre) throws SQLException {
        String sql = "SELECT id FROM usuarios WHERE nombre = ?";
        try (Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, nombre);
            try (ResultSet fila = sentencia.executeQuery()) {
                return fila.next();
            }
        }
    }

    // Inserta el usuario y devuelve el mismo objeto pero ya con el id que asigno MySQL
    public Usuario insertar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, contrasena, tipo, salario, horario, puntos_acumulados) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            sentencia.setString(1, usuario.getNombre());
            sentencia.setString(2, usuario.getContrasena());
            sentencia.setString(3, usuario.getTipo());

            double salario = 0;
            String horario = "";
            double puntos = 0;
            if (usuario instanceof Cajero cajero) {
                salario = cajero.getSalario();
                horario = cajero.getHorario();
            } else if (usuario instanceof Cocinero cocinero) {
                salario = cocinero.getSalario();
                horario = cocinero.getHorario();
            } else if (usuario instanceof Cliente cliente) {
                puntos = cliente.getPuntosAcumulados();
            }
            sentencia.setDouble(4, salario);
            sentencia.setString(5, horario);
            sentencia.setDouble(6, puntos);
            sentencia.executeUpdate();

            try (ResultSet claves = sentencia.getGeneratedKeys()) {
                if (claves.next()) {
                    usuario.setId(claves.getInt(1));
                }
            }
        }
        return usuario;
    }

    // Convierte la fila en la subclase de Usuario que corresponda
    private Usuario construirUsuario(ResultSet fila) throws SQLException {
        int id = fila.getInt("id");
        String nombre = fila.getString("nombre");
        String contrasena = fila.getString("contrasena");
        String tipo = fila.getString("tipo");

        switch (tipo) {
            case "Cajero":
                return new Cajero(id, nombre, contrasena, fila.getDouble("salario"), fila.getString("horario"));
            case "Cocinero":
                return new Cocinero(id, nombre, contrasena, fila.getDouble("salario"), fila.getString("horario"));
            default:
                return new Cliente(id, nombre, contrasena, fila.getDouble("puntos_acumulados"));
        }
    }
}
