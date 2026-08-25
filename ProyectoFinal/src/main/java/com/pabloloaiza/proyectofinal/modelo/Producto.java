package com.pabloloaiza.proyectofinal.modelo;

import java.io.Serializable;

/**
 *
 * @author Pablo Loaiza
 */
public interface Producto extends Serializable {

    // Todo producto vendible tiene id (el de MySQL), nombre, precio y disponibilidad
    int getId();
    void setId(int id);

    String getNombre();
    void setNombre(String nombre);

    double getPrecio();
    void setPrecio(double precio);

    boolean isDisponible();
    void setDisponible(boolean disponible);

    // Sirve para saber en que tabla/columna guardarlo sin usar instanceof por todos lados
    String getTipo();
}
