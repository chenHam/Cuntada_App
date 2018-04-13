package com.example.chen.cuntada_app.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity{
    private static final String TAG = MainActivity.class.getSimpleName();
    private DatabaseReference UsersDB;
    TextView firstname;
    TextView lastname;
    TextView mail;
    TextView pass;
    TextView confirm_pass;
    Boolean diet;
    Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        UsersDB = FirebaseDatabase.getInstance().getReference("users");

    }

    public void RegisterButton(View view){

        firstname = this.findViewById(R.id.first_name);
        lastname = findViewById(R.id.last_name);
        mail = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        confirm_pass = findViewById(R.id.confirm_password);
        diet = false;
//        boolean checked = ((CheckBox) view).isChecked();
//        if (checked){
//            dietician = true;
//        }
//        CheckValidation(first_name,last_name,email,password,confirm_password);
//        checkPassword(password,confirm_password);

        String id = UsersDB.push().getKey();
        User user = new User(id,firstname,lastname,mail,pass,diet);
        UsersDB.child(id).setValue(user);
        Toast.makeText(this,"User added", Toast.LENGTH_LONG).show();
    }

    private void CheckValidation(String first_name,String last_name, String email, String password, String confirm_password){

        //check for null
        if(first_name == null){
            Log.e(TAG, "please enter First name" );
        }
        if(last_name == null){
            Log.e(TAG, "please enter Last name" );
        }
        if(email == null){
            Log.e(TAG, "please enter Email" );
        }
        if (!(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            Log.e(TAG, "please enter valid Email" );
        }
        if(password == null){
            Log.e(TAG, "please enter Password" );
        }
        if(confirm_password == null){
            Log.e(TAG, "please enter Confirm password" );
        }

    }

    private void checkPassword(String password , String confirm_password){
        if(password != confirm_password){
            Log.e(TAG, "Confirm password not match password" );
        }
    }

//    private void addUser(){
//     first_name.toString();
//     last_name.toString();
//     email.toString();
//     password.toString();
//
//     String id = UsersDB.push().getKey();
//     User user = new User(id,first_name,last_name,email,password,dietician);
//     UsersDB.child(id).setValue(user);
//     Toast.makeText(this,"User added", Toast.LENGTH_LONG).show();
//    }
}