package com.pabloloaiza.proyectofinal;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Pablo Loaiza
 */
// Ventana inicial. Es la unica que cierra la aplicacion al cerrarse (EXIT_ON_CLOSE).
// Desde aqui se puede iniciar sesion o registrar un nuevo usuario.
public class VentanaMenuPrincipal extends JFrame {

    private JButton botonIngresar;
    private JButton botonCrearUsuario;

    public VentanaMenuPrincipal() {
        setTitle("Menu Principal");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        botonIngresar = new JButton("Ingresar");
        botonCrearUsuario = new JButton("Crear Usuario");
        add(botonIngresar);
        add(botonCrearUsuario);

        // Cada boton abre su ventana y cierra el menu principal
        botonIngresar.addActionListener(e -> {
            new VentanaIniciarSesion();
            dispose();
        });
        botonCrearUsuario.addActionListener(e -> {
            new VentanaCrearUsuario();
            dispose();
        });

        setVisible(true);
    }
}
