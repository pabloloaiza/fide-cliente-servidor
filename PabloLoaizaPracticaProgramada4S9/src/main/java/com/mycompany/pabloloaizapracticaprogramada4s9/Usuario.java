 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pabloloaizapracticaprogramada4s9;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Pablo Loaiza
 */
public class Usuario implements Comparable<Usuario>, Serializable {
    //Atributos
    //Para la tercera prácitca programada, se cambió el atribud
    private String nombre;
    private String apellido;
    private String correo;
    private String contrasena;
    
    //Implementación de método abstracto de la interface
    @Override
    public int compareTo(Usuario otroUsuario) {
        return this.nombre.compareTo(otroUsuario.getNombre());    
    }
    
    //Método para poder eliminar los objetos con el mismo nombre en el main
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Usuario otro = (Usuario) obj;

        return nombre.equals(otro.nombre)
                && apellido.equals(otro.apellido)
                && correo.equals(otro.correo)
                && contrasena.equals(otro.contrasena);
    }
    
    //Método para poder devolver el nombre en el main con System.out.println
    @Override
    public String toString() {
        return nombre;
    }
    
    //Método para dar bienvenida al usuario
    public static void IniciarSesion(Usuario nombre){
        System.out.println("¡Bienvenido " + nombre.getNombre() + "!\n");
    }
    
    //Método para guardar TODA la colección de usuarios en un archivo
    public static void GuardarColeccion(ArrayList<Usuario> usuarios) {
        try {
            FileOutputStream archivoUsuarios = new FileOutputStream("Usuarios.dat");
            ObjectOutputStream output = new ObjectOutputStream(archivoUsuarios);
            output.writeObject(usuarios);
            output.close();
            archivoUsuarios.close();
        } catch (IOException ex) {
            System.out.println("Hubo una exepcion al guardar: " + ex.getMessage());
        }
    }

    //Método para leer la colección de usuarios desde el archivo
    //Si el archivo todavía no existe, se devuelve una lista vacía
    public static ArrayList<Usuario> LeerColeccion() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try {
            FileInputStream archivoUsuarios = new FileInputStream("Usuarios.dat");
            ObjectInputStream input = new ObjectInputStream(archivoUsuarios);
            usuarios = (ArrayList<Usuario>) input.readObject();
            input.close();
            archivoUsuarios.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Hubo una exepcion al leer: " + ex.getMessage());
        }
        return usuarios;
    }
    
    //Constructor
    public Usuario(String nombre, String apellido, String correo, String contrasena) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
    }
    
    //Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
