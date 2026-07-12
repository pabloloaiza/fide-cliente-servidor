/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pabloloaiza.proyectofinal;

import javax.swing.*;

//other
import java.awt.*;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
/**
 *
 * @author Pablo Loaiza
 */
public class VentanaMenuPrincipal extends JFrame {
    private JButton botonIngresar;
    private JButton botonCrearUsuario;

    public VentanaMenuPrincipal() {
        setTitle("Menu Principal");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));
        botonIngresar = new JButton("Ingresar");
        botonCrearUsuario = new JButton("Crear Usuario");
        add(botonIngresar);
        add(botonCrearUsuario);
        
        //botonIngresar.addActionListener(e -> iniciarSesion());
        botonCrearUsuario.addActionListener(e -> {
            VentanaCrearUsuario crear = new VentanaCrearUsuario();
            crear.setVisible(true);
        });
        /*
        setVisible(true); 
    } */
    /*
    private void iniciarSesion() {
        try {
            String respuesta = entrada.readLine();
            if (respuesta.equals("OK")) {
                VentanaMenuPrincipal menu =
                        new VentanaMenuPrincipal(txtUsuario.getText());
                menu.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Usuario o contraseña incorrectos.");
            }
            socket.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "No fue posible conectar con el servidor.");
        }
    }
    */
    setVisible(true); 
    }
}
