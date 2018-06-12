package com.example.chen.cuntada_app.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
    String fname,lname,email,ps,cps,weight,height;

    Button buttonAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        UsersDB = FirebaseDatabase.getInstance().getReference("users");

        /*firstname = (EditText) findViewById(R.id.firstNameEditText);
        lastname = (EditText) findViewById(R.id.lastNameEditText);
        mail = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        confirm_pass = (EditText) findViewById(R.id.confirm_password);*/

//        buttonAdd = (Button) findViewById(R.id.button_save);
//        buttonAdd.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                RegisterButton(view);
//            }
//        });
    }

    /*public void RegisterButton(View view){

        fname = firstname.getText().toString().trim();
        lname = lastname.getText().toString().trim();
        email = mail.getText().toString().trim();
        ps = pass.getText().toString().trim();
        cps = confirm_pass.getText().toString().trim();
        diet = false;
        weight ="dd";
        height = "12";
//        boolean checked = ((CheckBox) view).isChecked();
//        if (checked){
//            diet = true;
//        }
        CheckValidation(fname,lname,email,ps,cps);
//        checkPassword(ps,cps);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(email);
        ValueEventListener listener = myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){


                }
                else{
                    CreateUser();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        getSpecificUser(email,new Callback<List<User>>() {
//            @Override
//            public void onComplete(List<User> data) {
//                Log.d("TAG", "Found : " + email);
//
//
//                CreateUser();
//            }
//        });
    }


    public interface OnCreationUser {
        void onCompletion(boolean success);
    }

    public interface OnCreation{
        public void onCompletion(boolean success);
    }


    public static void addUser(User user, final OnCreation listener) {
        Log.d("TAG", "add user to firebase");
        HashMap<String, Object> json = user.toJson();
        json.put("lastUpdated", ServerValue.TIMESTAMP);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        DatabaseReference ref = myRef.child(user.Email);
        ref.setValue(json, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e("TAG", "Error: User could not be saved "
                            + databaseError.getMessage());
                    listener.onCompletion(false);
                } else {
                    Log.e("TAG", "Success : User saved successfully.");
                    listener.onCompletion(true);
                }
            }
        });

    }


     void CreateUser(){
        String id = UsersDB.push().getKey();
        User user = new User(id,fname,lname,email,ps,diet,weight,height);
         addUser(user, new OnCreation() {
             @Override
             public void onCompletion(boolean success) {
                 Log.d("TAG","created user");
              //Toast.makeText(getActivity(), "User Details Updated!", Toast.LENGTH_SHORT).show();
             }
         });
        Toast.makeText(this,"User added", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), DetailsActivity.class));

     }

     //TODO : check if this log error show on the screen
    private void CheckValidation(String first_name,String last_name, String email, String password, String confirm_password){

//        //check for null
//        if(first_name == null){
//            Log.e(TAG, "please enter First name" );
//        }
//        if(last_name == null){
//            Log.e(TAG, "please enter Last name" );
//        }
//        if(email == null){
//            Log.e(TAG, "please enter Email" );
//        }
////        if (!(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())){
////            Log.e(TAG, "please enter valid Email" );
////        }
//        if(password == null){
//            Log.e(TAG, "please enter Password" );
//        }
//        if(confirm_password == null){
//            Log.e(TAG, "please enter Confirm password" );
//        }

    }


    public interface Callback<T> {
        void onComplete(T data);
    }

    public static void getAllUsersAndObserve(final Callback<List<User>> callback) {
        Log.d("TAG", "getAllUsersAndObserve");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        ValueEventListener listener = myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> list = new LinkedList<User>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    User user = snap.getValue(User.class);
                    list.add(user);
                }
                callback.onComplete(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onComplete(null);
            }
        });
    }


    public static void getSpecificUser(String userMail,final Callback<List<User>> callback) {
        Log.d("TAG", "getAllUsersAndObserve");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(userMail);
        ValueEventListener listener = myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> list = new LinkedList<User>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    User user = snap.getValue(User.class);
                    list.add(user);
                }
                callback.onComplete(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onComplete(null);
            }
        });
    }



//
//    private void checkPassword(String password , String confirm_password){
//        if(password != confirm_password){
//            Log.e(TAG, "Confirm password not match password" );
//        }
//    }*/

}