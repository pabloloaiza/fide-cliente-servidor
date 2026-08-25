package com.pabloloaiza.proyectofinal.cliente;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Pablo Loaiza
 */
// Ventana inicial. Cada vez que se abre login o registro se crea una conexion
// nueva al servidor, o sea un hilo nuevo del lado del servidor.
public class VentanaMenuPrincipal extends JFrame {

    public VentanaMenuPrincipal() {
        setTitle("Menu Principal");
        setSize(350, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        add(new JLabel("  Sistema de pedidos (cliente-servidor)"));
        JButton botonIngresar = new JButton("Ingresar");
        JButton botonCrearUsuario = new JButton("Crear Usuario");
        add(botonIngresar);
        add(botonCrearUsuario);

        botonIngresar.addActionListener(e -> {
            new VentanaIniciarSesion();
        });
        botonCrearUsuario.addActionListener(e -> {
            new VentanaCrearUsuario();
            dispose();
        });

        setVisible(true);
    }
}
