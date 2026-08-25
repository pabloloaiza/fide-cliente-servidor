package com.pabloloaiza.proyectofinal.servidor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Pablo Loaiza
 */
// Datos de acceso a MySQL. Solo el servidor usa esta clase; el cliente nunca
// habla directo con la base de datos, va siempre por el socket.
public class ConexionBD {

    private static final String URL =
            "jdbc:mysql://127.0.0.1:3306/fideburguesas";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "CONTRASEÑA_AQUI";

    // Cada llamada abre una conexion nueva. Como cada cliente corre en su propio
    // hilo, asi evito que dos hilos compartan la misma Connection (no es thread-safe).
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }

    // Prueba de arranque: si esto falla, el servidor avisa y no sigue
    public static void probarConexion() throws SQLException {
        try (Connection conexion = conectar()) {
            System.out.println("Conexion a MySQL establecida: " + conexion.getCatalog());
        }
    }
}
