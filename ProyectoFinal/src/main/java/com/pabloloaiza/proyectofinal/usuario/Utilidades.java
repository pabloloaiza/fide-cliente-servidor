package com.pabloloaiza.proyectofinal.usuario;

import com.pabloloaiza.proyectofinal.modelo.Cajero;
import com.pabloloaiza.proyectofinal.modelo.Cliente;
import com.pabloloaiza.proyectofinal.modelo.Cocinero;
import com.pabloloaiza.proyectofinal.modelo.Usuario;
import java.awt.Window;

/**
 *
 * @author Pablo Loaiza
 */
public class Utilidades {

    public static void cerrarTodasLasVentanas() {
        for (Window ventana : Window.getWindows()) {
            ventana.dispose();
        }
    }

    // Cierra la sesion: avisa al servidor (para que termine su hilo), cierra las
    // ventanas y vuelve al menu principal para que entre otro usuario.
    public static void cerrarSesion(ConexionServidor conexion) {
        if (conexion != null) {
            conexion.cerrar();
        }
    }

    public static void abrirVentanaSegunRol(Usuario usuario, ConexionServidor conexion) {
        if (usuario instanceof Cajero cajero) {
            new VentanaCajero(cajero, conexion);
        } else if (usuario instanceof Cocinero cocinero) {
            new VentanaCocinero(cocinero, conexion);
        } else {
            new VentanaCliente((Cliente) usuario, conexion);
        }
    }
}
