/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.pabloloaizapracticaprogramada4s9;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Pablo Loaiza
 */
public class PabloLoaizaPracticaProgramada4s9 {
    public static void main(String[] args) {

        //Se levanta el SERVIDOR dentro de este mismo programa. La ventana del servidor
        //arranca su propio hilo (ServerSocket en el puerto 5000) desde su constructor,
        //por lo que al crearla queda escuchando conexiones sin congelar el resto.
        //Así se demuestra la ejecución en hilos: servidor y cliente conviven en el mismo proceso.
        new VentanaServidor();

        //Código de la entrega #3: ventana de inicio de sesión (el CLIENTE)
        new VentanaInicioSesion().setVisible(true);

        //Código de la entrega #2, comentado para limpiar el resultado final
        /*
        ArrayList<Usuario> coleccionUsuarios = new ArrayList<>();
        
        coleccionUsuarios.add(new Usuario("Juan", "Valdez", "juan.valdez", "1234"));
        coleccionUsuarios.add(new Usuario("Pedro", "Pérez", "pedro.perez", "1234"));
        coleccionUsuarios.add(new Usuario("Ernesto", "Vives", "ernesto.vives", "1234"));
        coleccionUsuarios.add(new Usuario("Pablo", "Neruda", "pablo.neruda", "1234"));
        coleccionUsuarios.add(new Usuario("Lucas", "Loaiza", "lucas.loaiza", "1234"));
        coleccionUsuarios.add(new Usuario("Mariana", "Porras", "mariana.porras", "1234"));
        coleccionUsuarios.add(new Usuario("Vanessa", "Montero", "vanessa.montero", "1234"));
        coleccionUsuarios.add(new Usuario("Sofia", "Zamora", "sofia.zamora", "1234"));
        coleccionUsuarios.add(new Usuario("Paula", "Ortiz", "paula.ortiz", "1234"));
        coleccionUsuarios.add(new Usuario("Yolanda", "Oreamuno", "yolanda.oreamuno", "1234"));
        
        System.out.println("Coleccion original");
        System.out.println(coleccionUsuarios);
        Collections.sort(coleccionUsuarios);
        
        System.out.println("Coleccion ordenada");
        System.out.println(coleccionUsuarios);
        
        //Se define el usuario a eliminar
        Usuario usuarioEliminar = new Usuario("Pedro", "Pérez", "pedro.perez", "1234");
        
        //Manejo de excepciones
        try {
            coleccionUsuarios.remove(usuarioEliminar);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Coleccion sin el usuario eliminado");
            System.out.println(coleccionUsuarios);
            
            //Destrucción de objetos
            coleccionUsuarios.clear();
            usuarioEliminar = null;
            
            System.out.println("Print de la coleccion vacia (los objetos fueron destruidos)");
            System.out.println(coleccionUsuarios);
        }
        */
        
        //Código de la entrega #1, comentado para limpiar el resultado final
        /*
        //Creación de objetos
        Usuario Pablo = new Usuario("Pablo", "Loaiza", 28, "Masculino");
        
        Pelicula Interstellar = new Pelicula("Interstellar", 8.7, 2014, 169, "Thriller sobre viajes en el tiempo.");
        
        Documental TheSocialDilema = new Documental("The Social Dilema", 7.6, 2020, 94, "Documental sobre el dilema de las redes sociales.");
        
        Serie DriveToSurvive = new Serie("Drive to Survive", 8.5, 2019, 50, 8, "Vista exclusiva a los equipos de Formula 1.");
        
        //Ejecución del método de iniciar sesión
        Usuario.IniciarSesion(Pablo);
        
        //Información de los objetos
        System.out.println("Pelicula: " + Interstellar.getNombre() + "\n" +
        "Calificacion IMDb: " + Interstellar.getCalificacion() + "\n" +
        "Lanzamiento: " + Interstellar.getLanzamiento()+ "\n" +
        "Duracion : " + Interstellar.getDuracionMinutos()+ "mins\n" +
        "Descripcion: " + Interstellar.getDescripcion()+ "\n"
        );
        
        System.out.println("Pelicula: " + TheSocialDilema.getNombre() + "\n" +
        "Calificacion IMDb: " + TheSocialDilema.getCalificacion() + "\n" +
        "Lanzamiento: " + TheSocialDilema.getLanzamiento()+ "\n" +
        "Duracion : " + TheSocialDilema.getDuracionMinutos()+ "mins\n" +
        "Descripcion: " + TheSocialDilema.getDescripcion()+ "\n"
        );
        
        System.out.println("Pelicula: " + DriveToSurvive.getNombre() + "\n" +
        "Calificacion IMDb: " + DriveToSurvive.getCalificacion() + "\n" +
        "Lanzamiento: " + DriveToSurvive.getLanzamiento()+ "\n" +
        "Temporadas: " + DriveToSurvive.getTemporadas()+ "\n" +
        "Descripcion: " + DriveToSurvive.getDescripcion()+ "\n"
        );
        */
    }
}