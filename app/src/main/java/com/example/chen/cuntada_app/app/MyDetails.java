package com.example.chen.cuntada_app.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MyDetails extends Activity{

    Button calculateButton;
    EditText target_weight,body_weight,height_edit;
    TextView bmi;
    int height,weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_details_activity);

        target_weight = (EditText) findViewById(R.id.target_weight);
        body_weight = (EditText) findViewById(R.id.body_weight);
        height_edit = (EditText) findViewById(R.id.height_edit);
        bmi = (TextView) findViewById(R.id.bmi_edit);

        calculateButton = (Button)findViewById(R.id.button_calculate);
        calculateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                CalculateBMI(view);
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
