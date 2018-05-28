package com.example.chen.cuntada_app.app;

import java.util.HashMap;

public class User {

    String userid;
    String FirstName;
    String LastName;
    String Email;
    String Password;
    Boolean dietician;
    String weight;
    String height;

    public User(String userid, String firstName, String lastName, String email, String password, Boolean dietician, String weight, String height) {
        this.userid = userid;
        this.FirstName = firstName;
        this.LastName = lastName;
        this. Email = email;
        this.Password = password;
        this.dietician = dietician;
        this.weight = weight;
        this.height = height;
    }

    public String getUserid() {
        return userid;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public Boolean getDietician() {
        return dietician;
    }

    public String getWeight() { return weight; }

    public String getHeight() { return height; }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setDietician(Boolean dietician) {
        this.dietician = dietician;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setWeight(String weight){ this.weight = weight; }

    public void setHeight(String height) { this.height = height; }

    HashMap<String,Object> toJson(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("userid", userid);
        result.put("FirstName", FirstName);
        result.put("LastName", LastName);
        result.put("Email", Email);
        result.put("Password", Password);
        result.put("dietician", dietician);
        result.put("weight", weight);
        result.put("height", height);
        return result;
    }
}
