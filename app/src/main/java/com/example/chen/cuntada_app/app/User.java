package com.example.chen.cuntada_app.app;

public class User {

    String userid;
    String FirstName;
    String LastName;
    String Email;
    String Password;
    Boolean dietician;

    public User(String userid, String firstName, String lastName, String email, String password, Boolean dietician) {
        this.userid = userid;
        this.FirstName = firstName;
        this.LastName = lastName;
        this. Email = email;
        this.Password = password;
        this.dietician = dietician;
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
}
