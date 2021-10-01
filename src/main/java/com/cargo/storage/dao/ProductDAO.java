/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cargo.storage.dao;

import com.cargo.storage.model.Product;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author yura
 */
public class ProductDAO {
    
    private Connection conn;
    
   public ProductDAO() throws Exception{
                Properties props = new Properties();
		props.load(new FileInputStream("properties.properties"));
		
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		
		// connect to database
		conn = DriverManager.getConnection(dburl, user, password);
    }
    

public void addProduct(String name, String type) throws SQLException {
    
    PreparedStatement stmt = null;
    
    String query = "insert into product (name, type) values (?, ?);";
    
    try{
    
        stmt = conn.prepareStatement(query);
        stmt.setString(1, name);
        stmt.setString(2, type);
        stmt.executeUpdate();
        
    }
    finally{
        close(stmt);
    }
    
}

public void addProdToStorehouse(int idProd, int idStore, int countProd) throws SQLException {

    String query =  "insert into " +
                    "productonstorehouse(idofproduct, idofstorehouse, countOfProduct) " +
                    "values(?,?,?);";
    
    PreparedStatement pstm = null;
    
    try{
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, idProd);
        pstm.setInt(2, idStore);
        pstm.setInt(3, countProd);
        pstm.executeUpdate();
    }
    finally{
        close(pstm);
    }

}

public void deleteProdFromStorehouse(int idStore, int idProduct) throws SQLException {

    String query = "delete from productonstorehouse " +
                    "where idofstorehouse = ? and idofproduct = ?  ";
    
    PreparedStatement pstm = null;
    
    try{
        pstm = conn.prepareStatement(query);
        pstm.setInt(1,idStore);
        pstm.setInt(2, idProduct);
        pstm.executeUpdate();
    }
    finally{
        close(pstm);
    }

}

public List<Product> searchProduct(String nameSearch) throws SQLException {

    List<Product> list = new ArrayList<Product>();
    PreparedStatement myStmt = null;
    ResultSet rst = null;
    String query = "select * from product where product.name like ?";
 
    try{
        nameSearch += "%";
        myStmt = conn.prepareStatement(query);
        myStmt.setString(1, nameSearch);
        rst = myStmt.executeQuery();
        while(rst.next()){
            int id = rst.getInt("idproduct");
            String name = rst.getString("name");
            String type = rst.getString("type");
            Product tmpProduct = new Product(id, name, type);
            list.add(tmpProduct);
        }
        return list;
    }
    finally{
        close(myStmt, rst);
    }
    
    
}

public List<Product> listOfProduct() throws SQLException {

    String query = "select * from product";
    Statement stmt = null;
    ResultSet rst = null;
    List<Product> list = new ArrayList<Product>();
    try{
        stmt = conn.createStatement();
        rst = stmt.executeQuery(query); 
        while(rst.next()){
            int id = rst.getInt("idproduct");
            String name = rst.getString("name");
            String type = rst.getString("type");
            Product tmpProduct = new Product(id, name, type);
            list.add(tmpProduct);
        }
        return list;
    }
    finally{
        close(stmt, rst);
    }
        
}

public void deleteProduct(int idProd) throws SQLException{
        String query = "delete from product where idproduct = ?";
        PreparedStatement pst = null;
            
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, idProd);
            pst.executeUpdate();
        }
        finally{
            close(pst);
        }
        
    }
    
private void close(Statement myStmt) throws SQLException {
            close(null, myStmt, null);		
    }

private static void close(Connection myConn, Statement myStmt, ResultSet myRs)
                    throws SQLException {

            if (myRs != null) {
                    myRs.close();
            }

            if (myStmt != null) {
                    myStmt.close();
            }

            if (myConn != null) {
                    myConn.close();
            }
    }

public void closeConnection() throws SQLException {
    conn.close();
}

private void close(Statement myStmt, ResultSet myRs) throws SQLException {
        close(null, myStmt, myRs);		

}
    
}
