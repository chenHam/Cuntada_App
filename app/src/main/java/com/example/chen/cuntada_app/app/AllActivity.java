package com.example.chen.cuntada_app.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.chen.cuntada_app.app.Model.Model;
import com.example.chen.cuntada_app.app.Model.Recipe;

public class AllActivity extends AppCompatActivity {


    Button RecipesButton;
    Button ForumButton;
    Button DetailsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);

        Log.d("Tokyo", "AllActivity");


        /*final Recipe r = new Recipe();
        r.name = "shuit eruka";
        r.category = "tasty food";
        r.ingredients = "ingredi";
        r.instructions = "instru";
        r.publisherId = "sadajukdnaks";
        Model.instance.addStudent(r);*/

        RecipesButton = (Button) findViewById(R.id.recipes);
        RecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recipes(view);
            }
        });
        /*ForumButton = (Button) findViewById(R.id.forum);
        ForumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Forum(view);
            }
        });
        DetailsButton = (Button) findViewById(R.id.privateDetails);
        DetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Details(view);
            }
        });*/

    }

    public void Details(View view){
        finish();
        startActivity(new Intent(getApplicationContext(), DetailsActivity.class));

    }
    public void Recipes(View view){
        //finish();
        startActivity(new Intent(getApplicationContext(), RecipesActivity.class));

    }
    public void Forum(View view){
        finish();
       startActivity(new Intent(getApplicationContext(), DetailsActivity.class));

    }


}
