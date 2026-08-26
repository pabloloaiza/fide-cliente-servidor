package com.pabloloaiza.proyectofinal.usuario;

import com.pabloloaiza.proyectofinal.red.Accion;
import com.pabloloaiza.proyectofinal.red.Peticion;
import com.pabloloaiza.proyectofinal.red.Respuesta;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Pablo Loaiza
 */
// El lado cliente del socket. Cada sesion (cada usuario que entra) tiene su
// propia instancia, y en el servidor eso equivale a un hilo distinto.
public class ConexionServidor {

    public static final String HOST = "localhost";
    public static final int PUERTO = 5000;

    private Socket socket;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;

    public void conectar() throws IOException {
        socket = new Socket(HOST, PUERTO);
        salida = new ObjectOutputStream(socket.getOutputStream());
        entrada = new ObjectInputStream(socket.getInputStream());
    }

    // synchronized porque si en el futuro dos ventanas de la misma sesion
    // piden algo a la vez, no se pueden mezclar peticion y respuesta en el flujo.
    public synchronized Respuesta enviar(Accion accion, Object... datos) {
        try {
            salida.writeObject(new Peticion(accion, datos));
            salida.flush();
            salida.reset();
            return (Respuesta) entrada.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            return Respuesta.error("Se perdio la conexion con el servidor: " + ex.getMessage());
        }
    }

    public void cerrar() {
        try {
            if (socket != null && !socket.isClosed()) {
                enviar(Accion.CERRAR_SESION);
                socket.close();
            }
        } catch (IOException ex) {
            System.out.println("Error cerrando la conexion: " + ex.getMessage());
        }
    }
}
