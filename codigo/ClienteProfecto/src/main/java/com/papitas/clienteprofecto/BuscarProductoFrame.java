/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.papitas.clienteprofecto;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lenovo
 */
public class BuscarProductoFrame extends javax.swing.JFrame {
    private DefaultTableModel modelo;
    private List<Producto> productos;
    /**
     * Creates new form BuscarProductoFrame
     */
    public BuscarProductoFrame() {
        this.productos = new ArrayList();
        initComponents();
        this.setupComponents();
    }

    private void setupComponents(){
        this.setLocationRelativeTo(null);
        
        this.generarTabla();
    }
            
    private void generarTabla(){
        modelo = new DefaultTableModel();
        modelo.addColumn("Comercio");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        modelo.addColumn("Oferta");
        this.jtProductos.setModel(modelo);
    }
    
    private void cargarTabla(){
        this.generarTabla();
        String[] campos = new String[4];
        for(Producto p : this.productos){
            campos[0] = p.getComercio();
            campos[1] = p.getNombre();
            campos[2] = Double.toString(p.getPrecio());
System.out.println((p.getPrecio()*p.getOferta()));
            double descuento = p.getPrecio() - (p.getPrecio()*p.getOferta());
            campos[3] = Double.toString(descuento);
            this.modelo.addRow(campos);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jbBuscar = new javax.swing.JButton();
        jtfBuscar = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtProductos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(470, 400));

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(153, 0, 51));
        jPanel2.setPreferredSize(new java.awt.Dimension(317, 120));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Buscar producto");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, -1));

        jbBuscar.setText("Buscar");
        jbBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscarActionPerformed(evt);
            }
        });
        jPanel2.add(jbBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 50, -1, -1));
        jPanel2.add(jtfBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(129, 50, 170, -1));

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(153, 0, 51));
        jPanel5.setPreferredSize(new java.awt.Dimension(400, 20));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel5, java.awt.BorderLayout.PAGE_END);

        jtProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Comercio", "Nombre", "Precio", "Oferta"
            }
        ));
        jScrollPane1.setViewportView(jtProductos);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 400, 190));

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarActionPerformed
        HttpURLConnection connection;
        BufferedReader reader;
        String line;
        String nombre = this.jtfBuscar.getText().replace(" ", "%20");
        StringBuffer responseContent = new StringBuffer();
        try {
            URL url = new URL("http://localhost:8080/products/"+nombre);
            connection = (HttpURLConnection)url.openConnection();
            
            //Request setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            int status = connection.getResponseCode();
            System.out.println(status);
            
            if(status > 299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                
            }else{
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }
            
            while((line = reader.readLine()) != null){
                responseContent.append(line);
            }
            reader.close();
            String respuesta = responseContent.toString();
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<List<Producto>>() {}.getType();
            this.productos = gson.fromJson(respuesta, type);
            this.cargarTabla();
        } catch (MalformedURLException ex ) {
            ex.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_jbBuscarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BuscarProductoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BuscarProductoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BuscarProductoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BuscarProductoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BuscarProductoFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbBuscar;
    private javax.swing.JTable jtProductos;
    private javax.swing.JTextField jtfBuscar;
    // End of variables declaration//GEN-END:variables
}
