package com.example.chen.cuntada_app.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chen.cuntada_app.app.Model.Model;
import com.example.chen.cuntada_app.app.Model.Recipe;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class NewRecipeFragment extends Fragment {


    private static final String ARG_NAME = "ARG_NAME";
    private static final String ARG_ID = "ARG_ID";

    public NewRecipeFragment() {
        // Required empty public constructor
    }

    EditText nameEditText;
    EditText categoryEditText;
    EditText ingredientsEditText;
    EditText instructionsEditText;
    ImageView avatar;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    //ProgressBar progress;
    Button addRecipeButton;
    Button editPictureButton;
    Spinner categorySpinner;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_recipe, container, false);

        nameEditText = view.findViewById(R.id.nameEditText);
        categoryEditText = view.findViewById(R.id.categoryEditText);
        ingredientsEditText = view.findViewById(R.id.ingredientsEditText);
        instructionsEditText = view.findViewById(R.id.instructionsEditText);
        avatar = view.findViewById(R.id.recipeImage);
        addRecipeButton  = view.findViewById(R.id.addRecipeButton);
        editPictureButton = view.findViewById(R.id.editPictureButton);
        categorySpinner = (Spinner) view.findViewById(R.id.categorySpinner);


        progressDialog = new ProgressDialog(getActivity());
        //progress . setVisibility(View.GONE);

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progress . setVisibility(View.VISIBLE);

                final Recipe recipe = new Recipe();
                recipe.name = nameEditText.getText().toString();
//                recipe.category = categoryEditText.getText().toString();
                recipe.category = categorySpinner.getSelectedItem().toString();
                recipe.ingredients = ingredientsEditText.getText().toString();
                recipe.instructions = instructionsEditText.getText().toString();

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                String userId = firebaseAuth.getCurrentUser().getUid();

                recipe.publisherId = userId;

                if(recipe.name.equals("") || recipe.category.equals("") ||
                        recipe.ingredients.equals("") || recipe.instructions.equals("")){
                    Toast.makeText(getActivity(), "You have to fill all fields!", Toast.LENGTH_LONG).show();
                    return;
                }

                if(imageBitmap == null){
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "You have to add a picture!", Toast.LENGTH_LONG).show();
                    return;
                }

                progressDialog.setMessage("Saving Recipe...");
                progressDialog.show();

                //save image
                if (imageBitmap != null) {
                    Model.instance.saveImage(imageBitmap, new Model.SaveImageListener() {
                        @Override
                        public void onDone(String url) {
                            recipe.avatar = url;
                            Model.instance.addRecipe(recipe);
                            progressDialog.dismiss();
                            getActivity().getSupportFragmentManager().popBackStack();
                        }
                    });
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
        return view;
    }

//    // add items into spinner dynamically
//    public void addItemsOnSpinner2() {
//
//        List<String> list = new ArrayList<String>();
//        list.add("list 1");
//        list.add("list 2");
//        list.add("list 3");
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, list);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        categorySpinner.setAdapter(dataAdapter);
//    }

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
