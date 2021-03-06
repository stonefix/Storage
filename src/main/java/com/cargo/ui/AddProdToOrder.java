/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cargo.ui;

import com.cargo.storage.dao.OrderDAO;
import com.cargo.storage.dao.StorehouseDAO;
import com.cargo.storage.model.Products;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author yura
 */
public class AddProdToOrder extends javax.swing.JFrame {

    public OrderDAO orddao;
    public StorehouseDAO strhdao;
    public List<Products> prodstore;
    public ArrayList<Integer> countOfProd;
    public int idStorehouse;
    int idOrder;
    responordFrame rf;
    public AddProdToOrder(responordFrame rf, int idStorehouse, int idOrder) {
        try {
            this.rf = rf;
            this.idOrder = idOrder;
            this.idStorehouse = idStorehouse;
            countOfProd = new ArrayList<Integer>();
            initComponents();
            strhdao = new StorehouseDAO();
            orddao = new OrderDAO();
            prodstore = strhdao.getAllProductsOnStore(idStorehouse);
            for(int i=0; i< prodstore.size();i++){
                jcmbProd.addItem(prodstore.get(i).getElement().getName());
                countOfProd.add(prodstore.get(i).getCount());
            }
        } catch (Exception ex) {
            Logger.getLogger(AddProdToOrder.class.getName()).log(Level.SEVERE, null, ex);
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

        jtfCount = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jcmbProd = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jbtnAdd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jtfCount.setText(" ");
        jtfCount.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel1.setText("????????????????????:");

        jcmbProd.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));

        jLabel3.setText("??????????????:");

        jbtnAdd.setText("????????????????");
        jbtnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnAdd)
                    .addComponent(jtfCount, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcmbProd, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcmbProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(jbtnAdd)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAddActionPerformed
       try {
        int ok = jcmbProd.getSelectedIndex()-1;
        String valCount = jtfCount.getText().trim();
        
        int newCount = Integer.parseInt(valCount);
        int oldCount = countOfProd.get(ok);
        
        if(newCount > oldCount){            
            JOptionPane.showMessageDialog(this, "???? ???????????? ?????? ?????????????? ????????????", "????????????!", JOptionPane.ERROR_MESSAGE);       
        } else {
          
                strhdao.setCountProdOnStore(idStorehouse, 
                        prodstore.get(ok).getElement().getId(),
                        oldCount - newCount);
                
                orddao.addProdToOrder(idOrder, prodstore.get(ok).getElement().getId(), newCount);   
                orddao.changeState(idOrder, idStorehouse, 2);
                orddao.closeConnection();
                strhdao.closeConnection();
                dispose();
                
                rf.refreshProdsOfOrderView(idOrder);}
            } catch (SQLException ex) {
                Logger.getLogger(AddProdToOrder.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NumberFormatException exc) {
            JOptionPane.showMessageDialog(
                            rf,
                            "????????????: "
                                            + exc.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
		
    } 
            
        
       
        
        
    }//GEN-LAST:event_jbtnAddActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton jbtnAdd;
    private javax.swing.JComboBox<String> jcmbProd;
    private javax.swing.JTextField jtfCount;
    // End of variables declaration//GEN-END:variables
}
