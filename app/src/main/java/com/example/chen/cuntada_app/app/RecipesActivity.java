package com.example.chen.cuntada_app.app;

import android.app.Fragment;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

//package com.example.chen.cuntada_app.app;
//
//import android.app.Activity;
//import android.app.Fragment;
//import android.app.FragmentTransaction;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ServerValue;
//import com.google.firebase.database.ValueEventListener;
//
//import org.w3c.dom.Text;
//
//import java.util.HashMap;
//
//
public class RecipesActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

    }
}
//    private static final String TAG = MainActivity.class.getSimpleName();
//
//    private DatabaseReference RecipeDB;
//    EditText recipes_name, ingredients, preparation;
//    Button saveButton;
//    Spinner category;
//    String rn, ing, pr, ca;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_recipes, container, false);
//        RecipeDB = FirebaseDatabase.getInstance().getReference("recipes");
//
//        EditText recipes_name = view.findViewById(R.id.recipes_name);
//        EditText ingredients = view.findViewById(R.id.ingredients);
//        EditText preparation = view.findViewById(R.id.preparation);
//        String[] categoryList = new String[]{"Vegan", "Gluten free", "Kosher"};
//
//
//        if (savedInstanceState == null) {
//            AddRecipes fragmentAddRecipes = new AddRecipes();
//            ShowRecipes fragmentShowRecipes = new ShowRecipes();
//            FragmentTransaction tran = getFragmentManager().beginTransaction();
//            tran.add(R.id.main_container, fragmentAddRecipes);
//            tran.add(R.id.main_container, fragmentShowRecipes);
//            tran.commit();
//        }
//
//        saveButton = (Button) view.findViewById(R.id.button_save_recipe);
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), AllActivity.class);
//                getActivity().startActivity(intent);
//                SaveRecipeButton(view);
//            }
//        });
//        return view;
//    }
//
//    protected void SaveRecipeButton(View view){
//        rn = recipes_name.getText().toString().trim();
//        ing = ingredients.getText().toString().trim();
//        pr = preparation.getText().toString().trim();
//        ca = category.toString().trim();
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("recipe").child(rn);
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//
//
//                }
//                else{
//                    CreateRecipe();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }
//
//    void CreateRecipe(){
//
//        String id = RecipeDB.push().getKey();
//        Recipes recipe = new Recipes(rn,ing,pr,ca);
//        addRecipe(recipe, new OnCreation() {
//            @Override
//            public void onCompletion(boolean success) {
//                Log.d("TAG","created");
//                //Toast.makeText(getActivity(), "User Details Updated!", Toast.LENGTH_SHORT).show();
//            }
//        });
//        Toast.makeText(getActivity(),"added", Toast.LENGTH_LONG).show();
//        startActivity(new Intent(getActivity().getApplicationContext(), AllActivity.class));
//
//
//    }
//    public interface OnCreation{
//        public void onCompletion(boolean success);
//    }
//    public static void addRecipe(Recipes recipe, final OnCreation listener) {
//        Log.d("TAG", "add user to firebase");
//        HashMap<String, Object> json = recipe.toJson();
//        json.put("lastUpdated", ServerValue.TIMESTAMP);
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("recipe");
//        DatabaseReference ref = myRef.child(recipe.RecipeName);
//        ref.setValue(json, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                if (databaseError != null) {
//                    Log.e("TAG", "Error: User could not be saved "
//                            + databaseError.getMessage());
//                    listener.onCompletion(false);
//                } else {
//                    Log.e("TAG", "Success : User saved successfully.");
//                    listener.onCompletion(true);
//                }
//            }
//        });
//
//    }
//
//}
