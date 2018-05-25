package com.example.chen.cuntada_app.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.w3c.dom.Text;


public class AddRecipes extends Fragment {
    private Text ingredients, preparation;
    Button saveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_recipes, container, false);

        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            AddRecipes fragment = new AddRecipes();
            FragmentTransaction tran = getFragmentManager().beginTransaction();
            tran.add(R.id.main_container, fragment);
            tran.commit();
        }

        saveButton = (Button)view.findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                SaveRecipeButton(view);
            }
        });
        return view;

    }

    protected void SaveRecipeButton(View view){
        String ingredients = view.findViewById(R.id.ingredients).toString();
        String preparation = view.findViewById(R.id.preparation).toString();
        //TODO: need to save to DB?
    }
}
