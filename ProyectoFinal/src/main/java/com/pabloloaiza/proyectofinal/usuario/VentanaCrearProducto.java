package com.pabloloaiza.proyectofinal.usuario;

import com.pabloloaiza.proyectofinal.modelo.Articulo;
import com.pabloloaiza.proyectofinal.modelo.Combo;
import com.pabloloaiza.proyectofinal.modelo.Producto;
import com.pabloloaiza.proyectofinal.red.Accion;
import com.pabloloaiza.proyectofinal.red.Respuesta;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Pablo Loaiza
 */
// Formulario para dar de alta un Articulo o un Combo (segun el flag esCombo).
// Si es combo, los articulos se eligen de una lista que viene del servidor,
// asi el combo queda ligado a productos reales por su id.
public class VentanaCrearProducto extends JFrame {

    private final ConexionServidor conexion;
    private final boolean esCombo;

    private final JTextField textoNombre = new JTextField();
    private final JTextField textoPrecio = new JTextField();
    private final JTextField textoDescripcion = new JTextField();
    private final DefaultListModel<Articulo> modeloArticulos = new DefaultListModel<>();
    private final JList<Articulo> listaArticulos = new JList<>(modeloArticulos);

    public VentanaCrearProducto(ConexionServidor conexion, boolean esCombo) {
        this.conexion = conexion;
        this.esCombo = esCombo;

        setTitle(esCombo ? "Nuevo Combo" : "Nuevo Producto");
        setSize(420, esCombo ? 380 : 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelDatos = new JPanel(new GridLayout(esCombo ? 2 : 3, 2));
        panelDatos.add(new JLabel("Nombre:"));
        panelDatos.add(textoNombre);
        panelDatos.add(new JLabel("Precio:"));
        panelDatos.add(textoPrecio);
        if (!esCombo) {
            panelDatos.add(new JLabel("Descripcion:"));
            panelDatos.add(textoDescripcion);
        }
        add(panelDatos, BorderLayout.NORTH);

        if (esCombo) {
            listaArticulos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            add(new JScrollPane(listaArticulos), BorderLayout.CENTER);
            cargarArticulos();
        }

        JButton botonGuardar = new JButton("Guardar");
        add(botonGuardar, BorderLayout.SOUTH);
        botonGuardar.addActionListener(e -> guardarProducto());

        setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void cargarArticulos() {
        Respuesta respuesta = conexion.enviar(Accion.LISTAR_ARTICULOS);
        if (!respuesta.isExito()) {
            JOptionPane.showMessageDialog(this, respuesta.getMensaje());
            return;
        }
        for (Articulo articulo : (ArrayList<Articulo>) respuesta.getContenido()) {
            modeloArticulos.addElement(articulo);
        }
    }

    private void guardarProducto() {
        String nombre = textoNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio.");
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(textoPrecio.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un numero.");
            return;
        }

        Producto nuevo;
        if (esCombo) {
            List<Articulo> seleccionados = listaArticulos.getSelectedValuesList();
            if (seleccionados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecciona al menos un articulo para el combo.");
                return;
            }
            ArrayList<Integer> ids = new ArrayList<>();
            for (Articulo articulo : seleccionados) {
                ids.add(articulo.getId());
            }
            nuevo = new Combo(nombre, precio, ids);
        } else {
            nuevo = new Articulo(nombre, precio, textoDescripcion.getText().trim());
        }

        Respuesta respuesta = conexion.enviar(Accion.CREAR_PRODUCTO, nuevo);
        JOptionPane.showMessageDialog(this, respuesta.getMensaje());
        if (respuesta.isExito()) {
            dispose();
        }
    }
}
