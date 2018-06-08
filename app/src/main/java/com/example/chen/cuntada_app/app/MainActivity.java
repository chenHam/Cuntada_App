package com.example.chen.cuntada_app.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    User userLogIn;
    private Button loginButton;
    private EditText Email;
    private EditText Pass;
    private Button signUpButton;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    private static final String TAG = MainActivity.class.getSimpleName();
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Dani", "before anything happens");

        /*firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }*/


        Email = (EditText) findViewById(R.id.user_login);
        Pass = (EditText) findViewById(R.id.pass_login);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        loginButton = (Button) findViewById(R.id.loginButton);
        progressDialog = new ProgressDialog(this);


        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                login_Button(view);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp(view);
            }
        });
    }

        public void login_Button(View view){
        String userEmail = Email.getText().toString().trim();
        String pas = Pass.getText().toString().trim();
        UserLogin();
//        startActivity(new Intent(getApplicationContext(), AllActivity.class));

        }
    public void SignUp(View view){
        finish();
        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
    }


     void UserLogin(){

        String email = Email.getText().toString().trim();
        final String password = Pass.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }


         FirebaseDatabase database = FirebaseDatabase.getInstance();
         DatabaseReference myRef = database.getReference("users").child(email);
         ValueEventListener listener = myRef.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 if(dataSnapshot.exists()){


                    String userDetails = dataSnapshot.getValue().toString();
                    String[] arrayDetails = userDetails.split(",");
                     String pass = arrayDetails[6].split("=")[1];
                     pass= pass.substring(0, pass.length() - 1);
                    if(pass.equals(password)){
                        String id = arrayDetails[0].toString().split("=")[1];
                        String fname = arrayDetails[2].split("=")[1];
                        String lname = arrayDetails[4].split("=")[1];
                        String email = arrayDetails[5].split("=")[1];
                        String weight = arrayDetails[6].split("=")[1];
                        String height = arrayDetails[7].split("=")[1];

                        userLogIn = new User(id, fname, lname, email, pass, Boolean.FALSE,weight,height);
                        startActivity(new Intent(getApplicationContext(), AllActivity.class));

                    }
//                     userLogIn = new User(id,arrayDetails[2],arrayDetails[4],arrayDetails[5],arrayDetails[6],Boolean.FALSE);

                 }
                 else{
                 }
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });

//        progressDialog.setMessage("Sign In please wait...");
//        progressDialog.show();
//
//        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                progressDialog.dismiss();
//                if(task.isSuccessful()) {
//                    finish();
//                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
//                }
//            }
//        });



    }

    @Override
    public void onClick(View view) {
        if(view==loginButton){
            UserLogin();
        }
        if(view==signUpButton){
            finish();
            startActivity(new Intent(this,RegisterActivity.class));
        }

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
