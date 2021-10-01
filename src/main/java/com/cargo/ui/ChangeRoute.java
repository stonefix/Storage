/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cargo.ui;

import com.cargo.comboboxmodels.RoleCmbModel;
import com.cargo.storage.dao.OrderDAO;
import com.cargo.storage.dao.StorehouseDAO;
import com.cargo.storage.model.Path;
import com.cargo.storage.model.Storehouse;
import static com.cargo.ui.ChangeRoute.myFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.UIManager;

/**
 *
 * @author yura
 */
public class ChangeRoute extends JFrame{
    
    static ChangeRoute myFrame;
    JPanel mainPanel;
    JScrollPane scrollPane;
    OrderDAO orddao;
    
    public ChangeRoute(){
        
    }
    
    public static void createAndShowGUI(int idOrd, int count, String[] roleItems, logistFrame lf) throws Exception {
        
         
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(userFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(userFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(userFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(userFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
       
        myFrame = new ChangeRoute();
        myFrame.prepareUI(count, roleItems, idOrd, lf);
        myFrame.pack();
        myFrame.setVisible(true);
    }
    
    static ArrayList<Integer> idStoreFrom; 
    static ArrayList<Integer> idStoreTo;
    static ArrayList<Integer> idRoleFrom;
    static ArrayList<Integer> idRoleTo;
    
    private void prepareUI(int rows, String[] roleItems, int idOrd, logistFrame lf) throws Exception {
        mainPanel = new JPanel();
        scrollPane = new JScrollPane(mainPanel);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        orddao = new OrderDAO();
        
        idStoreFrom = new ArrayList<>();
        idStoreTo = new ArrayList<>();
        idRoleFrom = new ArrayList<>();
        idRoleTo = new ArrayList<>();
        
        for (int i=0; i<rows; i++){
            mainPanel.add(new subPanel(roleItems, orddao, idOrd, i));       
        }
       
        JButton buttonAdd = new JButton("Добавить маршрут");
        
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int newIndex = mainPanel.getComponentCount();
                    mainPanel.add(new subPanel(roleItems, orddao, idOrd, newIndex));
                    idStoreFrom.add(0);
                    idStoreTo.add(0);
                    idRoleFrom.add(0);
                    idRoleTo.add(0);
                    scrollPane.revalidate();
                    myFrame.pack();
                    
                    
                } catch (Exception ex) {
                    Logger.getLogger(ChangeRoute.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
           
        });
        
        JButton buttonAddToOrd = new JButton("Изменить маршрут");
        buttonAddToOrd.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){           
                
                try {
                    System.out.println(idStoreFrom);
                    System.out.println(idRoleFrom);
                    System.out.println(idStoreTo);
                    System.out.println(idRoleTo);
                    orddao.deletePath(idOrd);
                    for (int i=0;i < idStoreFrom.size(); i++){   
                        if(idStoreFrom.get(i) != null){
                            orddao.insertPath(idOrd, idRoleFrom.get(i)+1, idStoreFrom.get(i)+1, 
                            idRoleTo.get(i)+1, idStoreTo.get(i)+1);        
                        }
                    } 
                    lf.pathView();
                    dispose();
                } catch (SQLException ex) {
                    Logger.getLogger(ChangeRoute.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
        });
        
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonAdd, BorderLayout.PAGE_START);
        getContentPane().add(buttonAddToOrd, BorderLayout.PAGE_END);
       
    }

   
    private class subPanel extends JPanel {
         
        subPanel me;
        
        public subPanel(String[] roleItems, OrderDAO orderdao, int idOrd, int index) throws Exception {
            super();
            me = this;        
            System.out.println(index);
            
            StorehouseDAO strh = new StorehouseDAO(); 
            
            List<Path> listpath = orderdao.getPath(idOrd);
            
            List<Storehouse> liststores = strh.getAllStorehouses();
            
            JComboBox fromCmbBox = new JComboBox();
            JComboBox toCmbBox = new JComboBox();
              
            JComboBox roleLeft = new JComboBox(new RoleCmbModel(roleItems));
            JComboBox roleRight = new JComboBox(new RoleCmbModel(roleItems));
            roleLeft.setSelectedIndex(0);
            roleRight.setSelectedIndex(1);
            
            JLabel lblFrom = new JLabel("Откуда: ");
            JLabel lblRole1 = new JLabel("Роль: ");
            JLabel lblTo = new JLabel("Куда: ");
            JLabel lblRole2 = new JLabel("Роль: ");
            
            for (int i=0; i<liststores.size();i++){
                toCmbBox.addItem(liststores.get(i).getName());
                fromCmbBox.addItem(liststores.get(i).getName());        
            }
            
            if(index < listpath.size()){
                
                toCmbBox.setSelectedItem(listpath.get(index).getPointTo().getStorehouse().getName());              
                roleLeft.setSelectedItem(listpath.get(index).getPointFrom().getNameOfRole()); 
                fromCmbBox.setSelectedItem(listpath.get(index).getPointFrom().getStorehouse().getName());                 
                roleRight.setSelectedItem(listpath.get(index).getPointTo().getNameOfRole());
                
                idStoreFrom.add(fromCmbBox.getSelectedIndex());
                idStoreTo.add(toCmbBox.getSelectedIndex());
                idRoleFrom.add(roleLeft.getSelectedIndex());
                idRoleTo.add(roleRight.getSelectedIndex());
  
            }
            
            fillListFromCmb(fromCmbBox, index, idStoreFrom);
            fillListFromCmb(toCmbBox, index, idStoreTo);
            fillListFromCmb(roleLeft, index, idRoleFrom);
            fillListFromCmb(roleRight, index, idRoleTo);
                       
           // fromCmbBox.getSelectedItem(liststores.get(index).getId());
            
            add(lblFrom);
            fromCmbBox.setPrototypeDisplayValue("Определенный какой-то склад");
            
            add(fromCmbBox);    
            add(lblRole1);
            roleLeft.setPrototypeDisplayValue("Продукт такой-то");
            add(roleLeft);
                   
            add(lblTo);
            toCmbBox.setPrototypeDisplayValue("Склад такой-то");
            add(toCmbBox);
                          
            add(lblRole2);
            roleRight.setPrototypeDisplayValue("Роль такая то");
            add(roleRight);      
           
            final int st = index;
            
            JButton myButtonRemoveMe = new JButton("Убрать");
            myButtonRemoveMe.addActionListener(new ActionListener(){
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    me.getParent().remove(me);
                    idStoreFrom.set(index, null);
                    idStoreTo.set(index, null);
                    idRoleFrom.set(index, null);
                    idRoleTo.set(index, null);
                    myFrame.pack();
                    
                }
            });
            add(myButtonRemoveMe);
                     
        }

        private void fillListFromCmb(JComboBox fromCmbBox, int index, ArrayList idStoreFrom) {
            fromCmbBox.addItemListener(new ItemListener(){
                
                @Override
                public void itemStateChanged(ItemEvent e){
                    System.out.println(fromCmbBox.getSelectedIndex());
                    if ((e.getStateChange() == ItemEvent.SELECTED)){
                        int selection = fromCmbBox.getSelectedIndex();
                        idStoreFrom.set(index, selection);
                    }
                }
            });
        }    
    }   
}
