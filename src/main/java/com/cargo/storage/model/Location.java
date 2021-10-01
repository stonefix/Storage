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
public class Location {
    
    private int idLocation;
    private String city;
    private String region;
    private String country;
    private String address;
    private String phone;

    public Location(String city, String region, String country, String address, String phone){
        this.city = city;
        this.region = region;
        this.country = country;
        this.address = address;
        this.phone = phone;
    }
    
    public Location(int idLocation, String city, String region, String country, String address, String phone) {
        this.idLocation = idLocation;
        this.city = city;
        this.region = region;
        this.country = country;
        this.address = address;
        this.phone = phone;
    }

    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    
    
}
