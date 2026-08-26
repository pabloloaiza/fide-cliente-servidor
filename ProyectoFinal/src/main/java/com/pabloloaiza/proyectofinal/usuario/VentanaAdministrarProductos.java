package com.pabloloaiza.proyectofinal.usuario;

import com.pabloloaiza.proyectofinal.modelo.Articulo;
import com.pabloloaiza.proyectofinal.modelo.Producto;
import com.pabloloaiza.proyectofinal.red.Accion;
import com.pabloloaiza.proyectofinal.red.Respuesta;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Pablo Loaiza
 */
// Casos de uso del cajero: editar un producto ya creado y marcarlo como
// agotado o disponible. Muestra todos los productos, incluidos los agotados.
public class VentanaAdministrarProductos extends JFrame {

    private final ConexionServidor conexion;
    private final DefaultListModel<Producto> modelo = new DefaultListModel<>();
    private final JList<Producto> lista = new JList<>(modelo);

    public VentanaAdministrarProductos(ConexionServidor conexion) {
        this.conexion = conexion;

        setTitle("Administrar Menu");
        setSize(520, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(lista), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(1, 4));
        JButton botonEditar = new JButton("Editar");
        JButton botonAgotado = new JButton("Marcar Agotado");
        JButton botonDisponible = new JButton("Marcar Disponible");
        JButton botonRefrescar = new JButton("Refrescar");
        panelBotones.add(botonEditar);
        panelBotones.add(botonAgotado);
        panelBotones.add(botonDisponible);
        panelBotones.add(botonRefrescar);
        add(panelBotones, BorderLayout.SOUTH);

        botonEditar.addActionListener(e -> editarSeleccionado());
        botonAgotado.addActionListener(e -> cambiarDisponibilidad(false));
        botonDisponible.addActionListener(e -> cambiarDisponibilidad(true));
        botonRefrescar.addActionListener(e -> cargarProductos());

        cargarProductos();
        setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void cargarProductos() {
        Respuesta respuesta = conexion.enviar(Accion.LISTAR_PRODUCTOS);
        if (!respuesta.isExito()) {
            JOptionPane.showMessageDialog(this, respuesta.getMensaje());
            return;
        }
        modelo.clear();
        for (Producto producto : (ArrayList<Producto>) respuesta.getContenido()) {
            modelo.addElement(producto);
        }
    }

    // Pido los datos nuevos con dialogos simples y mando el objeto completo al servidor
    private void editarSeleccionado() {
        Producto producto = lista.getSelectedValue();
        if (producto == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto de la lista.");
            return;
        }

        String nombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", producto.getNombre());
        if (nombre == null || nombre.trim().isEmpty()) {
            return;
        }
        String textoPrecio = JOptionPane.showInputDialog(this, "Nuevo precio:", producto.getPrecio());
        if (textoPrecio == null) {
            return;
        }
        double precio;
        try {
            precio = Double.parseDouble(textoPrecio.trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un numero.");
            return;
        }

        producto.setNombre(nombre.trim());
        producto.setPrecio(precio);
        if (producto instanceof Articulo articulo) {
            String descripcion = JOptionPane.showInputDialog(this, "Nueva descripcion:", articulo.getDescripcion());
            if (descripcion != null) {
                articulo.setDescripcion(descripcion.trim());
            }
        }

        Respuesta respuesta = conexion.enviar(Accion.EDITAR_PRODUCTO, producto);
        JOptionPane.showMessageDialog(this, respuesta.getMensaje());
        cargarProductos();
    }

    private void cambiarDisponibilidad(boolean disponible) {
        Producto producto = lista.getSelectedValue();
        if (producto == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto de la lista.");
            return;
        }
        Respuesta respuesta = conexion.enviar(Accion.CAMBIAR_DISPONIBILIDAD, producto.getId(), disponible);
        JOptionPane.showMessageDialog(this, respuesta.getMensaje());
        cargarProductos();
    }
}
