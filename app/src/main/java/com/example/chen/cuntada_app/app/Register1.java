package com.example.chen.cuntada_app.app;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;


public class Register1 extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();

    private DatabaseReference UsersDB;
    DatabaseReference ref;
    EditText first_name,last_name,mail,password,confirm_password,weight,height;
    CheckBox dietican;
    Boolean diet;
    String fname,lname,email,pass,confirm_pass,weightStr,heightStr;

    Button buttonAdd;

    Button saveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_1, container, false);

//        super.onCreate(savedInstanceState);

        UsersDB = FirebaseDatabase.getInstance().getReference("users");

        first_name = (EditText) view.findViewById(R.id.first_name);
        last_name = (EditText) view.findViewById(R.id.last_name);
        mail = (EditText) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);
        confirm_password = (EditText) view.findViewById(R.id.confirm_password);
        dietican = (CheckBox) view.findViewById(R.id.checkbox_Dietician);
        weight = (EditText) view.findViewById(R.id.confirm_password);
        height = (EditText) view.findViewById(R.id.confirm_password);

        saveButton = (Button)view.findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                RegisterButton(view);
            }
        });
        return view;
    }

    public void RegisterButton(View view){

        fname = first_name.getText().toString().trim();
        lname = last_name.getText().toString().trim();
        email = mail.getText().toString().trim();
        pass = password.getText().toString().trim();
        confirm_pass = confirm_password.getText().toString().trim();
        diet = dietican.isChecked();
        weightStr = weight.getText().toString().trim();
        heightStr = height.getText().toString().trim();

        CheckValidation(fname,lname,email,pass,confirm_pass);
        checkPassword(pass,confirm_pass);

//        ref = FirebaseDatabase.getInstance().getReference();

//        ref.child("users").child("email").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    Toast.makeText(getActivity().getApplicationContext(),"The user is already exist",Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    CreateUser();
//                }
//            }

//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(email);

        myRef.addValueEventListener(new ValueEventListener() {
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

    }

    void CreateUser(){
//        String id = UsersDB.push().getKey();
//        User user = new User(id,fname,lname,email,pass,diet,weightStr,heightStr);
//        UsersDB.child(email).setValue(user);
//        Toast.makeText(getActivity().getApplicationContext(),"User added", Toast.LENGTH_LONG).show();
//        startActivity(new Intent(getActivity().getApplicationContext(), AllActivity.class));

        String id = UsersDB.push().getKey();
        User user = new User(id,fname,lname,email,pass,diet,weightStr,heightStr);
        addUser(user, new OnCreation() {
            @Override
            public void onCompletion(boolean success) {
                Log.d("TAG","created user");
                //Toast.makeText(getActivity(), "User Details Updated!", Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(getActivity(),"User added", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getActivity().getApplicationContext(), AllActivity.class));


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

    //TODO : check if this log error show on the screen
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
        if(password.equals(confirm_password)){
            Log.e(TAG, "Confirm password not match password" );
        }
    }

}
