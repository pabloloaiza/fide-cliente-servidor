package com.pabloloaiza.proyectofinal.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Pablo Loaiza
 */
// Programa aparte del cliente: se queda escuchando en un puerto y por cada
// cliente que se conecta arranca un hilo (HiloCliente). Por eso varios usuarios
// pueden iniciar sesion y trabajar al mismo tiempo sin bloquearse entre ellos.
public class Servidor {

    public static final int PUERTO = 5000;

    // AtomicInteger porque varios hilos lo incrementan a la vez
    private static final AtomicInteger clientesConectados = new AtomicInteger(0);

    public static void main(String[] args) {
        try {
            ConexionBD.probarConexion();
        } catch (SQLException ex) {
            System.out.println("No se pudo conectar a MySQL. Revisa ConexionBD y que exista la BD 'pos'.");
            System.out.println("Detalle: " + ex.getMessage());
            return;
        }

        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Servidor escuchando en el puerto " + PUERTO + "...");
            while (true) {
                Socket socketCliente = servidor.accept();
                // El hilo principal solo acepta conexiones; la atencion la hace el hilo nuevo
                Thread hilo = new Thread(new HiloCliente(socketCliente));
                hilo.setName("Cliente-" + clientesConectados.incrementAndGet());
                hilo.start();
            }
        } catch (IOException ex) {
            System.out.println("Error en el servidor: " + ex.getMessage());
        }
    }

    public static void registrarDesconexion() {
        clientesConectados.decrementAndGet();
    }

    public static int getClientesConectados() {
        return clientesConectados.get();
    }
}
