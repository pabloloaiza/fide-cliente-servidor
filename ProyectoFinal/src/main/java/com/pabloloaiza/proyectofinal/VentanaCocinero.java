package com.pabloloaiza.proyectofinal;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Pablo Loaiza
 */
// Pantalla del Cocinero: por ahora solo lista las ordenes existentes y cierra sesion.
public class VentanaCocinero extends JFrame {

    private final Cocinero cocinero;

    public VentanaCocinero(Cocinero cocinero) {
        this.cocinero = cocinero;

        setTitle("Cocinero - " + cocinero.getNombre());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextArea areaOrdenes = new JTextArea();
        areaOrdenes.setEditable(false);
        areaOrdenes.setText(construirTextoOrdenes());
        add(new JScrollPane(areaOrdenes), BorderLayout.CENTER);

        JButton botonCerrarSesion = new JButton("Cerrar Sesion");
        add(botonCerrarSesion, BorderLayout.SOUTH);
        botonCerrarSesion.addActionListener(e -> Utilidades.cerrarSesion());

        setVisible(true);
    }

    // Arma el texto con las ordenes guardadas; si no hay, avisa
    private String construirTextoOrdenes() {
        ArrayList<Orden> ordenes = GestorArchivos.cargarOrdenes();
        if (ordenes.isEmpty()) {
            return "No hay ordenes registradas por el momento.";
        }
        StringBuilder texto = new StringBuilder("Ordenes existentes:\n\n");
        for (Orden o : ordenes) {
            texto.append("Orden #").append(o.getNumero())
                    .append(" - ").append(o.getDescripcion())
                    .append(" [").append(o.getEstado()).append("]\n");
        }
        return texto.toString();
    }
}
