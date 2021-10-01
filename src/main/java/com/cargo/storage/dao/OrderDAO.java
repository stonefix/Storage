/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cargo.storage.dao;

import com.cargo.storage.model.Order;
import com.cargo.storage.model.Path;
import com.cargo.storage.model.Point;
import com.cargo.storage.model.Product;
import com.cargo.storage.model.Products;
import com.cargo.storage.model.Storehouse;
import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yura
 */
public class OrderDAO {
    
Connection conn;

public OrderDAO() throws Exception{
    
   // Properties props = new Properties();
   // props.load(new FileInputStream("properties.properties"));

    String user = "root";
    String password = "12345";
    String dburl = "jdbc:mysql://localhost:3306/storage?useSSL=false&allowMultiQueries=true";
		
		
    conn = DriverManager.getConnection(dburl, user, password);
}


public Order getOrder(int idOrder) throws SQLException {

   
    String query = "select * from storage.order " +
                    "where idorder = ?; ";

    PreparedStatement pstm = null;
    ResultSet rst = null;
    
    try{
        Order ord = new Order(1, " ",  " ", " ", 1);
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, idOrder);
        rst = pstm.executeQuery();
        while(rst.next()) {
               int id = rst.getInt("idorder");
               String nameOfOrder = rst.getString("nameOfOrder");
               int code = rst.getInt("code");
               Date date = rst.getDate("dateOfdeparture");
               Date dateOfFinish = rst.getDate("dateOfFinish");
               String dateStr = date.toString();
               String dateFinish = dateOfFinish.toString();
               
               ord.setCode(code);
               ord.setDateOfBirth(dateStr);
               ord.setDateOfFinish(dateFinish);
               ord.setName(nameOfOrder);
               ord.setIdOrd(id);
        }
        return ord;
    }
    finally{
        close(pstm, rst);
    }
    
}

public List<Order> getFinishOrders() throws SQLException{
    List<Order> list = new ArrayList<>();

    String query = "select * from storage.order " +
                    "where dateOfFinish is not null; ";
    Statement stmt = null;
    ResultSet rst = null;
    try{
         stmt = conn.createStatement();
         rst = stmt.executeQuery(query);       
         while(rst.next()){
               int id = rst.getInt("idorder");
               String nameOfOrder = rst.getString("nameOfOrder");
               int code = rst.getInt("code");
               Date date = rst.getDate("dateOfdeparture");
               Date dateOfFinish = rst.getDate("dateOfFinish");
               String dateStr = date.toString();
               String dateFinish = dateOfFinish.toString();
               
               Order ord = new Order(id, nameOfOrder,  dateStr, dateFinish, code);
               list.add(ord);
         }
         return list;
    }
    finally{
        close(stmt, rst);
    }
    
}

public void insertPath(String nameOrd, String code, String date, int idStore1, int idStore2, String transport) throws SQLException {

    String query= "insert into state(type) values(1); " +
                "set @last_insert_state = last_insert_id(); " +
                "insert into storage.order (nameOfOrder, code, dateOfdeparture, stateid) values (?, ?, ?, @last_insert_state); " +
                 "set @last_id_order = last_insert_id(); " + 
                 "insert into points (idstorehouses, idroleofstorehouse) values (?, 1); " +
                 "set @last_id_pointFrom = last_insert_id(); " +
                 "insert into points (idstorehouses, idroleofstorehouse) values (?, 2); " +
                 "set @last_id_pointTo = last_insert_id(); " +
                 "insert into fromto (order_id, from_id, to_id, transport) values (@last_id_order, @last_id_pointFrom, @last_id_pointTo, ?);";

    PreparedStatement pstm = null;
    
    try{
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date myDate = formatter.parse(date);
        java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
        
        pstm = conn.prepareStatement(query);
        pstm.setString(1, nameOrd);
        pstm.setString(2, code);
        pstm.setDate(3, sqlDate);
        pstm.setInt(4, idStore1);
        pstm.setInt(5, idStore2);
        pstm.setString(6, transport);
        pstm.executeUpdate();
    
    }
    catch (ParseException ex) {    
        Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
    }    
    finally{
        close(pstm);
    }
    
}

public void addProdToOrder(int idorder, int idproduct, int countofproduct) throws SQLException {

    String query = "insert into positionsoforder (idorder, idproduct, countofproduct) " +
                    "values(?, ?, ?)";
    
    PreparedStatement pstm = null;
    
    try{
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, idorder);
        pstm.setInt(2, idproduct);
        pstm.setInt(3, countofproduct);
        pstm.executeUpdate();
    }
    finally{}
    

}

public void addOrder(String name, String code, String date) throws SQLException, ParseException{

    String query =  "insert into storage.order (nameOfOrder, code, dateOfdeparture) values (?, ?, ?); ";
    
    PreparedStatement pstm = null;
  
    
     DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
     java.util.Date myDate = formatter.parse(date);
     java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
  
    try{
        
        pstm = conn.prepareStatement(query);
        pstm.setString(1, name);
        pstm.setString(2, code);
        pstm.setDate(3, sqlDate);
        pstm.executeUpdate();
    }
    finally{
        close(pstm);
    }
}

public void insertPath(int idOrd, int idRole1, int idStore1, int idRole2, int idStore2) throws SQLException{

    String query = "insert into points (idstorehouses, idroleofstorehouse) values (?, ?); " +
                    "set @last_id_pointFrom = last_insert_id(); " +
                    "insert into points (idstorehouses, idroleofstorehouse) values (?, ?); " +
                    "set @last_id_pointTo = last_insert_id(); " +
                    "insert into fromto (order_id, from_id, to_id) values (?, @last_id_pointFrom, @last_id_pointTo);";

    PreparedStatement pstm = null;

    try{
        
            pstm = conn.prepareStatement(query);
            pstm.setInt(1, idStore1);
            pstm.setInt(2, idRole1);
            pstm.setInt(3, idStore2);
            pstm.setInt(4, idRole2);
            pstm.setInt(5, idOrd);
      
            pstm.executeUpdate();
        
    }
    
    finally{
        close(pstm);
    }
    
}

public void deleteOrder(int idOrd) throws SQLException{

    String query = 
                    "delete from fromto where order_id = ?; " +
                    "delete from positionsoforder where idorder = ?; " +
                    "delete from storage.order where idorder = ?; ";

    
    PreparedStatement pstm = null;
    try{
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, idOrd);
        pstm.setInt(2, idOrd);
        pstm.setInt(3, idOrd);
        pstm.executeUpdate();
    }
    finally{
        close(pstm);
    }
}

public void deletePath(int idOrd) throws SQLException{

    String query = "delete from fromto where order_id = ?";
    PreparedStatement pstm = null;
    try{
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, idOrd);
        pstm.executeUpdate();
    }
    finally{
        close(pstm);
    }
    

}

public List<Path> getPath(int idOrd) throws Exception {
    List<Path> list = new ArrayList<Path>();
    Path path = null;
    String query = "select " +
                    "ft.idfromto, " +
                    "ft.order_id, " +
                    "str.idstorehouse as 'idstore1', " +
                    "str.name as 'from', " +
                    "role.idroleofstorehouse as 'idrole1', " +
                    "role.nameofrole as role1, " +
                    "str2.idstorehouse as 'idstore2', " +
                    "str2.name as 'to', " +
                    "role2.idroleofstorehouse as 'idrole2', " +
                    "role2.nameofrole as role2, " +
                    "ft.transport as 'transport', " +
                    "ft.time as 'time' "   +
                    "from fromto ft " +
                    "inner join points p " +
                    "	on ft.from_id = p.id " +
                    "inner join points p2 " +
                    "	on ft.to_id = p2.id " +
                    "inner join storehouse str " +
                    "	on p.idstorehouses = str.idstorehouse " +
                    "inner join storehouse str2 " +
                    "	on p2.idstorehouses = str2.idstorehouse " +
                    "inner join roleofstorehouse role " +
                    "	on p.idroleofstorehouse = role.idroleofstorehouse " +
                    "inner join roleofstorehouse role2 " +
                    "	on p2.idroleofstorehouse = role2.idroleofstorehouse " +
                    "where ft.order_id = ?;";
    
    PreparedStatement pstmt = null;
    ResultSet rst = null;
    try{
        pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, idOrd);
        rst = pstmt.executeQuery();
        while(rst.next()){
            int idpath = rst.getInt("idfromto");
            
            int idStore1 = rst.getInt("idstore1");
            String from = rst.getString("from");
            
            int idRole1 = rst.getInt("idrole1");
            String role1 = rst.getString("role1");
            
            int idStore2 = rst.getInt("idstore2");
            String to = rst.getString("to");
            
            int idRole2 = rst.getInt("idrole2");
            String role2 = rst.getString("role2");
            
            String transport = rst.getString("transport");
            String time = rst.getString("time");
            
            Point fromP = new Point(new Storehouse(idStore1, from), idRole1, role1);
            Point toP = new Point(new Storehouse(idStore2, to), idRole2, role2);
            path = new Path(idpath, fromP, toP, transport, time);
            list.add(path);
        }
        return list;
    } finally{
        close(pstmt, rst);
    }

}

public Point getFrom(int idOrd) throws Exception {
    Point from = null;
    PreparedStatement pstmt = null;
    ResultSet rst = null;
    String query = "select str.idstorehouse, str.name, role.nameofrole " +
                    "from storehouse str, points p, roleofstorehouse role " +
                    "where str.idstorehouse = p.idstorehouses " +
                    "and role.idroleofstorehouse = p.idroleofstorehouse " +
                    "and p.id = " +
                    "(select from_id from fromTo where idfromto = " +
                    "(select min(idfromto) from fromTo " +
                    "where order_id = ?));";
    try{
        pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, idOrd);
        rst = pstmt.executeQuery();
        if (!rst.isBeforeFirst() ) {    
            from = new Point(new Storehouse(" ", "  "), " ");
            return from;
        }  else{
            while(rst.next()){
                int idStore = rst.getInt("idstorehouse");
                String nameStore = rst.getString("name");
                String role = rst.getString("nameofrole");
                Storehouse tmp = new Storehouse(idStore, nameStore);
                from = new Point( tmp, role);
            }
            return from;
        }
         
    }
    finally{
        close(pstmt, rst);
    }
    
}

public Point getTo(int idOrd) throws Exception{
    Point to = null;
    PreparedStatement pstmt = null;
    ResultSet rst = null;
   
    String query = "select str.idstorehouse, str.name, role.nameofrole " +
                    "from storehouse str, points p, roleofstorehouse role " +
                    "where str.idstorehouse = p.idstorehouses " +
                    "and role.idroleofstorehouse = p.idroleofstorehouse " +
                    "and p.id = " +
                    "(select to_id from fromTo where idfromto = " +
                    "(select max(idfromto) from fromTo " +
                    "where order_id = ?));";
    try{
        pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, idOrd);
        rst = pstmt.executeQuery();
        if (!rst.isBeforeFirst() ) {    
            to = new Point(new Storehouse(" ", "  "),  " ");
            return to;
        }  else{
        while(rst.next()){
            int idStore = rst.getInt("idstorehouse");
            String nameStore = rst.getString("name");
            String role = rst.getString("nameofrole");
            Storehouse tmp = new Storehouse(idStore, nameStore);
            to = new Point(tmp, role);
        }       
            return to;
        }
        
    }
    finally{
        close(pstmt, rst);
    }

}

public List<Products> getProductsOfOrder(int idord) throws Exception{
    List<Products> list = new ArrayList<Products>();
    String query = "{call listOfProdOfOrder(?)}";
    CallableStatement callStmt = null;
    ResultSet rst = null;
    
    try{
            callStmt = conn.prepareCall(query);
            callStmt.setInt(1, idord);
            rst = callStmt.executeQuery();
            while(rst.next()){
                int id = rst.getInt("idproduct");
                String name = rst.getString("name");
                int count = rst.getInt("counts");
                Products prods = new Products(new Product(id,name), count);
                list.add(prods);
            }
            return list;
    }
    finally{
        close(callStmt, rst);
    }
    
}

public void updateFinishDate(int indexOrd, String dateFinish) throws SQLException, ParseException {

    String query = "update storage.order " +
                    "set dateOfFinish = ? " +
                    "where idorder = ?;";
    PreparedStatement pstm = null;
    
    
     DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
     java.util.Date myDate = formatter.parse(dateFinish);
     java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
   
    
    try{
        
        pstm = conn.prepareStatement(query);
        pstm.setDate(1, sqlDate);
        pstm.setInt(2, indexOrd);
        pstm.executeUpdate();
    }
    finally{
        close(pstm);
    }
    
    
}

public List<Order> getAllOrders() throws Exception{
    List<Order> list = new ArrayList<Order>();
    String query = "select * from storage.order";
    
    Statement stmt = null;
    ResultSet rst = null;
    try{
         stmt = conn.createStatement();
         rst = stmt.executeQuery(query);       
         while(rst.next()){
               int id = rst.getInt("idorder");
               String nameOfOrder = rst.getString("nameOfOrder");
               int code = rst.getInt("code");
               Date date = rst.getDate("dateOfdeparture");
               Date dateOfFinish = rst.getDate("dateOfFinish");
               String dateStr = date.toString();
               String dateFinish;
               if (dateOfFinish == null){
                   dateFinish = " ";
               }
               else {
                    dateFinish = dateOfFinish.toString();
               }
               Order ord = new Order(id, nameOfOrder,  dateStr, dateFinish, code);
               list.add(ord);
         }
         return list;
    } 
    
    finally{
        close(stmt, rst);
    }
    
   
}

public void changeState(int idOrd, int type) throws SQLException{
    String query = "update state " +
                    "set type= ? "+ 
                    "where state.idstate = (select storage.order.stateid from " +
                    "storage.order where storage.order.idorder = ?)";
    
    PreparedStatement pstm = null;
    try{
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, type);      
        pstm.setInt(2, idOrd);
        pstm.executeUpdate();
    }
    finally{
        close(pstm);
    }
}

public void changeState(int idOrd, int idStore, int type) throws SQLException{
    String query = "update state " +
                    "set type= ?, storehouse= ? "+ 
                    "where state.idstate = (select storage.order.stateid from " +
                    "storage.order where storage.order.idorder = ?)";
    
    PreparedStatement pstm = null;
    try{
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, type);
        pstm.setInt(2, idStore);
        pstm.setInt(3, idOrd);
        pstm.executeUpdate();
    }
    finally{
        close(pstm);
    }
}

public String getState(int idOrd) throws SQLException{
    
    String state = "";
    String query = "select st.storehouse, tos.name " +
                    "from storage.order o, state st, typeofstate tos " +
                    "where o.stateid = st.idstate " +
                    "and st.type = tos.idtypeOfState " +
                    "and idorder = ?";
    
    PreparedStatement pstmt = null;
    ResultSet rst = null;
    
    try{
        pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, idOrd);
        rst = pstmt.executeQuery();
        while(rst.next()){
            int storehouse = rst.getInt("storehouse");
            state = rst.getString("name");
            
        }
        return state;
    }
    
    finally{
        close(pstmt, rst);
    }
    
}

public String getStoreOnMoment(int idOrd) throws SQLException{

    String store = "";
    String query = "select str.name as namestore from storage.order o, state st, typeofstate tos, storehouse str " +
                    "where o.stateid = st.idstate " +
                    "and st.type = tos.idtypeOfState " +
                    "and idorder = ? and str.idstorehouse = st.storehouse;";
    PreparedStatement pstm = null;
    ResultSet rst = null;
    
    try{
        pstm = conn.prepareStatement(query);
        pstm.setInt(1, idOrd);
        rst = pstm.executeQuery();
        
        while(rst.next()){
            store = rst.getString("namestore");
            
        }
        
        return store;
    
    }
    finally{
        close(pstm, rst);
    }

}

public void changeTime(int idFrom, String time) throws SQLException{

    String query = "update fromto " + 
                    "set time = ? " + 
                    "where idfromto = ?";
    PreparedStatement pstm = null;
    try{
        pstm = conn.prepareStatement(query);
        pstm.setString(1, time);
        pstm.setInt(2, idFrom);
        pstm.executeUpdate();
    }
    finally{
        close(pstm);
    }
    

}

public void changeTransport(int idFrom, String transport) throws SQLException{


    String query  = "update fromto " +
                    "set transport = ? " +
                    "where idfromto = ?";
    PreparedStatement pstm = null;
    
    try{
        pstm = conn.prepareStatement(query);
        pstm.setString(1, transport);
        pstm.setInt(2, idFrom);
        pstm.executeUpdate();
    }
    finally{
        close(pstm);
    }
    
}

private void close(Statement myStmt) throws SQLException {
            close(null, myStmt, null);		
    }

private static void close(Connection myConn, Statement myStmt, ResultSet myRs) throws SQLException {

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
