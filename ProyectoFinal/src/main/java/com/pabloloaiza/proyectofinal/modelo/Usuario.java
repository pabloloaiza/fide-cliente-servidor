package com.pabloloaiza.proyectofinal.modelo;

import java.io.Serializable;

/**
 *
 * @author Pablo Loaiza
 */
// Serializable porque los objetos Usuario viajan por el socket entre cliente y servidor
public abstract class Usuario implements Serializable {

    private static final long serialVersionUID = 2L;

    private int id;
    private String nombre;
    private String contrasena;
    private String tipo;

    public Usuario(int id, String nombre, String contrasena, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
