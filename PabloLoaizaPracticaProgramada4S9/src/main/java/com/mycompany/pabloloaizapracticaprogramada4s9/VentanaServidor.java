/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pabloloaizapracticaprogramada4s9;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author Pablo Loaiza
 */
public class VentanaServidor extends JFrame {

    public static final int PUERTO = 5000;

    private JTextArea txtLog;
    private JButton botonIniciar;
    private ServerSocket serverSocket;
    private boolean servidorActivo = false;

    public VentanaServidor() {
        setTitle("Servidor");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        txtLog = new JTextArea();
        txtLog.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtLog);

        botonIniciar = new JButton("Iniciar Servidor");

        add(scroll, BorderLayout.CENTER);
        add(botonIniciar, BorderLayout.SOUTH);

        botonIniciar.addActionListener(e -> iniciarServidor());

        setVisible(true);

        //El servidor queda en escucha desde que se abre la ventana,
        iniciarServidor();
    }

    //Escribe una línea en el log desde cualquier hilo
    private void log(String mensaje) {
        String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        SwingUtilities.invokeLater(() -> txtLog.append("[" + hora + "] " + mensaje + "\n"));
    }

    //Inicia el servidor en un hilo aparte para no congelar la ventana
    private void iniciarServidor() {
        if (servidorActivo) {
            log("El servidor ya está en ejecución.");
            return;
        }
        servidorActivo = true;
        botonIniciar.setEnabled(false);

        //Hilo principal del servidor
        Thread hiloServidor = new Thread(() -> {
            boolean seLogroIniciar = false;
            try {
                serverSocket = new ServerSocket(PUERTO);
                seLogroIniciar = true;
                log("Servidor iniciado. Escuchando en el puerto " + PUERTO + "...");

                //Escucha constante para múltiples clientes
                while (servidorActivo) {
                    Socket cliente = serverSocket.accept();
                    log("Cliente conectado desde " + cliente.getInetAddress().getHostAddress());
                    //Cada cliente se atiende en su propio hilo
                    new Thread(() -> atenderCliente(cliente)).start();
                }
            } catch (IOException ex) {
                if (servidorActivo) {
                    if (!seLogroIniciar) {
                        log("No se pudo iniciar el servidor en el puerto " + PUERTO + ": " + ex.getMessage()
                                + " (¿hay otra instancia usando ese puerto?)");
                    } else {
                        log("Error en el servidor: " + ex.getMessage());
                    }
                }
            } finally {
                //Si el hilo termina, se libera el estado
                servidorActivo = false;
                SwingUtilities.invokeLater(() -> botonIniciar.setEnabled(true));
            }
        });
        hiloServidor.start();
    }

    //Procesa la petición de un cliente
    private void atenderCliente(Socket cliente) {
        try (ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
             ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream())) {

            Object tipoRecibido = entrada.readObject();
            String tipo = (tipoRecibido instanceof String) ? (String) tipoRecibido : "";

            switch (tipo) {
                case "CREAR_USUARIO":
                    atenderCrearUsuario(entrada, salida);
                    break;
                case "LOGIN":
                    atenderLogin(entrada, salida);
                    break;
                default:
                    salida.writeObject("ERROR: Petición no válida.");
                    salida.flush();
                    log("Petición NO completada: tipo de petición no reconocido.");
                    break;
            }
        } catch (IOException | ClassNotFoundException ex) {
            log("Petición NO completada. Error: " + ex.getMessage());
        } finally {
            try {
                cliente.close();
            } catch (IOException ex) {
                log("Error al cerrar la conexión: " + ex.getMessage());
            }
        }
    }

    //Proceso de creación de usuario
    private void atenderCrearUsuario(ObjectInputStream entrada, ObjectOutputStream salida)
            throws IOException, ClassNotFoundException {
        Object recibido = entrada.readObject();

        if (recibido instanceof Usuario) {
            Usuario nuevoUsuario = (Usuario) recibido;
            log("Petición recibida: crear usuario '" + nuevoUsuario.getCorreo() + "'");

            ArrayList<Usuario> coleccion = Usuario.LeerColeccion();
            coleccion.add(nuevoUsuario);
            Usuario.GuardarColeccion(coleccion);

            salida.writeObject("OK: Usuario guardado correctamente.");
            salida.flush();
            log("Petición COMPLETADA: usuario '" + nuevoUsuario.getCorreo() + "' guardado.");
        } else {
            salida.writeObject("ERROR: Petición no válida.");
            salida.flush();
            log("Petición NO completada: objeto recibido no válido.");
        }
    }

    //Proceso de inicio de sesión
    private void atenderLogin(ObjectInputStream entrada, ObjectOutputStream salida)
            throws IOException, ClassNotFoundException {
        String correo = (String) entrada.readObject();
        String contrasena = (String) entrada.readObject();

        log("Petición recibida: inicio de sesión para '" + correo + "'");

        ArrayList<Usuario> coleccion = Usuario.LeerColeccion();
        Usuario usuarioEncontrado = null;
        for (Usuario usuario : coleccion) {
            if (usuario.getCorreo().equals(correo) && usuario.getContrasena().equals(contrasena)) {
                usuarioEncontrado = usuario;
            }
        }

        if (usuarioEncontrado != null) {
            salida.writeObject(usuarioEncontrado);
            salida.flush();
            log("Petición COMPLETADA: acceso concedido a '" + correo + "'.");
        } else {
            salida.writeObject("ERROR: El usuario no tiene acceso a la aplicacion.");
            salida.flush();
            log("Petición COMPLETADA: acceso denegado a '" + correo + "'.");
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(VentanaServidor::new);
    }
}