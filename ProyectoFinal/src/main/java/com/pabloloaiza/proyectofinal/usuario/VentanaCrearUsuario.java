package com.pabloloaiza.proyectofinal.usuario;

import com.pabloloaiza.proyectofinal.modelo.Cajero;
import com.pabloloaiza.proyectofinal.modelo.Cliente;
import com.pabloloaiza.proyectofinal.modelo.Cocinero;
import com.pabloloaiza.proyectofinal.modelo.Usuario;
import com.pabloloaiza.proyectofinal.red.Accion;
import com.pabloloaiza.proyectofinal.red.Respuesta;
import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Pablo Loaiza
 */
public class VentanaCrearUsuario extends JFrame {

    private final JTextField textoUsuario = new JTextField();
    private final JPasswordField textoContrasena = new JPasswordField();
    private final JRadioButton opcionCliente = new JRadioButton("Cliente");
    private final JRadioButton opcionCajero = new JRadioButton("Cajero");
    private final JRadioButton opcionCocinero = new JRadioButton("Cocinero");

    public VentanaCrearUsuario() {
        setTitle("Crear Usuario");
        setSize(320, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Usuario:"));
        add(textoUsuario);
        add(new JLabel("Contrasena:"));
        add(textoContrasena);

        add(new JLabel("Rol:"));
        add(opcionCliente);
        add(opcionCajero);
        add(opcionCocinero);

        // El ButtonGroup obliga a que solo se pueda elegir un rol a la vez
        ButtonGroup grupoRoles = new ButtonGroup();
        grupoRoles.add(opcionCliente);
        grupoRoles.add(opcionCajero);
        grupoRoles.add(opcionCocinero);

        JButton botonGuardar = new JButton("Guardar");
        add(botonGuardar);
        botonGuardar.addActionListener(e -> guardarUsuario());

        setVisible(true);
    }

    private void guardarUsuario() {
        String nombre = textoUsuario.getText().trim();
        String contrasena = new String(textoContrasena.getPassword());
        if (nombre.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes ingresar usuario y contrasena.");
            return;
        }
        String rol = obtenerRolSeleccionado();
        if (rol == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un rol.");
            return;
        }

        ConexionServidor conexion = new ConexionServidor();
        try {
            conexion.conectar();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo conectar al servidor. Esta encendido?");
            return;
        }

        // El id va en 0 porque lo asigna MySQL con AUTO_INCREMENT
        Usuario nuevo = crearUsuarioSegunRol(nombre, contrasena, rol);
        Respuesta respuesta = conexion.enviar(Accion.REGISTRAR_USUARIO, nuevo);
        JOptionPane.showMessageDialog(this, respuesta.getMensaje());
        if (!respuesta.isExito()) {
            conexion.cerrar();
            return;
        }

        Utilidades.cerrarTodasLasVentanas();
        Utilidades.abrirVentanaSegunRol((Usuario) respuesta.getContenido(), conexion);
    }

    private String obtenerRolSeleccionado() {
        if (opcionCliente.isSelected()) {
            return "Cliente";
        }
        if (opcionCajero.isSelected()) {
            return "Cajero";
        }
        if (opcionCocinero.isSelected()) {
            return "Cocinero";
        }
        return null;
    }

    private Usuario crearUsuarioSegunRol(String nombre, String contrasena, String rol) {
        switch (rol) {
            case "Cajero":
                return new Cajero(0, nombre, contrasena, 0.0, "");
            case "Cocinero":
                return new Cocinero(0, nombre, contrasena, 0.0, "");
            default:
                return new Cliente(0, nombre, contrasena, 0.0);
        }
    }
}
