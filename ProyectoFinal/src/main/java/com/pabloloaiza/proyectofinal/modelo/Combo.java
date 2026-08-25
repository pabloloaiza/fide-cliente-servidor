package com.pabloloaiza.proyectofinal.modelo;

import java.util.ArrayList;

/**
 *
 * @author Pablo Loaiza
 */
public class Combo implements Producto {

    private static final long serialVersionUID = 2L;

    private int id;
    private String nombre;
    private double precio;
    private boolean disponible;
    // Ids de los articulos que forman el combo (tabla combo_articulos)
    private ArrayList<Integer> idsArticulos;
    // Solo para mostrar en pantalla, no se guarda en la tabla del combo
    private ArrayList<String> nombresArticulos;

    public Combo(int id, String nombre, double precio, boolean disponible,
            ArrayList<Integer> idsArticulos, ArrayList<String> nombresArticulos) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.disponible = disponible;
        this.idsArticulos = idsArticulos;
        this.nombresArticulos = nombresArticulos;
    }

    public Combo(String nombre, double precio, ArrayList<Integer> idsArticulos) {
        this(0, nombre, precio, true, idsArticulos, new ArrayList<>());
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
        return "COMBO";
    }

    public ArrayList<Integer> getIdsArticulos() {
        return idsArticulos;
    }

    public void setIdsArticulos(ArrayList<Integer> idsArticulos) {
        this.idsArticulos = idsArticulos;
    }

    public ArrayList<String> getNombresArticulos() {
        return nombresArticulos;
    }

    public void setNombresArticulos(ArrayList<String> nombresArticulos) {
        this.nombresArticulos = nombresArticulos;
    }

    @Override
    public String toString() {
        return nombre + " - $" + precio + " " + nombresArticulos + (disponible ? "" : " (AGOTADO)");
    }
}
