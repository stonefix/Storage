/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cargo.comboboxmodels;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author yura
 */
public class RoleCmbModel  extends AbstractListModel implements ComboBoxModel {

    String [] items;
    String selection = null;
    
    public RoleCmbModel(String[] items){
        this.items = items;
    }
    
    @Override
    public int getSize() {
        return items.length;
    }

    @Override
    public Object getElementAt(int i) {
       return items[i];
    }

    @Override
    public void setSelectedItem(Object o) {
        selection = (String) o;
    }

    @Override
    public Object getSelectedItem() {
       return selection;
    }
    
}
