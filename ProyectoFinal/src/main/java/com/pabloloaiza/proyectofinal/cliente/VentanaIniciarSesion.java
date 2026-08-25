package com.pabloloaiza.proyectofinal.cliente;

import com.pabloloaiza.proyectofinal.modelo.Usuario;
import com.pabloloaiza.proyectofinal.red.Accion;
import com.pabloloaiza.proyectofinal.red.Respuesta;
import java.awt.GridLayout;
import java.io.IOException;
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

    private final JTextField textoUsuario = new JTextField();
    private final JPasswordField textoContrasena = new JPasswordField();

    public VentanaIniciarSesion() {
        setTitle("Iniciar Sesion");
        setSize(320, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Usuario:"));
        add(textoUsuario);
        add(new JLabel("Contrasena:"));
        add(textoContrasena);

        JButton botonIngresar = new JButton("Ingresar");
        add(botonIngresar);
        botonIngresar.addActionListener(e -> iniciarSesion());

        setVisible(true);
    }

    private void iniciarSesion() {
        String nombre = textoUsuario.getText().trim();
        String contrasena = new String(textoContrasena.getPassword());
        if (nombre.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes ingresar usuario y contrasena.");
            return;
        }

        ConexionServidor conexion = new ConexionServidor();
        try {
            conexion.conectar();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo conectar al servidor. Esta encendido?");
            return;
        }

        Respuesta respuesta = conexion.enviar(Accion.INICIAR_SESION, nombre, contrasena);
        if (!respuesta.isExito()) {
            JOptionPane.showMessageDialog(this, respuesta.getMensaje());
            conexion.cerrar();
            return;
        }

        Utilidades.cerrarTodasLasVentanas();
        Utilidades.abrirVentanaSegunRol((Usuario) respuesta.getContenido(), conexion);
    }
}
