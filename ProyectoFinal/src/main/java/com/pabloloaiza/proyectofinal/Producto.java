package com.pabloloaiza.proyectofinal;

import java.io.Serializable;

/**
 *
 * @author Pablo Loaiza
 */
public interface Producto extends Serializable {

    // Todo producto vendible debe poder dar su nombre y precio
    public String getNombre();
    public double getPrecio();
}
