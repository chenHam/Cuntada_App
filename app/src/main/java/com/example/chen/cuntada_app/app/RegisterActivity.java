package com.example.chen.cuntada_app.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity{
    private static final String TAG = MainActivity.class.getSimpleName();
    private DatabaseReference UsersDB;
    DatabaseReference ref;
    EditText firstname;
    EditText lastname;
    EditText mail;
    EditText pass;
    EditText confirm_pass;
    Boolean diet;
    String fname,lname,email,ps,cps;

    Button buttonAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        UsersDB = FirebaseDatabase.getInstance().getReference("users");

        firstname = (EditText) findViewById(R.id.first_name);
        lastname = (EditText) findViewById(R.id.last_name);
        mail = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        confirm_pass = (EditText) findViewById(R.id.confirm_password);

        buttonAdd = (Button) findViewById(R.id.button_register);
        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                RegisterButton(view);
            }
        });
    }

    public void RegisterButton(View view){

        fname = firstname.getText().toString().trim();
        lname = lastname.getText().toString().trim();
        email = mail.getText().toString().trim();
        ps = pass.getText().toString().trim();
        cps = confirm_pass.getText().toString().trim();
        diet = false;
//        boolean checked = ((CheckBox) view).isChecked();
//        if (checked){
//            diet = true;
//        }
        CheckValidation(fname,lname,email,ps,cps);
        checkPassword(ps,cps);

        ref = FirebaseDatabase.getInstance().getReference();

        ref.child("users").child("email").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Toast.makeText(getApplicationContext(),"The user is already exist",Toast.LENGTH_SHORT).show();
                }
                else{

                    CreateUser();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

     void CreateUser(){
        String id = UsersDB.push().getKey();
        User user = new User(id,fname,lname,email,ps,diet);
        UsersDB.child(id).setValue(user);
        Toast.makeText(this,"User added", Toast.LENGTH_LONG).show();
         startActivity(new Intent(getApplicationContext(), DetailsActivity.class));

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

}