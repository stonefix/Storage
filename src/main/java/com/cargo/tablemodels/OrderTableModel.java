/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cargo.tablemodels;

import com.cargo.storage.model.Order;
import java.sql.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author yura
 */
public class OrderTableModel extends AbstractTableModel{

    private static final int ID = 0;
    private static final int NAME = 1;
    private static final int CODE = 2;
    private static final int DATE = 3;
    private static final int DATEFINISH = 4;
    
    private String[] columnNames = {"Идентификатор", "Имя", "Код", "Дата начала", "Дата завершения"}; 
    
    private List<Order> items;
    
    public OrderTableModel(List<Order> items){
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
        Order tmpOrders = items.get(row);
        
           switch(column) {
        
            case ID:
                    return tmpOrders.getIdOrder();
            case NAME:
                    return tmpOrders.getName();
            case CODE:
                    return tmpOrders.getCode();
            case DATE:
                    return tmpOrders.getDateOfBirth();
            case DATEFINISH:
                    return tmpOrders.getDateOfFinish();
            default:
                    return tmpOrders.getName();
        }
        
    }
    
}
