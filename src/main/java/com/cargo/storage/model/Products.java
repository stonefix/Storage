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
public class Products {
    
    private Product element;
    private int count;
    
    public Products(Product element, int count){
    
        this.element = element;
        this.count = count;
        
    }
    
    public Product getElement(){
        return element;
    }
    
    public void setElement(Product element){
        this.element = element;
    }
    
    public int getCount(){
        return count;
    }
    
    public void setCount(int count){
        this.count = count;
    }
    
    @Override
    public String toString(){
        return element.toString() + " count:" + count;
    }

}

