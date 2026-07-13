package com.pabloloaiza.proyectofinal;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Pablo Loaiza
 */
// Pantalla del Cajero: puede agregar productos o combos, y cerrar sesion.
public class VentanaCajero extends JFrame {

    private final Cajero cajero;

    public VentanaCajero(Cajero cajero) {
        this.cajero = cajero;

        setTitle("Cajero - " + cajero.getNombre());
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        add(new JLabel("Bienvenido, " + cajero.getNombre()));

        JButton botonAgregarProducto = new JButton("Agregar Producto");
        JButton botonAgregarCombo = new JButton("Agregar Combo");
        JButton botonCerrarSesion = new JButton("Cerrar Sesion");
        add(botonAgregarProducto);
        add(botonAgregarCombo);
        add(botonCerrarSesion);

        // El mismo formulario sirve para articulo o combo, le indico cual con el parametro
        botonAgregarProducto.addActionListener(e -> new VentanaCrearProducto(cajero, false));
        botonAgregarCombo.addActionListener(e -> new VentanaCrearProducto(cajero, true));
        botonCerrarSesion.addActionListener(e -> Utilidades.cerrarSesion());

        setVisible(true);
    }
}
