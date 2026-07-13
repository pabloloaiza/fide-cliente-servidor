package com.pabloloaiza.proyectofinal;

import javax.swing.SwingUtilities;

/**
 *
 * @author Pablo Loaiza
 */
public class ProyectoFinal {

    public static void main(String[] args) {
        // Levanto la interfaz en el hilo de eventos de Swing (buena practica)
        SwingUtilities.invokeLater(() -> new VentanaMenuPrincipal());
    }
}
