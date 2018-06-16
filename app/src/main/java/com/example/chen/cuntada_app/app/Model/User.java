package com.example.chen.cuntada_app.app.Model;

import java.util.HashMap;

public class User {

    //String userId;
    String firstName;
    String lastName;
    String email;
    String pw;
    boolean dietician;
    String weight;
    String height;
    boolean isMale;

    public  User(){

    }
    public User(String firstName, String lastName, String email, String pw,
                boolean dietician, String weight, String height, boolean isMale) {
        //this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.pw = pw;
        this.dietician = dietician;
        this.weight = weight;
        this.height = height;
        this.isMale = isMale;
    }

    /*public String getUserId() {
        return userId;
    }*/

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return pw;
    }

    public Boolean getDietician() {
        return dietician;
    }

    public String getWeight() { return weight; }

    public String getHeight() { return height; }

    public boolean getIsMale() { return isMale; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.pw = password;
    }

    public void setDietician(boolean dietician) {
        this.dietician = dietician;
    }

    /*public void setUserid(String userId) {
        this.userId = userId;
    }*/

    public void setWeight(String weight){ this.weight = weight; }

    public void setHeight(String height) { this.height = height; }

    public void setIsMale(boolean isMale) { this.isMale = isMale; }



    /*HashMap<String,Object> toJson(){
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
    }*/
}
