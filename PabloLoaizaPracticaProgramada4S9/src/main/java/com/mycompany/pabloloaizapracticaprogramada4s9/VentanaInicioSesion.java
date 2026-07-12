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

    //Tiempo máximo de espera antes de reportar un error
    private static final int TIMEOUT_MS = 3000;
    
    private JLabel labelTitulo;
    private JLabel labelUsuario;
    private JLabel labelContrasena;
    private JTextField campoUsuario;
    private JPasswordField campoContrasena;
    private JButton botonIniciarSesion;
    private JButton botonCrearUsuario;

    //Componentes de la ventana
    public VentanaInicioSesion() {
        setTitle("Fideflix - Inicio de Sesion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 220);
        setLocationRelativeTo(null);
        
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

        //Botón Iniciar Sesion
        botonIniciarSesion.addActionListener(this::iniciarSesionAccion);

        //Botón Crear Usuario
        botonCrearUsuario.addActionListener(this::crearUsuarioAccion);
    }

    //Método que envía la petición al SERVIDOR por medio de un socket.
    private void iniciarSesionAccion(java.awt.event.ActionEvent evt) {
        String usuarioEscrito = campoUsuario.getText();
        String contrasenaEscrita = new String(campoContrasena.getPassword());
        
        botonIniciarSesion.setEnabled(false);

        //La comunicación con el servidor se hace en un hilo aparte
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

    //Envía las credenciales al servidor y devuelve la respuesta
    private Object solicitarLogin(String correo, String contrasena) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("localhost", VentanaServidor.PUERTO), TIMEOUT_MS);
            socket.setSoTimeout(TIMEOUT_MS);

            try (ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())) {

                salida.writeObject("LOGIN");
                salida.writeObject(correo);
                salida.writeObject(contrasena);
                salida.flush();
                
                return entrada.readObject();
            }
        } catch (IOException | ClassNotFoundException ex) {
            return "No se pudo conectar con el servidor. ¿Está encendido?\n" + ex.getMessage();
        }
    }

    //Abre la ventana de creación de usuarios
    private void crearUsuarioAccion(java.awt.event.ActionEvent evt) {
        VentanaCrearUsuario ventana = new VentanaCrearUsuario();
        ventana.setVisible(true);
    }

    //Main para poder ejecutar directamente la ventana de inicio de sesión
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new VentanaInicioSesion().setVisible(true));
    }
}
