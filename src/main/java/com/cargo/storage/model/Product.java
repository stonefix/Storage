/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cargo.storage.model;

/**
 *
 * @author yura
 */
public class Product {
    
    private int productId;
    private String name;
    private String type;
    
    public Product(int productId, String name){
        this.productId = productId;
        this.name = name;
    }
    
    public Product(int productId, String name, String type){
        this.productId = productId;
        this.name = name;
        this.type = type;
        
    }
    
    public int getId(){
        return productId;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    public String getType(){
        return type;
    }
    
    @Override
    public String toString(){
        return  "Product [productId=" + productId + ", name=" + name 
                + ", type=" + type + " ]";
    }
    
}
