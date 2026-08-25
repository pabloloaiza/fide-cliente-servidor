package com.pabloloaiza.proyectofinal.red;

import java.io.Serializable;

/**
 *
 * @author Pablo Loaiza
 */
// Lo que el servidor devuelve: si salio bien, un mensaje para mostrar y el dato pedido.
public class Respuesta implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean exito;
    private String mensaje;
    private Object contenido;

    public Respuesta(boolean exito, String mensaje, Object contenido) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.contenido = contenido;
    }

    public static Respuesta ok(String mensaje) {
        return new Respuesta(true, mensaje, null);
    }

    public static Respuesta ok(String mensaje, Object contenido) {
        return new Respuesta(true, mensaje, contenido);
    }

    public static Respuesta error(String mensaje) {
        return new Respuesta(false, mensaje, null);
    }

    public boolean isExito() {
        return exito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Object getContenido() {
        return contenido;
    }
}
