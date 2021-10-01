/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cargo.tablemodels;

import com.cargo.storage.model.Order;
import com.cargo.storage.model.Path;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author yura
 */
public class PathTableModel extends AbstractTableModel{
    
    private static final int  from = 0;
    private static final int role1 = 1;
    private static final int to = 2;
    private static final int role2 = 3;
    private static final int transport = 4;
    private static final int time = 5;
    private String[] columnNames = {"Откуда", "Роль", "Куда", "Роль", "Транспорт", "Время"}; 
    
    private List<Path> items;
    
    public PathTableModel(List<Path> items){
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
        Path tmpPath = items.get(row);
        
           switch(column) {
               
            case from:
                    return tmpPath.getPointFrom().getStorehouse().getName();
            case role1:
                    return tmpPath.getPointFrom().getNameOfRole();
            case to:
                    return tmpPath.getPointTo().getStorehouse().getName();
            case role2:
                    return tmpPath.getPointTo().getNameOfRole();
            case transport:
                    return tmpPath.getTransport();
            case time:
                    return tmpPath.getTime();
         
            case 6:
                    return tmpPath.getId();
            default:
                return tmpPath.getPointFrom().getStorehouse().getName();
                   
        }
        
    }
    

}
