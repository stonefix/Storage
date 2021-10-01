/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cargo.tablemodels;

import com.cargo.storage.model.Products;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author yura
 */
public class ProductsOnOrderTableModel extends AbstractTableModel {
    
    
    private static final int ID = 0;
    private static final int NAME = 1;   
    private static final int COUNT = 2;
    
    private String[] columnNames = {"Id", "Name", "Count"};
    
    private List<Products> items;
    
    public ProductsOnOrderTableModel(List<Products> items){
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
        Products tmpProds = items.get(row);
        
        switch(column) {
        
            case ID:
                    return tmpProds.getElement().getId();
            case NAME:
                    return tmpProds.getElement().getName();
            case COUNT:
                    return tmpProds.getCount();
            default:
                    return tmpProds.getElement().getName();
        }
    
}
}
