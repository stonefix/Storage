/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cargo.tablemodels;
import com.cargo.storage.model.Storehouse;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author yura
 */
public class StorehouseTableModel extends AbstractTableModel{
    
    private static final int ID = 0;
    private static final int NAME = 1;
    private static final int DESCRIPTION = 2;
    
    private String[] columnNames = {"Id","Name", "Description"};
    
    private List<Storehouse> storehouses;
    
    public StorehouseTableModel(List<Storehouse> storehouses){
        this.storehouses = storehouses;
    }
    
    @Override
    public int getRowCount() {
        return storehouses.size();
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
        Storehouse tmpStorehouse = storehouses.get(row);
        
        switch (column) {
                case ID:
                    return tmpStorehouse.getId();
		case NAME:
			return tmpStorehouse.getName();
		case DESCRIPTION:
			return tmpStorehouse.getDescription();
		default:
                        return tmpStorehouse.getName();
		}
    }
    
}
