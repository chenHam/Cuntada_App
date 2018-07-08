package com.example.chen.cuntada_app.app;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.example.chen.cuntada_app.app.Model.Model;
import com.example.chen.cuntada_app.app.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, emailEditText, pwEditText,
            confirmPwEditText, weightEditText, heightEditText;
//    private CheckBox dieticanCheckBox;

    private RadioGroup genderRadioGroup;
    private Button registerButton;
//    private Boolean dietBoolean;
    private ProgressDialog progressDialog;
    Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register_1);

        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        pwEditText = (EditText) findViewById(R.id.pwEditText);
        confirmPwEditText = (EditText) findViewById(R.id.confirmPwEditText);
//        dieticanCheckBox = (CheckBox) findViewById(R.id.dieticanCheckBox);
        weightEditText = (EditText) findViewById(R.id.weightEditText);
        heightEditText = (EditText) findViewById(R.id.heightEditText);
        genderRadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
        genderRadioGroup.check(R.id.maleGender);
        registerButton = (Button) findViewById(R.id.registerButton);

        progressDialog = new ProgressDialog(this);

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String firstName = firstNameEditText.getText().toString().trim();
                String lastName = lastNameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String pw = pwEditText.getText().toString().trim();
                String confirmPw = confirmPwEditText.getText().toString().trim();
//                boolean isDietican = dieticanCheckBox.isChecked();
                String weight = weightEditText.getText().toString().trim();
                String height = heightEditText.getText().toString().trim();

                int gender = genderRadioGroup.getCheckedRadioButtonId();
                boolean isMale = true;
                if(gender == R.id.femaleGender){
                    isMale = false;
                }

                boolean res = validateInput(firstName, lastName, email, pw, confirmPw, weight, height);

                if(!res){
                    return;
                }

                final User user = new User(firstName, lastName, email, pw, weight, height, isMale);

                progressDialog.setMessage("Creating user...");
                progressDialog.show();

                final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.createUserWithEmailAndPassword(email, pw)
                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if(task.isSuccessful()){
                                    String userId = firebaseAuth.getCurrentUser().getUid();
                                    Model.instance.addUser(userId, user);
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

        boolean res = false;
        if(firstName.equals("") || lastName.equals("") || email.equals("") || pw.equals("") ||
                confirmPw.equals("") || weight.equals("") || height.equals("")){
            Toast.makeText(getApplicationContext(), "You have to fill all fields!", Toast.LENGTH_LONG).show();

            return res;
        }

        if(!pw.equals(confirmPw)){
            Toast.makeText(getApplicationContext(), "Passwords don't match!", Toast.LENGTH_LONG).show();
            return res;
        }

        if(pw.length()<6){
            Toast.makeText(getApplicationContext(), "Password must contain at least six characters!", Toast.LENGTH_LONG).show();
            return res;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(getApplicationContext(), "Please enter a valid Email!", Toast.LENGTH_LONG).show();

            return res;

        }

        try {
            Integer.parseInt(weight);
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "Weight must be a number!", Toast.LENGTH_LONG).show();
            if (regex.matcher(weight).find()){
                Toast.makeText(getApplicationContext(), "test!", Toast.LENGTH_LONG).show();
            }

            return res;
        }

        try {
            Integer.parseInt(height);
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "Height must be a number!", Toast.LENGTH_LONG).show();
            return res;
        }

        return true;

    }





}
