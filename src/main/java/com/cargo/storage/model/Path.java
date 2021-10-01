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
public class Path {
    
    private int id;
    private Point pointFrom;
    private Point pointTo;
    private String transport;
    private String time;
    
    public Path(int idPath, Point pointFrom, Point pointTo, String transport, String time){
        this.id = idPath;
        this.pointFrom = pointFrom;
        this.pointTo = pointTo;
        this.transport = transport;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getTime(){
        return time;
    }
        
    public void setTime(String time){
        this.time = time;
    }
    
    public String getTransport(){
        return transport;
    }
    
    public void setTransport(String transport){
        this.transport = transport;
    }

    public Point getPointFrom() {
        return pointFrom;
    }

    public void setPointFrom(Point pointFrom) {
        this.pointFrom = pointFrom;
    }

    public Point getPointTo() {
        return pointTo;
    }

    public void setPointTo(Point pointTo) {
        this.pointTo = pointTo;
    }
    
    
}
