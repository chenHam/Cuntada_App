package com.example.chen.cuntada_app.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.chen.cuntada_app.app.Model.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, emailEditText, pwEditText,
            confirmPwEditText, weightEditText, heightEditText;
    private CheckBox dieticanCheckBox;

    private RadioGroup genderRadioGroup;
    private Button registerButton;
    private Boolean dietBoolean;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register_1);

        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        pwEditText = (EditText) findViewById(R.id.pwEditText);
        confirmPwEditText = (EditText) findViewById(R.id.confirmPwEditText);
        dieticanCheckBox = (CheckBox) findViewById(R.id.dieticanCheckBox);
        weightEditText = (EditText) findViewById(R.id.weightEditText);
        heightEditText = (EditText) findViewById(R.id.heightEditText);
        genderRadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
        registerButton = (Button) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String firstName = firstNameEditText.getText().toString().trim();
                String lastName = lastNameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String pw = pwEditText.getText().toString().trim();
                String confirmPw = confirmPwEditText.getText().toString().trim();
                boolean isDietican = dieticanCheckBox.isChecked();
                String weight = weightEditText.getText().toString().trim();
                String height = heightEditText.getText().toString().trim();

                int gender = genderRadioGroup.getCheckedRadioButtonId();
                boolean isMale = true;
                if(gender == R.id.femaleGender){
                    isMale = false;
                }

                boolean res = validateInput(firstName, lastName, email, pw, confirmPw, weight, height);

                if(!res){
                    return; // + error
                }

                final User user = new User(firstName, lastName, email, pw, isDietican, weight, height, isMale);
                //Model.instance.addUser(user);



                final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.createUserWithEmailAndPassword(email, pw)
                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    String userId = firebaseAuth.getCurrentUser().getUid();
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
                                    databaseReference.child(userId).setValue(user);


                                    startActivity(new Intent(getApplicationContext(),AllActivity.class));
                                } else {
                                    Log.d("Tokyo", "Not added user: ");
                                }
                            }
                        });
            }
        });


    }

    public boolean validateInput(String firstName, String lastName, String email, String pw,
                                 String confirmPw, String weight, String height){

        /*boolean res = false;
        if(firstName.equals("") || lastName.equals("") || email.equals("") || pw.equals("") ||
                confirmPw.equals("") || weight.equals("") || height.equals("")){
            // output
            return res;
        }
        if(!pw.equals(confirmPw)){
            //output
            return res;
        }*/

        return true;

    }


}
