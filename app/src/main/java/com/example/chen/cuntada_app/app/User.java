package com.example.chen.cuntada_app.app;

import android.widget.TextView;

public class User {

    String userid;
    TextView FirstName;
    TextView LastName;
    TextView Email;
    TextView Password;
    Boolean dietician;

    public User(String userid, TextView firstName, TextView lastName, TextView email, TextView password, Boolean dietician) {
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

    public TextView getFirstName() {
        return FirstName;
    }

    public TextView getLastName() {
        return LastName;
    }

    public TextView getEmail() {
        return Email;
    }

    public TextView getPassword() {
        return Password;
    }

    public Boolean getDietician() {
        return dietician;
    }
}
