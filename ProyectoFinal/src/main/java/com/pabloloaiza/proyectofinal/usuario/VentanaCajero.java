package com.pabloloaiza.proyectofinal.usuario;

import com.pabloloaiza.proyectofinal.modelo.Cajero;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Pablo Loaiza
 */
// Pantalla del Cajero: crear productos/combos y administrar el menu existente.
public class VentanaCajero extends JFrame {

    private final Cajero cajero;
    private final ConexionServidor conexion;

    public VentanaCajero(Cajero cajero, ConexionServidor conexion) {
        this.cajero = cajero;
        this.conexion = conexion;

        setTitle("Cajero - " + cajero.getNombre());
        setSize(360, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        add(new JLabel("  Bienvenido, " + cajero.getNombre()));

        JButton botonAgregarProducto = new JButton("Agregar Producto");
        JButton botonAgregarCombo = new JButton("Agregar Combo");
        JButton botonAdministrar = new JButton("Administrar Menu (editar / agotado)");
        JButton botonCerrarSesion = new JButton("Cerrar Sesion");
        add(botonAgregarProducto);
        add(botonAgregarCombo);
        add(botonAdministrar);
        add(botonCerrarSesion);

        // El mismo formulario sirve para articulo o combo, le indico cual con el parametro
        botonAgregarProducto.addActionListener(e -> new VentanaCrearProducto(conexion, false));
        botonAgregarCombo.addActionListener(e -> new VentanaCrearProducto(conexion, true));
        botonAdministrar.addActionListener(e -> new VentanaAdministrarProductos(conexion));
        botonCerrarSesion.addActionListener(e -> Utilidades.cerrarSesion(conexion));
        botonCerrarSesion.addActionListener(e -> dispose());
        setVisible(true);
    }
}
