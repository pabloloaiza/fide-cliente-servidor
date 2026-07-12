/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.pabloloaizapracticaprogramada4s9;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Pablo Loaiza
 */
public class VentanaInicioSesion extends JFrame {

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

        try (Socket socket = new Socket("localhost", VentanaServidor.PUERTO);
             ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())) {

            salida.writeObject("LOGIN");
            salida.writeObject(usuarioEscrito);
            salida.writeObject(contrasenaEscrita);
            salida.flush();

            //El servidor responde con el Usuario (acceso concedido) o con un mensaje de error
            Object respuesta = entrada.readObject();

            if (respuesta instanceof Usuario) {
                VentanaMenuPrincipal menu = new VentanaMenuPrincipal((Usuario) respuesta);
                menu.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, respuesta);
            }
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo conectar con el servidor. ¿Está encendido?\n" + ex.getMessage());
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
