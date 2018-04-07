package com.example.chen.cuntada_app.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class DetailsActivity extends Activity{
    private Spinner weight, height;
    RadioGroup rg;
    RadioButton rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        rg = (RadioGroup) findViewById(R.id.gender);

        weight = findViewById(R.id.weight);
        String[] ListOfWeight = new String[]{"35", "36", "37", "38", "39", "40", "41", "42", "43", "44"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ListOfWeight);
        weight.setAdapter(adapter);

        height = findViewById(R.id.height);
        String[] ListOfHeight = new String[]{"1", "2", "3"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ListOfHeight);
        height.setAdapter(adapter1);
    }

    public void rbclick(View v){
        int radiobuttondid = rg.getCheckedRadioButtonId();
        RadioButton rb =  (RadioButton) findViewById(radiobuttondid);
        Toast.makeText(getBaseContext(),rb.getText(), Toast.LENGTH_LONG).show();

    }

    protected void RegisterButton(View view){
        String weight = findViewById(R.id.weight).toString();
        String height = findViewById(R.id.height).toString();
        RadioButton gender = findViewById(rg.getCheckedRadioButtonId());
    }
//    String[] ListOfWeight(){
//        String[] list = null;
//        for(int i=29;i<151;i++) {
//            for (int j = 0; j < 122; j++) {
//               String newI = Integer.toString(i);
//                list[j]=newI;
//            }
//        }
//        return list;
//    }

}
