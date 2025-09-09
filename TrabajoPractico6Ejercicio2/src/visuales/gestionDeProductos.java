/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package visuales;

import classes.Producto;
import classes.Rubro;
import classes.Supermercado;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author emadupre
 */
public class gestionDeProductos extends javax.swing.JInternalFrame {

    /**
     * Creates new form gestionDeProductos
     */
    DefaultTableModel modelo = new DefaultTableModel();
    private Producto productoActual = null;

    private void habilitarGuardar() {
        btnGuardar.setEnabled(true);
    }

    private void nuevoProducto() {
        try {
            int codigo = Integer.parseInt(txtF_Codigo.getText());
            String descripcion = txtF_Descripcion.getText();
            double precio = Double.parseDouble(txtF_Precio.getText());
            int stock = (int) stockSpinner.getValue();

            Rubro rubro = null;

            String seleccionado = (String) comboBoxRubro.getSelectedItem();
            if ("Limpieza".equals(seleccionado)) {
                rubro = Rubro.LIMPIEZA;
            }
            if ("Perfumeria".equals(seleccionado)) {
                rubro = Rubro.PERFUMERIA;
            }
            if ("Comestible".equals(seleccionado)) {
                rubro = Rubro.COMESTIBLE;
            }

            productoActual = new Producto(codigo, descripcion, precio, rubro, stock);

            JOptionPane.showMessageDialog(this, "Producto Creado. Presiona Guardar para confirmar.");
            btnGuardar.setEnabled(true);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Complete correctamente los campos numericos.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al crear el producto" + e.getMessage());
        }
    }

    private void actualizarProducto() {
        int fila = tablaProductos.getSelectedRow();
        if (fila >= 0) {

            txtF_Codigo.setText(tablaProductos.getValueAt(fila, 0).toString());
            txtF_Descripcion.setText(tablaProductos.getValueAt(fila, 1).toString());
            txtF_Precio.setText(tablaProductos.getValueAt(fila, 2).toString());
            stockSpinner.setValue(Integer.parseInt(tablaProductos.getValueAt(fila, 4).toString()));

            String rubroTextoTabla = tablaProductos.getValueAt(fila, 3).toString();
            for (int i = 0; i < comboBoxRubro.getItemCount(); i++) {
                String rubroCombo = comboBoxRubro.getItemAt(i).toString();
                if (rubroCombo.equalsIgnoreCase(rubroTextoTabla)) {
                    comboBoxRubro.setSelectedIndex(i);
                    break;
                }
            }
            
            int codigo = Integer.parseInt(tablaProductos.getValueAt(fila, 0).toString());
            String descripcion = tablaProductos.getValueAt(fila, 1).toString();
            double precio = Double.parseDouble(tablaProductos.getValueAt(fila, 2).toString());
            int stock = Integer.parseInt(tablaProductos.getValueAt(fila, 4).toString());
            
            Rubro rubro = null;
            for(Rubro r : Rubro.values()){
                if(r.toString().equalsIgnoreCase(rubroTextoTabla)){
                    rubro = r;
                    break;
                }
            }
            
            productoActual = new Producto(codigo,descripcion,precio,rubro,stock);
            
            habilitarGuardar();

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para actualizar.");
        }
    }

    private void eliminarProducto() {
        int fila = tablaProductos.getSelectedRow();
        if (fila >= 0) {
            int codigo = Integer.parseInt(tablaProductos.getValueAt(fila, 0).toString());
            int opcion = JOptionPane.showConfirmDialog(this, "Desea eliminar el producto?", "Eliminar", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                DeTodoSA.supermercado.eliminarProducto(codigo);
                ((DefaultTableModel) tablaProductos.getModel()).removeRow(fila);
                btnGuardar.setEnabled(false);
                productoActual = null;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar");
        }
    }

    private void guardarProducto() {

        if (productoActual == null) {
            JOptionPane.showMessageDialog(this, "Primero cree un producto con el botón Nuevo.");
            return;
        }

        try {
            
            int codigo = Integer.parseInt(txtF_Codigo.getText());
            String descripcion = txtF_Descripcion.getText();
            double precio = Double.parseDouble(txtF_Precio.getText());
            int stock = (int) stockSpinner.getValue();
            
            Rubro rubro = null;
            String rubroTexto = (String) comboBoxRubro.getSelectedItem();
            for(Rubro r : Rubro.values()){
                if(r.toString().equalsIgnoreCase(rubroTexto)){
                    rubro = r;
                    break;
                }
            }
            
            productoActual.setCodigo(codigo);
            productoActual.setDescripcion(descripcion);
            productoActual.setPrecio(precio);
            productoActual.setStock(stock);
            productoActual.setRubro(rubro);
            
            DeTodoSA.supermercado.sobreescribirProducto(productoActual);
            DeTodoSA.supermercado.anadirProducto(productoActual);

            JOptionPane.showMessageDialog(this, "Producto guardado en Supermercado DeTodo - S.A");

            rellenarTabla(new ArrayList<>(DeTodoSA.supermercado.getCatalogo()));
            

            txtF_Codigo.setText("");
            txtF_Descripcion.setText("");
            txtF_Precio.setText("");
            stockSpinner.setValue(0);
            comboBoxRubro.setSelectedIndex(-1);
            btnGuardar.setEnabled(false);
            productoActual = null;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar." + e.getMessage());
            e.printStackTrace();
        }

        //Se sobreescribe por si ya existe el producto.
        btnGuardar.setEnabled(false);

    }

    public void rellenarTabla(ArrayList<Producto> productos) {
        try {
            modelo = (DefaultTableModel) tablaProductos.getModel();
            modelo.setRowCount(0);
            for (Producto p : productos) {
                modelo.addRow(new Object[]{p.getCodigo(), p.getDescripcion(), p.getPrecio(), p.getRubro(), p.getStock()});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public gestionDeProductos(Supermercado supermercado) {
        initComponents();
        DeTodoSA.rellenarCabecerasTablas(tablaProductos);
        DeTodoSA.rellenarComboBox(comboBoxRubro);
        DeTodoSA.rellenarComboBox(comboBoxFiltrarCategoria);
        rellenarTabla(new ArrayList<>(supermercado.getCatalogo()));
        this.setClosable(true);
        this.setResizable(true);
        this.setMaximizable(true);
        this.setIconifiable(true);

        btnGuardar.setEnabled(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtGestionProductos = new javax.swing.JLabel();
        txtFiltrarCategoria = new javax.swing.JLabel();
        comboBoxFiltrarCategoria = new javax.swing.JComboBox<>();
        paneProductos = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        panelProductos = new javax.swing.JPanel();
        txtCodigo = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JLabel();
        txtRubro = new javax.swing.JLabel();
        txtStock = new javax.swing.JLabel();
        stockSpinner = new javax.swing.JSpinner();
        comboBoxRubro = new javax.swing.JComboBox<>();
        txtF_Precio = new javax.swing.JTextField();
        txtF_Descripcion = new javax.swing.JTextField();
        txtF_Codigo = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        setTitle("De todo S.A - Productos");

        txtGestionProductos.setFont(new java.awt.Font("URW Gothic", 0, 18)); // NOI18N
        txtGestionProductos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtGestionProductos.setText("Gestión de Productos");

        txtFiltrarCategoria.setFont(new java.awt.Font("URW Gothic", 0, 13)); // NOI18N
        txtFiltrarCategoria.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtFiltrarCategoria.setText("Filtrar por Categoría:");

        comboBoxFiltrarCategoria.setFont(new java.awt.Font("URW Gothic", 0, 13)); // NOI18N
        comboBoxFiltrarCategoria.setToolTipText("Filtre por Categoria");
        comboBoxFiltrarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxFiltrarCategoriaActionPerformed(evt);
            }
        });

        paneProductos.setFont(new java.awt.Font("URW Gothic", 0, 13)); // NOI18N

        tablaProductos.setFont(new java.awt.Font("URW Gothic", 0, 13)); // NOI18N
        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        paneProductos.setViewportView(tablaProductos);

        panelProductos.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtCodigo.setFont(new java.awt.Font("URW Gothic", 0, 13)); // NOI18N
        txtCodigo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtCodigo.setText("Codigo:");

        txtDescripcion.setFont(new java.awt.Font("URW Gothic", 0, 13)); // NOI18N
        txtDescripcion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtDescripcion.setText("Descripcion:");

        txtPrecio.setFont(new java.awt.Font("URW Gothic", 0, 13)); // NOI18N
        txtPrecio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtPrecio.setText("Precio:");

        txtRubro.setFont(new java.awt.Font("URW Gothic", 0, 13)); // NOI18N
        txtRubro.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtRubro.setText("Rubro:");

        txtStock.setFont(new java.awt.Font("URW Gothic", 0, 13)); // NOI18N
        txtStock.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtStock.setText("Stock:");

        stockSpinner.setFont(new java.awt.Font("URW Gothic", 0, 13)); // NOI18N

        comboBoxRubro.setFont(new java.awt.Font("URW Gothic", 0, 13)); // NOI18N

        txtF_Precio.setFont(new java.awt.Font("URW Gothic", 0, 13)); // NOI18N

        txtF_Descripcion.setFont(new java.awt.Font("URW Gothic", 0, 13)); // NOI18N

        txtF_Codigo.setFont(new java.awt.Font("URW Gothic", 0, 13)); // NOI18N

        javax.swing.GroupLayout panelProductosLayout = new javax.swing.GroupLayout(panelProductos);
        panelProductos.setLayout(panelProductosLayout);
        panelProductosLayout.setHorizontalGroup(
            panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtStock, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtRubro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(comboBoxRubro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(stockSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtF_Precio)
                    .addComponent(txtF_Descripcion)
                    .addComponent(txtF_Codigo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelProductosLayout.setVerticalGroup(
            panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductosLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo)
                    .addComponent(txtF_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDescripcion)
                    .addComponent(txtF_Descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrecio)
                    .addComponent(txtF_Precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRubro)
                    .addComponent(comboBoxRubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStock)
                    .addComponent(stockSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        btnNuevo.setFont(new java.awt.Font("URW Gothic", 0, 13)); // NOI18N
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/icons8-agregar-a-carrito-de-compras-48.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setFont(new java.awt.Font("URW Gothic", 0, 13)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnActualizar.setFont(new java.awt.Font("URW Gothic", 0, 13)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/icons8-aprobar-y-actualizar-48.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("URW Gothic", 0, 13)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/eliminar.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtGestionProductos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelProductos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(paneProductos))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 246, Short.MAX_VALUE)
                .addComponent(txtFiltrarCategoria)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboBoxFiltrarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(203, 203, 203))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNuevo)
                .addGap(18, 18, 18)
                .addComponent(btnGuardar)
                .addGap(18, 18, 18)
                .addComponent(btnActualizar)
                .addGap(18, 18, 18)
                .addComponent(btnEliminar)
                .addGap(73, 73, 73))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(txtGestionProductos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFiltrarCategoria)
                    .addComponent(comboBoxFiltrarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(paneProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnGuardar)
                    .addComponent(btnActualizar)
                    .addComponent(btnEliminar))
                .addGap(0, 19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        nuevoProducto();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardarProducto();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        actualizarProducto();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarProducto();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void comboBoxFiltrarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxFiltrarCategoriaActionPerformed
        String seleccionado = (String) comboBoxFiltrarCategoria.getSelectedItem();

        ArrayList<Producto> filtrado;
        switch (seleccionado) {
            case "Limpieza":
                filtrado = DeTodoSA.supermercado.buscarProductosRubro(Rubro.LIMPIEZA);
                break;
            case "Comestible":
                filtrado = DeTodoSA.supermercado.buscarProductosRubro(Rubro.COMESTIBLE);
                break;
            case "Perfumeria":
                filtrado = DeTodoSA.supermercado.buscarProductosRubro(Rubro.PERFUMERIA);
                break;
            default:
                filtrado = new ArrayList();
                break;
        }
        rellenarTabla(filtrado);

    }//GEN-LAST:event_comboBoxFiltrarCategoriaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> comboBoxFiltrarCategoria;
    private javax.swing.JComboBox<String> comboBoxRubro;
    private javax.swing.JScrollPane paneProductos;
    private javax.swing.JPanel panelProductos;
    private javax.swing.JSpinner stockSpinner;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JLabel txtCodigo;
    private javax.swing.JLabel txtDescripcion;
    private javax.swing.JTextField txtF_Codigo;
    private javax.swing.JTextField txtF_Descripcion;
    private javax.swing.JTextField txtF_Precio;
    private javax.swing.JLabel txtFiltrarCategoria;
    private javax.swing.JLabel txtGestionProductos;
    private javax.swing.JLabel txtPrecio;
    private javax.swing.JLabel txtRubro;
    private javax.swing.JLabel txtStock;
    // End of variables declaration//GEN-END:variables
}
