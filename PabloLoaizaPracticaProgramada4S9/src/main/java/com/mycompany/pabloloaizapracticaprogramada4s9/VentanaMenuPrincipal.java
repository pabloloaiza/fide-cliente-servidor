/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.pabloloaizapracticaprogramada4s9;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Pablo Loaiza
 */
public class VentanaMenuPrincipal extends JFrame {

    //Componentes de la ventana
    private JLabel labelBienvenida;

    //Constructor: recibe el usuario que inició sesión para saludarlo
    public VentanaMenuPrincipal(Usuario usuario) {
        setTitle("Fideflix - Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        //Mensaje de bienvenida con el nombre del usuario
        labelBienvenida = new JLabel("¡Bienvenido " + usuario.getNombre() + "!");
        labelBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
        labelBienvenida.setBounds(0, 70, 400, 30);
        panel.add(labelBienvenida);

        add(panel);
    }
}
