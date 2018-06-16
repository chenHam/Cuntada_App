package com.example.chen.cuntada_app.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chen.cuntada_app.app.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    User userLogIn;

    private Button loginButton;
    private EditText emailEditText;
    private EditText pwEditText;
    private Button signUpButton;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), AllActivity.class));
        }

        emailEditText = (EditText) findViewById(R.id.emailEditText);
        pwEditText = (EditText) findViewById(R.id.pwEditText);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        loginButton = (Button) findViewById(R.id.loginButton);

        progressDialog = new ProgressDialog(this);


        loginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Log.d("Tokyo", "login Button clicked");
                final String emailString = emailEditText.getText().toString();
                final String pwString = pwEditText.getText().toString();

                if(emailString.equals("") || pwString.equals("")){
                    Toast.makeText(getApplicationContext(), "You have to fill all fields!", Toast.LENGTH_LONG).show();
                    return;
                }

                progressDialog.setMessage("Logging in...");
                progressDialog.show();


                firebaseAuth.signInWithEmailAndPassword(emailString, pwString).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){

                            startActivity(new Intent(getApplicationContext(),AllActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Wrong Email or Password!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Log.d("Tokyo", "signUp Button clicked");
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));

            }
        });

    }

}
