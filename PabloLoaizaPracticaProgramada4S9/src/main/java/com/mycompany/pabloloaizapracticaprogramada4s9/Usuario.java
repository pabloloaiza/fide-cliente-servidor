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

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String apellido;
    private String correo;
    private String contrasena;

    @Override
    public int compareTo(Usuario otroUsuario) {
        return this.nombre.compareTo(otroUsuario.getNombre());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario otro = (Usuario) obj;
        return nombre.equals(otro.nombre)
                && apellido.equals(otro.apellido)
                && correo.equals(otro.correo)
                && contrasena.equals(otro.contrasena);
    }

    @Override
    public String toString() {
        return nombre;
    }

    public static void IniciarSesion(Usuario nombre) {
        System.out.println("¡Bienvenido " + nombre.getNombre() + "!\n");
    }

    //Método sincronizado para evitar que dos hilos escriban el archivo a la vez
    public static synchronized void GuardarColeccion(ArrayList<Usuario> usuarios) {
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

    public static synchronized ArrayList<Usuario> LeerColeccion() {
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

    public Usuario(String nombre, String apellido, String correo, String contrasena) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}