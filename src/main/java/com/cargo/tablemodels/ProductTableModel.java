/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cargo.tablemodels;

import com.cargo.storage.model.Product;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author yura
 */
public class ProductTableModel extends AbstractTableModel {

    private static final int ID = 0;
    private static final int NAME = 1;
    private static final int TYPE = 2;
    
    private String[] columnNames = {"Уникальный код", "Имя", "Тип"}; 
    
    private List<Product> items;
    
    public ProductTableModel(List<Product> items){
        this.items = items;
    }
    
    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
            return columnNames[column];
    }
    
    @Override
    public Object getValueAt(int row, int column) {
        Product tmpProduct  = items.get(row);
        
        switch(column) {
        
            case ID:
                    return tmpProduct.getId();
            case NAME:
                    return tmpProduct.getName();
            case TYPE:
                    return tmpProduct.getType();
            
            default:
                    return tmpProduct.getName();
        }
        
        
    }
    
}
