package com.example.chen.cuntada_app.app;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    Button saveButton;

    Button buttonAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        if (savedInstanceState == null) {
//            Register1 fragmentRegister1 = new Register1();
////            Register2 fragmentRegister2 = new Register2();
//            FragmentTransaction tran = getFragmentManager().beginTransaction();
//            tran.add(R.id.register_container, fragmentRegister1);
////            tran.show(fragmentRegister1);
////            tran.add(R.id.register_container, fragmentRegister2);
//            tran.commit();
//        }

//        saveButton = (Button)findViewById(R.id.button_save);
//        saveButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                SaveDetailsButton(view);
//            }
//        });
    }


    protected void SaveDetailsButton(View view){
        String weight = findViewById(R.id.weight).toString();
        String height = findViewById(R.id.height).toString();
        RadioGroup rg = (RadioGroup) view.findViewById(R.id.gender);

        RadioButton gender = view.findViewById(rg.getCheckedRadioButtonId());
        //TODO: need to save to DB?
    }

}