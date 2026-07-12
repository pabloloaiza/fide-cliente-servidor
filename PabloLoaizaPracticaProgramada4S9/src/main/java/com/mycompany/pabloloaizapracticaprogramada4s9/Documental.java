/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pabloloaizapracticaprogramada4s9;

/**
 *
 * @author Pablo Loaiza
 */
public class Documental extends Audiovisual implements Comparable<Documental>{
    
    //Implementación de método abstracto de la interface
    @Override
    public int compareTo(Documental otroDocumental) {
        return this.nombre.compareTo(otroDocumental.getNombre());
    }
    
    public Documental(String nombre, double calificacion, int lanzamiento, int duracionMinutos, String descripcion) {
        super(nombre, calificacion, lanzamiento, duracionMinutos, descripcion);
    }
}
