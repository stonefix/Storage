/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cargo.storage.dao;

import com.cargo.storage.model.Location;
import com.cargo.storage.model.Product;
import com.cargo.storage.model.Products;
import com.cargo.storage.model.Storehouse;
import java.io.FileInputStream;
import java.sql.CallableStatement;
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
public class StorehouseDAO {
    
    private Connection conn;
    
    public StorehouseDAO() throws Exception{
                Properties props = new Properties();
		props.load(new FileInputStream("properties.properties"));
		
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		
		// connect to database
		conn = DriverManager.getConnection(dburl, user, password);
		
                System.out.println(getAllProductsOnStore(1).toString());
		System.out.println("DB connection successful to: " + dburl);
    }
        
    
    public void addExistProd(int countProd, int idProd, int idStore) throws SQLException{
    
    
        String query = "update productonstorehouse " +
                        "set countOfProduct = countOfProduct + ? " +
                        "where idofproduct = ? " +
                        "and idofstorehouse = ?;";
        
        PreparedStatement pstm = null;
        
        try{
        
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, countProd);
            pstm.setInt(2, idProd);
            pstm.setInt(3, idStore);
            pstm.executeUpdate();
            
        }
        finally{
            close(pstm);
        }
        
        
    
    }
    
    public boolean checkExistProd(int idProd, int idStore) throws SQLException {
    
        String query = "select * from productonstorehouse " +
                        "where idofstorehouse = ? " +
                        "and idofproduct = ?;";
        
        PreparedStatement pstm = null;
        ResultSet rst = null;
        try{
        
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, idStore);
            pstm.setInt(2, idProd);
            rst = pstm.executeQuery();
            if (!rst.isBeforeFirst() ) {   
                return false;
            } else {
                return true;
            }
        }
        finally{
            close(pstm, rst);
        }
        
       
    
    
    }
    
    public List<Products> getAllProductsOnStore(int idOfStore) throws Exception{
        List<Products> list = new ArrayList<Products>();
        String query = "{call listOfProdOnStore(?)}";
        CallableStatement callStmt = null;
        ResultSet rst = null;
        try{
            callStmt = conn.prepareCall(query);
            callStmt.setInt(1, idOfStore);
            rst = callStmt.executeQuery();
            while(rst.next()){
                int id = rst.getInt("idproduct");
                String name = rst.getString("name");
                String type = rst.getString("type");
                int count = rst.getInt("countOfProduct");               
                Products tmpProducts = new Products(new Product(id, name, type), count);
                list.add(tmpProducts);
            }
            return list;
        }
        finally{
            close(callStmt, rst);
        }             
        
    }
    
    public String[] getRoles() throws Exception {
        
        ArrayList<String> rolesList = new ArrayList<>();
        String query = "select * from roleofstorehouse";
        
        Statement stmt = null;
        ResultSet rst = null;
        
        try{
            stmt = conn.createStatement();
            rst = stmt.executeQuery(query);
            
            while(rst.next()){
                String role = rst.getString("nameofrole");
                rolesList.add(role);
            }
            String[] roles = rolesList.toArray(new String[rolesList.size()]);
            return roles;
        }
        finally{
            close(stmt, rst);
        }
    
    }
    
    public Location getLocationForStore(int idOfStore) throws Exception {
        
        PreparedStatement pstmt = null;
        ResultSet rst = null;
        Location tmpLocation = null;
        try{
            pstmt = conn.prepareStatement("select * from storewithdetails where idstorehouse = ?");
            pstmt.setInt(1,idOfStore); 
            rst = pstmt.executeQuery();
            while(rst.next()){
                int idStrhouse = rst.getInt("idstorehouse");
                String name = rst.getString("name");
                String city = rst.getString("city");
                String country = rst.getString("country");
                String region = rst.getString("region");
                String address = rst.getString("address");
                String phone = rst.getString("phone");
                tmpLocation = new Location(city, region, country, address, phone);
                    
            }
             return tmpLocation;
        }
        finally{
            close(pstmt, rst);
        }
        
        
           
    }
    
    public List<Storehouse> getAllStorehouses() throws Exception {
            List<Storehouse> list = new ArrayList<Storehouse>();
            
            Statement stmt = null;
            ResultSet rst = null;
            
            try{
                stmt = conn.createStatement();
                rst = stmt.executeQuery("select idstorehouse, name, description from storehouse");
                
                while(rst.next()) {
                    Storehouse tmpStorehouse = convertRowToStorehouse(rst);
                    list.add(tmpStorehouse);
                }
                
                return list;
            }
            finally{
                close(stmt, rst);
            }
            
            
    }
    
    public void setCountProdOnStore(int idStore, int idProd, int count) throws SQLException {
        String query = "update productonstorehouse " +
                        "set countOfProduct = ? " +
                        "where idofstorehouse = ? " +
                        "and idofproduct = ?; ";
        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, count);
            pstm.setInt(2, idStore);
            pstm.setInt(3, idProd);
            pstm.executeUpdate();
        }
        finally{
            close(pstm);
        }
        
    
    }
    
    public void addStorehouse(Storehouse storehouse) throws Exception{
        
        CallableStatement callStmt = null;
        String query = "{call insertStoreWithLocation(?, ?, ?, ?, ?, ?, ?)}";
        
        try{
           // myStmt = conn.prepareStatement("insert into storehouse (name, id)");
           callStmt = conn.prepareCall(query);
           callStmt.setString(1, storehouse.getLocation().getCity());
           callStmt.setString(2, storehouse.getLocation().getCountry());
           callStmt.setString(3, storehouse.getLocation().getRegion());
           callStmt.setString(4, storehouse.getLocation().getAddress());
           callStmt.setString(5, storehouse.getLocation().getPhone());
           callStmt.setString(6, storehouse.getName());
           callStmt.setString(7, storehouse.getDescription());
           callStmt.executeUpdate();
        }
        
        finally{
            close(callStmt);
        }
        
    }
    
    public void deleteStorehouse(int idSt) throws SQLException{
        String query = "delete from storehouse where idstorehouse = ?";
        PreparedStatement pst = null;
        
       
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, idSt);
            pst.executeUpdate();
        }
        finally{
            close(pst);
        }
        
    }
    
    private Storehouse convertRowToStorehouse(ResultSet rst) throws SQLException{
        
        int id = rst.getInt("idstorehouse");
        String name = rst.getString("name");
        String description = rst.getString("description");
        
        Storehouse tmpStorehouse = new Storehouse(id ,name, description);
        return tmpStorehouse;
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

    private void close(Statement myStmt, ResultSet myRs) throws SQLException {
            close(null, myStmt, myRs);		
    }
    
    public void closeConnection() throws SQLException{
        conn.close();
    }
    
}
