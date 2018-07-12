package com.example.chen.cuntada_app.app.View;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chen.cuntada_app.app.MainActivity;
import com.example.chen.cuntada_app.app.Model.Model;
import com.example.chen.cuntada_app.app.R;
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
    EditText weightEditText;
    EditText heightEditText;
    RadioGroup genderRadioGroup;
    ImageView image;


    Button saveDetailsButton;
    Button calculateBmiButton;

    TextView bmiTextView;
    private ProgressDialog progressDialog;

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
        weightEditText = (EditText) findViewById(R.id.weightEditText);
        heightEditText = (EditText) findViewById(R.id.heightEditText);
        genderRadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
        image = findViewById(R.id.UserImage);


        saveDetailsButton = (Button) findViewById(R.id.saveDetailsButton);
        calculateBmiButton = (Button) findViewById(R.id.calculateBmiButton);

        bmiTextView = (TextView) findViewById(R.id.bmiTextView);
        bmiTextView.setVisibility(View.INVISIBLE);

        final String userId = firebaseAuth.getCurrentUser().getUid();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading user data...");
        progressDialog.show();


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                firstNameEditText.setText((String) snapshot.child("firstName").getValue());
                lastNameEditText.setText((String) snapshot.child("lastName").getValue());
                weightEditText.setText((String) snapshot.child("weight").getValue());
                heightEditText.setText((String) snapshot.child("height").getValue());
                boolean isMale = (Boolean) snapshot.child("isMale").getValue();
                if(isMale){
                    genderRadioGroup.check(R.id.maleGender);
                } else {
                    genderRadioGroup.check(R.id.femaleGender);
                }
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


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
                double height_double = (double)height / 100;
                double weight_double = (double)weight;
                double res_bmi =  weight_double / (height_double*height_double);

                bmiTextView.setText(String.format("%.2f", res_bmi));
                bmiTextView.setVisibility(View.VISIBLE);
            }
        });

        saveDetailsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String weightStr = weightEditText.getText().toString();
                String heightStr = heightEditText.getText().toString();

                if(firstName.equals("") || lastName.equals("") || weightStr.equals("") || heightStr.equals("")){
                    Toast.makeText(getApplicationContext(), "You must fill weight and height fields!", Toast.LENGTH_LONG).show();
                    return;
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

                HashMap<String, Object> result = new HashMap<>();
                result.put("firstName", firstName);
                result.put("lastName", lastName);
                result.put("weight", weightStr);
                result.put("height", heightStr);
                result.put("isMale", genderRadioGroup.getCheckedRadioButtonId() == R.id.maleGender);

                Model.instance.updateUser(userId, result);

                startActivity(new Intent(getApplicationContext(), AllActivity.class));

            }
        });
    }

}
