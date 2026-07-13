package com.pabloloaiza.proyectofinal;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Pablo Loaiza
 */
public class VentanaIniciarSesion extends JFrame {

    private JTextField textoUsuario;
    private JPasswordField textoContrasena;
    private JButton botonIngresar;

    public VentanaIniciarSesion() {
        setTitle("Iniciar Sesion");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Usuario:"));
        textoUsuario = new JTextField();
        add(textoUsuario);

        add(new JLabel("Contraseña:"));
        textoContrasena = new JPasswordField();
        add(textoContrasena);

        botonIngresar = new JButton("Ingresar");
        add(botonIngresar);
        botonIngresar.addActionListener(e -> iniciarSesion());

        setVisible(true);
    }

    private void iniciarSesion() {
        String nombre = textoUsuario.getText().trim();
        String contrasena = new String(textoContrasena.getPassword());

        ArrayList<Usuario> usuarios = GestorArchivos.cargarUsuarios();

        // Busco un usuario que coincida en nombre y contraseña
        for (Usuario u : usuarios) {
            if (u.getNombre().equalsIgnoreCase(nombre) && u.getContrasena().equals(contrasena)) {
                Utilidades.cerrarTodasLasVentanas();
                VentanaCrearUsuario.abrirVentanaSegunRol(u);
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
    }
}
