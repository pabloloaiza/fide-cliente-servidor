/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pabloloaizapracticaprogramada4s9;

/**
 *
 * @author Pablo Loaiza
 */
public class Serie extends Audiovisual implements Comparable<Serie>{
    //Atributo adicional que no es compartido por otras clases
    private int temporadas;
    
    //Implementación de método abstracto de la interface
    @Override
    public int compareTo(Serie otraSerie) {
        return this.nombre.compareTo(otraSerie.getNombre());
    }
    
    //Constructor
    public Serie(String nombre, double calificacion, int lanzamiento, int duracionMinutos, int temporadas, String descripcion) {
        super(nombre, calificacion, lanzamiento, duracionMinutos, descripcion);
        this.temporadas = temporadas; 
    }
    
    //Getter y setter
    public int getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(int temporadas) {
        this.temporadas = temporadas;
    }
}
