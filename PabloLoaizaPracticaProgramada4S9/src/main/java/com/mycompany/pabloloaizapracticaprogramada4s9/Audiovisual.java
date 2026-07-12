/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pabloloaizapracticaprogramada4s9;
import java.util.ArrayList;

/**
 *
 * @author Pablo Loaiza
 */
public abstract class Audiovisual {
    //Atributos comunes de las clases Documental, Serie y Película
    protected String nombre;
    protected double calificacion;
    protected int lanzamiento;
    protected int duracionMinutos;
    protected String descripcion;
    protected String comentarioUsuario;

    //Colección para almacenar los comentarios de los usuarios
    static ArrayList<String> coleccionDeComentarios = new ArrayList<>();

    //Método para agregar comentarios 
    public static void agregarComentario(String comentarioUsuario){
        coleccionDeComentarios.add(comentarioUsuario);
    }
    
    //Constructor
    public Audiovisual(String nombre, double calificacion, int lanzamiento, int duracionMinutos, String descripcion){
        this.nombre = nombre;
        this.calificacion = calificacion;
        this.lanzamiento = lanzamiento;
        this.duracionMinutos = duracionMinutos;
        this.descripcion = descripcion;
    }
    
    //Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public int getLanzamiento() {
        return lanzamiento;
    }

    public void setLanzamiento(int lanzamiento) {
        this.lanzamiento = lanzamiento;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }    
}
