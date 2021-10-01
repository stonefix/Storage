/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cargo.ui;

import com.cargo.storage.dao.StorehouseDAO;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author yura
 */
public class ChangeCount extends javax.swing.JDialog {

    /**
     * Creates new form ChangeCount
     */
    StorehouseDAO strh;
    workerFrame wf;
    int idStore;
    int idProd;
    int count;
    public ChangeCount(workerFrame wf, int idStore, int idProd, int count) throws Exception {
        this.wf = wf;
        this.idStore = idStore;
        this.idProd = idProd;
        this.count = count;
        initComponents();
        strh = new StorehouseDAO();
        jtfCount.setText(String.valueOf(count));
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
        jbtnOk = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jtfCount.setText(" ");

        jLabel1.setText("Количество ");

        jbtnOk.setText("ОК");
        jbtnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbtnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfCount, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtfCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jbtnOk)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnOkActionPerformed
        try{
        count = Integer.parseInt(jtfCount.getText());
        strh.setCountProdOnStore(idStore, idProd, count);
        strh.closeConnection();
        setVisible(false);
        wf.refreshProdOnStoreView(idStore);
        } catch (NumberFormatException exc) {
            JOptionPane.showMessageDialog(
                            wf,
                            "Ошибка: "
                                            + exc.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
		
    } catch (SQLException exc){
            JOptionPane.showMessageDialog(
                            wf,
                            "Ошибка: "
                                            + exc.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
		
    }
        
    }//GEN-LAST:event_jbtnOkActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton jbtnOk;
    private javax.swing.JTextField jtfCount;
    // End of variables declaration//GEN-END:variables
}