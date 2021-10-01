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
public class Point {
    
    private int id;
    private int roleId;
    private String nameOfRole;
    private Storehouse storehouse;
    private String transport;
    
    public Point(Storehouse storehouse, int roleId, String nameOfRole){
        this.storehouse = storehouse;
        this.roleId = roleId;
        this.nameOfRole = nameOfRole;
        
    }

    
    public Point(Storehouse storehouse, String nameOfRole){
        this.storehouse = storehouse;
        this.nameOfRole = nameOfRole;
    }

    
    public int getRoleId(){
        return roleId;
    }
    
    public void setRoleId(int roleId){
        this.roleId = roleId;
    }
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getNameOfRole() {
        return nameOfRole;
    }

    public void setNameOfRole(String nameOfRole) {
        this.nameOfRole = nameOfRole;
    }

    public Storehouse getStorehouse() {
        return storehouse;
    }

    public void setStorehouse(Storehouse storehouse) {
        this.storehouse = storehouse;
    }
    
    
    
}
