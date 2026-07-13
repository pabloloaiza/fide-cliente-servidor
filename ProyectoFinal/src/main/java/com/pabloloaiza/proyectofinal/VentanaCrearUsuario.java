package com.pabloloaiza.proyectofinal;

import java.awt.GridLayout;
import java.util.ArrayList;
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

    private JTextField textoUsuario;
    private JPasswordField textoContrasena;
    private JRadioButton opcionCliente;
    private JRadioButton opcionCajero;
    private JRadioButton opcionCocinero;
    private JButton botonGuardar;

    public VentanaCrearUsuario() {
        setTitle("Crear Usuario");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Usuario:"));
        textoUsuario = new JTextField();
        add(textoUsuario);

        add(new JLabel("Contraseña:"));
        textoContrasena = new JPasswordField();
        add(textoContrasena);

        add(new JLabel("Rol:"));
        opcionCliente = new JRadioButton("Cliente");
        add(opcionCliente);
        opcionCajero = new JRadioButton("Cajero");
        add(opcionCajero);
        opcionCocinero = new JRadioButton("Cocinero");
        add(opcionCocinero);

        // El ButtonGroup obliga a que solo se pueda elegir un rol a la vez
        ButtonGroup grupoRoles = new ButtonGroup();
        grupoRoles.add(opcionCliente);
        grupoRoles.add(opcionCajero);
        grupoRoles.add(opcionCocinero);

        botonGuardar = new JButton("Guardar");
        add(botonGuardar);
        botonGuardar.addActionListener(e -> guardarUsuario());

        setVisible(true);
    }

    private void guardarUsuario() {
        String nombre = textoUsuario.getText().trim();
        String contrasena = new String(textoContrasena.getPassword());

        if (nombre.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes ingresar usuario y contraseña.");
            return;
        }

        String rol = obtenerRolSeleccionado();
        if (rol == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un rol.");
            return;
        }

        ArrayList<Usuario> usuarios = GestorArchivos.cargarUsuarios();

        // El nombre de usuario debe ser unico
        for (Usuario u : usuarios) {
            if (u.getNombre().equalsIgnoreCase(nombre)) {
                JOptionPane.showMessageDialog(this, "El usuario \"" + nombre + "\" ya existe.");
                return;
            }
        }

        Usuario nuevo = crearUsuarioSegunRol(nombre, contrasena, rol);
        usuarios.add(nuevo);
        GestorArchivos.guardarUsuarios(usuarios);

        JOptionPane.showMessageDialog(this, "Usuario creado correctamente.");

        // Cierro todas las ventanas y abro la pantalla propia del rol
        Utilidades.cerrarTodasLasVentanas();
        abrirVentanaSegunRol(nuevo);
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

    // Segun el rol elegido creo la subclase correspondiente de Usuario.
    // Salario/horario quedan en valores por defecto porque el formulario
    // solo pide usuario, contraseña y rol en esta iteracion.
    private Usuario crearUsuarioSegunRol(String nombre, String contrasena, String rol) {
        switch (rol) {
            case "Cajero":
                return new Cajero(nombre, contrasena, rol, 0.0, "");
            case "Cocinero":
                return new Cocinero(nombre, contrasena, rol, 0.0, "");
            default:
                return new Cliente(nombre, contrasena, rol, 0.0);
        }
    }

    // Abre la ventana correcta segun el tipo de usuario
    static void abrirVentanaSegunRol(Usuario usuario) {
        switch (usuario.getTipo()) {
            case "Cajero":
                new VentanaCajero((Cajero) usuario);
                break;
            case "Cocinero":
                new VentanaCocinero((Cocinero) usuario);
                break;
            default:
                new VentanaCliente((Cliente) usuario);
                break;
        }
    }
}
