package com.pabloloaiza.proyectofinal;

import java.awt.Window;

/**
 *
 * @author Pablo Loaiza
 */
public class Utilidades {

    // Cierra todas las ventanas abiertas de la aplicacion.
    // La uso cuando cambio de pantalla (login/rol) para que solo quede
    // visible la ventana nueva, tal como pide el enunciado.
    public static void cerrarTodasLasVentanas() {
        for (Window ventana : Window.getWindows()) {
            ventana.dispose();
        }
    }

    // Cierra la sesion actual: oculta todo y vuelve a mostrar el menu principal
    // para que otro usuario pueda ingresar.
    public static void cerrarSesion() {
        cerrarTodasLasVentanas();
        new VentanaMenuPrincipal();
    }
}
