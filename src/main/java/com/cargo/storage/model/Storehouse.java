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
public class Storehouse {

    private int id;
    private String name;
    private String description;
    private Location location;
    private Product product;
    
    /**
     * @return the name
     */
    
    public Storehouse(String name, String description){
        this.name = name;
        this.description = description;
    }
    
    public Storehouse(String name){
        this.name = name;
    }
    
    public Storehouse(int id, String name){
        this.id = id;
        this.name = name;
    }
    
    public Storehouse(String name, String description, Location location){
        this.name = name;
        this.description = description;
        this.location = location;
    }
    
    public Storehouse(int id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }
    
    public Storehouse(int id, String name, String description, Location location) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
    }
    
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
     
    @Override
    public String toString(){
        return String.format("Storehouse = [id=%s, name=%s, description=%s]", id, name, description);
    }
   
       
}
