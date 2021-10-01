/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cargo.storage.model;

import java.util.List;

/**
 *
 * @author yura
 */
public class Order {
    
    private int idOrder;
    private List<Product> items;
    private String name;
    private String dateOfBirth;
    private String dateOfFinish;
    private int code;
    private String state;
    
    public Order(int idOrder, String name, String dateOfBirth, int code, List<Product> items){
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.code = code;
        this.items = items;
    }
    
    public Order(int idOrder, String name, String dateOfBirth, String dateOfFinish, int code) {
        this.idOrder = idOrder;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.dateOfFinish = dateOfFinish;
        this.code = code;
    }

    
   
    public void setIdOrd(int idOrd){
        this.idOrder = idOrd;
        
    }
    public void addProduct(Product item){
        items.add(item);
    }
    
    public List<Product> getItems(){
        return items;
    }
    
    public void setItems(List<Product> items){
        this.items = items;
    }
    
    public int getIdOrder() {
        return idOrder;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDateOfFinish() {
        return dateOfFinish;
    }
    
    public int getCode() {
        return code;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfFinish(String dateOfFinish){
        this.dateOfFinish = dateOfFinish;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
}
