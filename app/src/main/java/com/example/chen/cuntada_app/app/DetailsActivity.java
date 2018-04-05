package com.example.chen.cuntada_app.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


public class DetailsActivity extends Activity{
    private Spinner weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addItemsOnSpinnerWeight();
    }
    public void addItemsOnSpinnerWeight(){
        weight = (Spinner) findViewById(R.id.weight);
        List<Double> list = new ArrayList<Double>();
        list.add(35.0);
        list.add(35.5);
        list.add(40.0);
        list.add(45.0);

        ArrayAdapter<Double> dataAdapter = new ArrayAdapter<Double>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weight.setAdapter(dataAdapter);

    }
}
