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
    }

    //Escribe una línea en el log de forma segura desde cualquier hilo
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

        //Hilo principal del servidor: acepta conexiones en un bucle infinito
        Thread hiloServidor = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(PUERTO);
                log("Servidor iniciado. Escuchando en el puerto " + PUERTO + "...");

                //Estado de escucha constante para múltiples clientes
                while (servidorActivo) {
                    Socket cliente = serverSocket.accept();
                    log("Cliente conectado desde " + cliente.getInetAddress().getHostAddress());
                    //Cada cliente se atiende en su propio hilo
                    new Thread(() -> atenderCliente(cliente)).start();
                }
            } catch (IOException ex) {
                if (servidorActivo) {
                    log("Error en el servidor: " + ex.getMessage());
                }
            }
        });
        hiloServidor.start();
    }

    //Procesa la petición de un cliente: recibe el Usuario, lo guarda y responde
    private void atenderCliente(Socket cliente) {
        try (ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
             ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream())) {

            //Se recibe el objeto Usuario enviado por el cliente
            Object recibido = entrada.readObject();

            if (recibido instanceof Usuario) {
                Usuario nuevoUsuario = (Usuario) recibido;
                log("Petición recibida: crear usuario '" + nuevoUsuario.getCorreo() + "'");

                //Se guarda dentro de la colección existente
                ArrayList<Usuario> coleccion = Usuario.LeerColeccion();
                coleccion.add(nuevoUsuario);
                Usuario.GuardarColeccion(coleccion);

                //Se confirma al cliente que la petición fue COMPLETADA
                salida.writeObject("OK: Usuario guardado correctamente.");
                salida.flush();
                log("Petición COMPLETADA: usuario '" + nuevoUsuario.getCorreo() + "' guardado.");
            } else {
                salida.writeObject("ERROR: Petición no válida.");
                salida.flush();
                log("Petición NO completada: objeto recibido no válido.");
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

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(VentanaServidor::new);
    }
}