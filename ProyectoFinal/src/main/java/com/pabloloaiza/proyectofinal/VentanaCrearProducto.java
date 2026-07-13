package com.pabloloaiza.proyectofinal;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Pablo Loaiza
 */
// Formulario para dar de alta un Articulo o un Combo (segun el flag esCombo).
// Se abre desde la ventana del Cajero y guarda en el archivo de productos.
public class VentanaCrearProducto extends JFrame {

    private final boolean esCombo;

    private JTextField textoNombre;
    private JTextField textoPrecio;
    private JTextField textoExtra; // descripcion (articulo) o lista de articulos (combo)

    public VentanaCrearProducto(Cajero cajero, boolean esCombo) {
        this.esCombo = esCombo;

        setTitle(esCombo ? "Nuevo Combo" : "Nuevo Producto");
        setSize(320, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Nombre:"));
        textoNombre = new JTextField();
        add(textoNombre);

        add(new JLabel("Precio:"));
        textoPrecio = new JTextField();
        add(textoPrecio);

        add(new JLabel(esCombo ? "Articulos (separados por coma):" : "Descripcion:"));
        textoExtra = new JTextField();
        add(textoExtra);

        JButton botonGuardar = new JButton("Guardar");
        add(botonGuardar);
        botonGuardar.addActionListener(e -> guardarProducto());

        setVisible(true);
    }

    private void guardarProducto() {
        String nombre = textoNombre.getText().trim();
        String textoExtraValor = textoExtra.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio.");
            return;
        }

        // Valido que el precio sea un numero valido
        double precio;
        try {
            precio = Double.parseDouble(textoPrecio.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un numero.");
            return;
        }

        ArrayList<Producto> productos = GestorArchivos.cargarProductos();

        if (esCombo) {
            // Separo la lista de articulos escrita por el cajero
            ArrayList<String> articulos = new ArrayList<>();
            for (String parte : textoExtraValor.split(",")) {
                if (!parte.trim().isEmpty()) {
                    articulos.add(parte.trim());
                }
            }
            productos.add(new Combo(nombre, precio, articulos));
        } else {
            productos.add(new Articulo(nombre, precio, textoExtraValor));
        }

        GestorArchivos.guardarProductos(productos);
        JOptionPane.showMessageDialog(this, (esCombo ? "Combo" : "Producto") + " guardado.");
        dispose();
    }
}
