package com.example.chen.cuntada_app.app;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Text;


public class RecipesActivity extends Activity{
    private Text ingredients, preparation;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        if (savedInstanceState == null) {
            AddRecipes fragmentAddRecipes = new AddRecipes();
            ShowRecipes fragmentShowRecipes = new ShowRecipes();
            FragmentTransaction tran = getFragmentManager().beginTransaction();
            tran.add(R.id.main_container, fragmentAddRecipes);
            tran.add(R.id.main_container, fragmentShowRecipes);
            tran.commit();
        }

        saveButton = (Button)findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                SaveRecipeButton(view);
            }
        });
    }

    protected void SaveRecipeButton(View view){
        String ingredients = findViewById(R.id.ingredients).toString();
        String preparation = findViewById(R.id.preparation).toString();
        //TODO: need to save to DB?
    }
}
