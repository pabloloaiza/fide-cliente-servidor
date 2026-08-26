package com.pabloloaiza.proyectofinal.modelo;

import com.pabloloaiza.proyectofinal.usuario.VentanaMenuPrincipal;
import javax.swing.SwingUtilities;

/**
 *
 * @author Pablo Loaiza
 */
// Punto de entrada del CLIENTE. El servidor es otro main:
// com.pabloloaiza.proyectofinal.servidor.Servidor
// Se puede ejecutar este main varias veces a la vez y cada instancia sera un
// usuario distinto, atendido por su propio hilo en el servidor.
public class ProyectoFinal {

    public static void main(String[] args) {
        // Levanto la interfaz en el hilo de eventos de Swing (buena practica)
        SwingUtilities.invokeLater(() -> new VentanaMenuPrincipal());
    }
}
