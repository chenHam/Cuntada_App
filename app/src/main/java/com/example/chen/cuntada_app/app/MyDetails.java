package com.example.chen.cuntada_app.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class MyDetails extends Activity{

    Button calculateButton;
    EditText body_weight,height_edit;
    TextView bmi;
    int height,weight;
    Button saveDetailsButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_details_activity);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        body_weight = (EditText) findViewById(R.id.body_weight);
        height_edit = (EditText) findViewById(R.id.height_edit);
        bmi = (TextView) findViewById(R.id.bmi_edit);


        //User aux = new User();

        final String userId = firebaseAuth.getCurrentUser().getUid();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
                // add spiner
                String weight = (String) snapshot.child("weight").getValue();
                String height = (String) snapshot.child("height").getValue();
                body_weight.setText(weight);
                height_edit.setText(height);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //databaseReference.child(userId).setValue(user);


        calculateButton = (Button)findViewById(R.id.button_calculate);
        calculateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                CalculateBMI(view);
            }
        });

        saveDetailsButton = (Button) findViewById(R.id.saveDetailsButton);
        saveDetailsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                HashMap<String, Object> result = new HashMap<>();
                result.put("weight", body_weight.getText().toString());
                result.put("height", height_edit.getText().toString());

                databaseReference.child(userId).updateChildren(result);
                startActivity(new Intent(getApplicationContext(), AllActivity.class));

            }
        });
    }


    public void CalculateBMI(View view){
        height = Integer.parseInt(height_edit.getText().toString());
        weight = Integer.parseInt(body_weight.getText().toString());

        double res_bmi = weight / (height*height);

        bmi.setText(String.valueOf(res_bmi));
    }
}
