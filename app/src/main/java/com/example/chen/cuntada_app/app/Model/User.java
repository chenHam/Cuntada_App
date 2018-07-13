package com.example.chen.cuntada_app.app.Model;

import java.util.HashMap;

public class User {

    String firstName;
    String lastName;
    String email;
    String pw;
    String weight;
    String height;
    boolean isMale;

    public  User(){

    }
    public User(String firstName, String lastName, String email, String pw, String weight, String height, boolean isMale) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.pw = pw;
        this.weight = weight;
        this.height = height;
        this.isMale = isMale;
    }

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

    public void setWeight(String weight){ this.weight = weight; }

    public void setHeight(String height) { this.height = height; }

    public void setIsMale(boolean isMale) { this.isMale = isMale; }

}
