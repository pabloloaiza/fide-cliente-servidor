package com.pabloloaiza.proyectofinal;

import java.io.Serializable;

/**
 *
 * @author Pablo Loaiza
 */
// Serializable para poder guardar los usuarios como objetos en el archivo
public abstract class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String contrasena;
    private String tipo;

    public Usuario(String nombre, String contrasena, String tipo) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.tipo = tipo;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }    
}
