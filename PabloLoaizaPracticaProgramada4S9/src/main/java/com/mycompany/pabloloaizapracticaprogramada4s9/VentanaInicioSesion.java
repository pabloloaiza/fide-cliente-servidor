/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.pabloloaizapracticaprogramada4s9;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Pablo Loaiza
 */
public class VentanaInicioSesion extends JFrame {

    //Tiempo máximo (ms) que se espera al servidor antes de reportar un error,
    //para no dejar la ventana congelada si el servidor no responde
    private static final int TIMEOUT_MS = 4000;

    //Componentes de la ventana
    private JLabel labelTitulo;
    private JLabel labelUsuario;
    private JLabel labelContrasena;
    private JTextField campoUsuario;
    private JPasswordField campoContrasena;
    private JButton botonIniciarSesion;
    private JButton botonCrearUsuario;

    //Constructor: arma la ventana
    public VentanaInicioSesion() {
        setTitle("Fideflix - Inicio de Sesion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 220);
        setLocationRelativeTo(null);

        //Se usa un panel con posiciones manuales (layout nulo) para colocar todo
        JPanel panel = new JPanel();
        panel.setLayout(null);

        labelTitulo = new JLabel("Iniciar Sesion");
        labelTitulo.setBounds(120, 10, 150, 25);
        panel.add(labelTitulo);

        labelUsuario = new JLabel("Usuario (correo)");
        labelUsuario.setBounds(20, 50, 120, 25);
        panel.add(labelUsuario);

        campoUsuario = new JTextField();
        campoUsuario.setBounds(150, 50, 160, 25);
        panel.add(campoUsuario);

        labelContrasena = new JLabel("Contraseña");
        labelContrasena.setBounds(20, 85, 120, 25);
        panel.add(labelContrasena);

        campoContrasena = new JPasswordField();
        campoContrasena.setBounds(150, 85, 160, 25);
        panel.add(campoContrasena);

        botonIniciarSesion = new JButton("Iniciar Sesion");
        botonIniciarSesion.setBounds(20, 130, 140, 30);
        panel.add(botonIniciarSesion);

        botonCrearUsuario = new JButton("Crear Usuario");
        botonCrearUsuario.setBounds(170, 130, 140, 30);
        panel.add(botonCrearUsuario);

        add(panel);

        //Acción del botón Iniciar Sesion
        botonIniciarSesion.addActionListener(this::iniciarSesionAccion);

        //Acción del botón Crear Usuario: abre la ventana de creación de usuarios (punto 1)
        botonCrearUsuario.addActionListener(this::crearUsuarioAccion);
    }

    //Método que envía la petición de inicio de sesión al SERVIDOR por medio de un socket.
    //Es el servidor quien verifica las credenciales y notifica si el usuario puede acceder.
    private void iniciarSesionAccion(java.awt.event.ActionEvent evt) {
        String usuarioEscrito = campoUsuario.getText();
        String contrasenaEscrita = new String(campoContrasena.getPassword());

        //Se deshabilita el botón mientras se espera la respuesta para evitar envíos duplicados
        botonIniciarSesion.setEnabled(false);

        //La comunicación con el servidor se hace en un hilo aparte: si se hiciera en el hilo
        //de la interfaz (EDT), un servidor lento o inalcanzable dejaría la ventana congelada.
        new Thread(() -> {
            Object respuesta = solicitarLogin(usuarioEscrito, contrasenaEscrita);

            SwingUtilities.invokeLater(() -> {
                botonIniciarSesion.setEnabled(true);
                if (respuesta instanceof Usuario) {
                    VentanaMenuPrincipal menu = new VentanaMenuPrincipal((Usuario) respuesta);
                    menu.setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, respuesta);
                }
            });
        }).start();
    }

    //Envía las credenciales al servidor y devuelve su respuesta: un Usuario si el acceso
    //fue concedido, o un String con el motivo del error. Se ejecuta fuera del EDT.
    private Object solicitarLogin(String correo, String contrasena) {
        try (Socket socket = new Socket()) {
            //Tiempo máximo para conectar, así no se espera indefinidamente si el servidor no responde
            socket.connect(new InetSocketAddress("localhost", VentanaServidor.PUERTO), TIMEOUT_MS);
            socket.setSoTimeout(TIMEOUT_MS);

            try (ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())) {

                salida.writeObject("LOGIN");
                salida.writeObject(correo);
                salida.writeObject(contrasena);
                salida.flush();

                //El servidor responde con el Usuario (acceso concedido) o con un mensaje de error
                return entrada.readObject();
            }
        } catch (IOException | ClassNotFoundException ex) {
            return "No se pudo conectar con el servidor. ¿Está encendido?\n" + ex.getMessage();
        }
    }

    //Método que abre la ventana de creación de usuarios
    private void crearUsuarioAccion(java.awt.event.ActionEvent evt) {
        VentanaCrearUsuario ventana = new VentanaCrearUsuario();
        ventana.setVisible(true);
    }

    //Main para poder ejecutar directamente la ventana de inicio de sesión
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new VentanaInicioSesion().setVisible(true));
    }
}
