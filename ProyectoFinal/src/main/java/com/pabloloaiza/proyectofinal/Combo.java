package com.pabloloaiza.proyectofinal;

import java.util.ArrayList;

/**
 *
 * @author Pablo Loaiza
 */
public class Combo implements Producto {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private double precio;
    // Un combo agrupa varios articulos, aqui guardo solo sus nombres
    private ArrayList<String> articulos;

    public Combo(String nombre, double precio, ArrayList<String> articulos) {
        this.nombre = nombre;
        this.precio = precio;
        this.articulos = articulos;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public double getPrecio() {
        return precio;
    }

    public ArrayList<String> getArticulos() {
        return articulos;
    }
}
