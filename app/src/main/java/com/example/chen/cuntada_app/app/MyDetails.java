package com.example.chen.cuntada_app.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chen.cuntada_app.app.Model.Model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class MyDetails extends Activity{



    private FirebaseAuth firebaseAuth;

    EditText firstNameEditText;
    EditText lastNameEditText;
    CheckBox dieticanCheckBox;
    EditText weightEditText;
    EditText heightEditText;
    RadioGroup genderRadioGroup;

    Button saveDetailsButton;
    Button calculateBmiButton;

    TextView bmiTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_details_activity);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        dieticanCheckBox = (CheckBox) findViewById(R.id.dieticanCheckBox);
        weightEditText = (EditText) findViewById(R.id.weightEditText);
        heightEditText = (EditText) findViewById(R.id.heightEditText);
        genderRadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);

        saveDetailsButton = (Button) findViewById(R.id.saveDetailsButton);
        calculateBmiButton = (Button) findViewById(R.id.calculateBmiButton);

        bmiTextView = (TextView) findViewById(R.id.bmiTextView);
        bmiTextView.setVisibility(View.INVISIBLE);

        final String userId = firebaseAuth.getCurrentUser().getUid();

        //load data

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
                // add spiner
                firstNameEditText.setText((String) snapshot.child("firstName").getValue());
                lastNameEditText.setText((String) snapshot.child("lastName").getValue());
                dieticanCheckBox.setChecked((Boolean) snapshot.child("dietician").getValue());
                weightEditText.setText((String) snapshot.child("weight").getValue());
                heightEditText.setText((String) snapshot.child("height").getValue());
                boolean isMale = (Boolean) snapshot.child("isMale").getValue();
                if(isMale){
                    genderRadioGroup.check(R.id.maleGender);
                } else {
                    genderRadioGroup.check(R.id.femaleGender);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //databaseReference.child(userId).setValue(user);


        calculateBmiButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(weightEditText.getText().toString().equals("") ||
                        heightEditText.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            "You must fill weight and height fields!", Toast.LENGTH_LONG).show();
                }

                int weight;
                try {
                    weight = Integer.parseInt(weightEditText.getText().toString());
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Weight must be a number!", Toast.LENGTH_LONG).show();
                    return;
                }
                int height;
                try {
                    height = Integer.parseInt(heightEditText.getText().toString());
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Height must be a number!", Toast.LENGTH_LONG).show();
                    return;
                }

                double res_bmi = (double)weight / (height*height);
                bmiTextView.setText(String.valueOf(res_bmi));
                bmiTextView.setVisibility(View.VISIBLE);
            }
        });

        saveDetailsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                // TODO: check if fields are legal and toast& return if not

                HashMap<String, Object> result = new HashMap<>();
                result.put("firstName", firstNameEditText.getText().toString());
                result.put("lastName", lastNameEditText.getText().toString());
                result.put("dietican", dieticanCheckBox.isChecked());
                result.put("weight", weightEditText.getText().toString());
                result.put("height", heightEditText.getText().toString());
                result.put("isMale", genderRadioGroup.getCheckedRadioButtonId() == R.id.maleGender);

                Model.instance.updateUser(userId, result);

                startActivity(new Intent(getApplicationContext(), AllActivity.class));

            }
        });
    }


    public void CalculateBMI(View view){

    }
}
