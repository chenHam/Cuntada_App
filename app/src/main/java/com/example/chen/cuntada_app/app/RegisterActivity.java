package com.example.chen.cuntada_app.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;


public class RegisterActivity extends AppCompatActivity{
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    protected void RegisterButton(View view){
        String first_name = findViewById(R.id.first_name).toString();
        String last_name = findViewById(R.id.last_name).toString();
        String email = findViewById(R.id.email).toString();
        String password = findViewById(R.id.password).toString();
        String confirm_password = findViewById(R.id.confirm_password).toString();
        Boolean dietician = false;
        boolean checked = ((CheckBox) view).isChecked();
        if (checked){
            dietician = true;
        }
        CheckValidation(first_name,last_name,email,password,confirm_password);
        checkPassword(password,confirm_password);
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
