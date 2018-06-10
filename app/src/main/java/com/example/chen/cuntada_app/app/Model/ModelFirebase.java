package com.example.chen.cuntada_app.app.Model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class ModelFirebase {
    public void addRecipe(Recipe recipe){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        // name? is the key
        mDatabase.child("recipes").child(recipe.name).setValue(recipe);
    }

    public void cancellGetAllRecipes() {
        DatabaseReference reRef = FirebaseDatabase.getInstance().getReference().child("recipes");
        reRef.removeEventListener(eventListener);
    }


    interface GetAllRecipesListener{
        public void onSuccess(List<Recipe> recipeslist);
    }

    ValueEventListener eventListener;

    public void getAllRecipes(final GetAllRecipesListener listener) {
        DatabaseReference reRef = FirebaseDatabase.getInstance().getReference().child("recipes");

        eventListener = reRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Recipe> recipesList = new LinkedList<>();

                for (DataSnapshot stSnapshot: dataSnapshot.getChildren()) {
                    Recipe recipe = stSnapshot.getValue(Recipe.class);
                    recipesList.add(recipe);
                }
                listener.onSuccess(recipesList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
