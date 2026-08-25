package com.pabloloaiza.proyectofinal.cliente;

import com.pabloloaiza.proyectofinal.modelo.Cliente;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Pablo Loaiza
 */
// Pantalla del Cliente: hacer un pedido, ver sus ordenes (y facturarlas) y sus puntos.
public class VentanaCliente extends JFrame {

    private final Cliente cliente;
    private final ConexionServidor conexion;

    public VentanaCliente(Cliente cliente, ConexionServidor conexion) {
        this.cliente = cliente;
        this.conexion = conexion;

        setTitle("Cliente - " + cliente.getNombre());
        setSize(360, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        add(new JLabel("  Bienvenido, " + cliente.getNombre()));

        JButton botonNuevaOrden = new JButton("Hacer Pedido");
        JButton botonMisOrdenes = new JButton("Mis Ordenes / Facturar");
        JButton botonPuntos = new JButton("Consultar Puntos");
        JButton botonCerrarSesion = new JButton("Cerrar Sesion");
        add(botonNuevaOrden);
        add(botonMisOrdenes);
        add(botonPuntos);
        add(botonCerrarSesion);

        botonNuevaOrden.addActionListener(e -> new VentanaCrearOrden(cliente, conexion));
        botonMisOrdenes.addActionListener(e -> new VentanaMisOrdenes(cliente, conexion));
        botonPuntos.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Puntos disponibles: " + cliente.getPuntosAcumulados()));
        botonCerrarSesion.addActionListener(e -> Utilidades.cerrarSesion(conexion));
        botonCerrarSesion.addActionListener(e -> dispose());
        
        setVisible(true);
    }
}
