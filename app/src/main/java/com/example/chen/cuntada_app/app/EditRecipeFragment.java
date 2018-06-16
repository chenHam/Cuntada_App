package com.example.chen.cuntada_app.app;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chen.cuntada_app.app.Model.Model;
import com.example.chen.cuntada_app.app.Model.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class EditRecipeFragment extends Fragment {
    EditText nameEditText;
    EditText categoryEditText;
    EditText ingredientsEditText;
    EditText instructionsEditText;
    ImageView avatar;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    //ProgressBar progress;
    Button addRecipeButton;
    Button editPictureButton;
    Button deleteRecipeButton;

    public EditRecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_recipe, container, false);

        nameEditText = view.findViewById(R.id.nameEditText);
        categoryEditText = view.findViewById(R.id.categoryEditText);
        ingredientsEditText = view.findViewById(R.id.ingredientsEditText);
        instructionsEditText = view.findViewById(R.id.instructionsEditText);
        avatar = view.findViewById(R.id.recipeImage);
        addRecipeButton = view.findViewById(R.id.addRecipeButton);
        editPictureButton = view.findViewById(R.id.editPictureButton);
        deleteRecipeButton = view.findViewById(R.id.deleteRecipeButton);

        nameEditText.setText(getArguments().getString("name"));
        categoryEditText.setText(getArguments().getString("category"));
        ingredientsEditText.setText(getArguments().getString("ingredients"));
        instructionsEditText.setText(getArguments().getString("instructions"));

        Bitmap bitmapimage = getArguments().getParcelable("avatar");
        avatar.setImageBitmap(bitmapimage);

        nameEditText.setEnabled(false);

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Recipe recipe = new Recipe();
                recipe.name = nameEditText.getText().toString();
                recipe.category = categoryEditText.getText().toString();
                recipe.ingredients = ingredientsEditText.getText().toString();
                recipe.instructions = instructionsEditText.getText().toString();

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                String userId = firebaseAuth.getCurrentUser().getUid();

                recipe.publisherId = userId;

                if (recipe.name.equals("") || recipe.category.equals("")
                        || recipe.ingredients.equals("") || recipe.instructions.equals("")) {
                    Toast.makeText(getActivity(), "You have to fill all fields!", Toast.LENGTH_LONG).show();
                    return;
                }

                final HashMap<String, Object> result = new HashMap<>();
                result.put("category", recipe.category);
                result.put("ingredients", recipe.ingredients);
                result.put("instructions", recipe.instructions);

                //save image
                if (imageBitmap != null) {
                    Model.instance.saveImage(imageBitmap, new Model.SaveImageListener() {
                        @Override
                        public void onDone(String url) {
                            recipe.avatar = url;
                            result.put("avatar", recipe.avatar);
                            Model.instance.updateRecipe(recipe.name, result);
                            getActivity().getSupportFragmentManager().popBackStack();
                        }
                    });
                } else {
                    Model.instance.updateRecipe(recipe.name, result);
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });
        editPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open camera
                Intent takePictureIntent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        deleteRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model.instance.deleteReciple(nameEditText.getText().toString());
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return view;

    }

    Bitmap imageBitmap;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE &&
                resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            avatar.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }
    @Override
    public void  onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        //bundle.putString(ARG_NAME, nameEt.getText().toString());
        //bundle.putString(ARG_ID, idEt.getText().toString());
    }
}
