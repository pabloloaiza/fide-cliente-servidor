package com.pabloloaiza.proyectofinal;

/**
 *
 * @author Pablo Loaiza
 */
public class Articulo implements Producto {

    private static final long serialVersionUID = 1L;

    private double precio;
    private String nombre;
    private String descripcion;

    public Articulo(String nombre, double precio, String descripcion) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
