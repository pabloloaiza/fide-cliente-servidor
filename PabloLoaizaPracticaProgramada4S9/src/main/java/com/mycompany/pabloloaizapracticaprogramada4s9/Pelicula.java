/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pabloloaizapracticaprogramada4s9;

/**
 *
 * @author Pablo Loaiza
 */
public class Pelicula extends Audiovisual implements Comparable<Pelicula>{
    
    //Implementación de método abstracto de la interface
    @Override
    public int compareTo(Pelicula otraPelicula) {
        return this.nombre.compareTo(otraPelicula.getNombre());
    }
    
    public Pelicula(String nombre, double calificacion, int lanzamiento, int duracionMinutos, String descripcion) {
        super(nombre, calificacion, lanzamiento, duracionMinutos, descripcion);
    }
}
