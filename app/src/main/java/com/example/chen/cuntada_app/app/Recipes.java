package com.example.chen.cuntada_app.app;

import android.widget.Spinner;

import java.util.HashMap;

public class Recipes {

    String RecipeName;
    String Ingredients;
    String Preparation;
    String Category;

    public Recipes(String name, String ingredients,String preparation, String category){

        RecipeName = name;
        Ingredients = ingredients;
        Preparation = preparation;
        Category = category;

    }

    HashMap<String,Object> toJson(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("RecipeName", RecipeName);
        result.put("Ingredients", Ingredients);
        result.put("Preparation", Preparation);
        result.put("Category", Category);
        return result;
    }



}
