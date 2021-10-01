/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cargo.ui;

import com.cargo.storage.dao.OrderDAO;
import com.cargo.storage.dao.ProductDAO;
import com.cargo.storage.dao.StorehouseDAO;
import com.cargo.storage.model.Products;
import com.cargo.storage.model.Storehouse;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author yura
 */
public class ChangeState extends javax.swing.JFrame {

    /**
     * Creates new form ChangeState
     */
     List<Storehouse> liststores;
     int index;
     int indexState;
    // OrderDAO orddao;
     logistFrame lf;
     static int indexStore = 0;
     static int idState = 0;
      StorehouseDAO strh;
      ProductDAO proddao;
      int indexOrd;
    public ChangeState(logistFrame lf) {
        try {
            this.indexOrd = lf.getIndexOrd();
            this.lf = lf;
            initComponents();
            strh = new StorehouseDAO();
            proddao = new ProductDAO();
            liststores = strh.getAllStorehouses();
            jcmbStore.addItem(" ");
            for (int i=0; i<liststores.size();i++){
                jcmbStore.addItem(liststores.get(i).getName());                    
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ChangeState.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        idStoreFromCmb(jcmbState);
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jcmbState = new javax.swing.JComboBox<>();
        jcmbStore = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jtfFinish = new javax.swing.JTextField();
        jlbFinish = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jcmbState.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Выпуск", "Отгрузка", "В пути", "Получение", "Выполнен" }));

        jLabel1.setText("Статус:");

        jLabel2.setText("Склад:");

        jButton1.setText("Изменить");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jtfFinish.setVisible(false);
        jtfFinish.setText(" ");

        jlbFinish.setVisible(false);
        jlbFinish.setText("Завершение:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(91, 91, 91)
                        .addComponent(jcmbState, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jlbFinish))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtfFinish)
                            .addComponent(jcmbStore, 0, 148, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcmbState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jcmbStore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfFinish, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbFinish))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void idStoreFromCmb(JComboBox fromCmbBox ) {
            fromCmbBox.addItemListener(new ItemListener(){      
                @Override
                public void itemStateChanged(ItemEvent e){
                    
                    if ((e.getStateChange() == ItemEvent.SELECTED)){                        
                        indexStore = fromCmbBox.getSelectedIndex();       
                        if(fromCmbBox.getSelectedItem().toString().equalsIgnoreCase("Выполнен") ){
                            jlbFinish.setVisible(true);
                            jtfFinish.setVisible(true);
                        }
                        else {
                            
                            jlbFinish.setVisible(false);
                            jtfFinish.setVisible(false);
                            jtfFinish.setText("");
                        }
                    }
                } 
            });
        } 
    
    
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        index = jcmbStore.getSelectedIndex();
        indexState = jcmbState.getSelectedIndex() + 1;
       
        int indexOfStore;
        try { 
        OrderDAO orddao = new OrderDAO();
        if (index>0){
                   
                if (indexState == 5){
                    String date = jtfFinish.getText().trim();
                    if (date.matches("\\d{4}-\\d{2}-\\d{2}")){
                        indexOfStore = index;
                        
                        orddao.changeState(indexOrd, indexOfStore, indexState); 
                        orddao.updateFinishDate(indexOrd, date);
                        
                        List<Products> list = orddao.getProductsOfOrder(indexOrd);
                        
                        
                        for (int i =0; i< list.size(); i++){
                        
                            if(strh.checkExistProd(list.get(i).getElement().getId(), indexOfStore)){
                                strh.addExistProd(list.get(i).getCount(), list.get(i).getElement().getId(), indexOfStore);
                            } else {
                                proddao.addProdToStorehouse(list.get(i).getElement().getId(), indexOfStore, list.get(i).getCount());
                            }
                        
                        }
                        
                          
                        lf.refreshOrderView();
                        lf.refreshStateField(indexOrd);
                        
                        JOptionPane.showMessageDialog(this, "Товар передан на склад." , "Товар передан на склад.",
					JOptionPane.ERROR_MESSAGE);
                        
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Ошибка: Дата должна быть в формате ГГГГ-ММ-ДД" , "Дата должна быть в формате ГГГГ-ММ-ДД",
					JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                 
                    indexOfStore = index;
                    orddao.changeState(indexOrd, indexOfStore, indexState); 
                    lf.refreshStateField(indexOrd);
                    dispose();
                }        
        } else {
         
                orddao.changeState(indexOrd,  indexState);
                lf.refreshStateField(indexOrd);
                dispose();
            
        }
    } catch (SQLException ex) {
          Logger.getLogger(ChangeState.class.getName()).log(Level.SEVERE, null, ex);
      } catch (Exception ex) {
          Logger.getLogger(ChangeState.class.getName()).log(Level.SEVERE, null, ex);
      }
        
    }//GEN-LAST:event_jButton1ActionPerformed
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JComboBox<String> jcmbState;
    private javax.swing.JComboBox<String> jcmbStore;
    private javax.swing.JLabel jlbFinish;
    private javax.swing.JTextField jtfFinish;
    // End of variables declaration//GEN-END:variables
}
