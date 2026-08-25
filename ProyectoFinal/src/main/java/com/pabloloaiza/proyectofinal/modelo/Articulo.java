package com.pabloloaiza.proyectofinal.modelo;

/**
 *
 * @author Pablo Loaiza
 */
public class Articulo implements Producto {

    private static final long serialVersionUID = 2L;

    private int id;
    private String nombre;
    private double precio;
    private String descripcion;
    private boolean disponible;

    public Articulo(int id, String nombre, double precio, String descripcion, boolean disponible) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.disponible = disponible;
    }

    // Constructor corto para cuando el producto todavia no existe en la base
    public Articulo(String nombre, double precio, String descripcion) {
        this(0, nombre, precio, descripcion, true);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public double getPrecio() {
        return precio;
    }

    @Override
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public boolean isDisponible() {
        return disponible;
    }

    @Override
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String getTipo() {
        return "ARTICULO";
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return nombre + " - $" + precio + (disponible ? "" : " (AGOTADO)");
    }
}
