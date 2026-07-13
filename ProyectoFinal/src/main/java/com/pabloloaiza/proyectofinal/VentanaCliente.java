package com.pabloloaiza.proyectofinal;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Pablo Loaiza
 */
// Pantalla del Cliente: por ahora solo consulta sus puntos y cierra sesion.
public class VentanaCliente extends JFrame {

    private final Cliente cliente;

    public VentanaCliente(Cliente cliente) {
        this.cliente = cliente;

        setTitle("Cliente - " + cliente.getNombre());
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        add(new JLabel("Bienvenido, " + cliente.getNombre()));

        JButton botonPuntos = new JButton("Consultar Puntos");
        JButton botonCerrarSesion = new JButton("Cerrar Sesion");
        add(botonPuntos);
        add(botonCerrarSesion);

        botonPuntos.addActionListener(e
                -> JOptionPane.showMessageDialog(this,
                        "Puntos disponibles: " + cliente.getPuntosAcumulados()));
        botonCerrarSesion.addActionListener(e -> Utilidades.cerrarSesion());

        setVisible(true);
    }
}
