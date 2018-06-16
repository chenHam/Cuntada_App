package com.example.chen.cuntada_app.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.chen.cuntada_app.app.Model.Model;
import com.example.chen.cuntada_app.app.Model.Recipe;
import com.google.firebase.auth.FirebaseAuth;

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

        //progress . setVisibility(View.GONE);

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progress . setVisibility(View.VISIBLE);

                final Recipe recipe = new Recipe();
                recipe.name = nameEditText.getText().toString();
                recipe.category = categoryEditText.getText().toString();
                recipe.ingredients = ingredientsEditText.getText().toString();
                recipe.instructions = instructionsEditText.getText().toString();

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                String userId = firebaseAuth.getCurrentUser().getUid();

                recipe.publisherId = userId;


                //save image
                if (imageBitmap != null) {
                    Model.instance.saveImage(imageBitmap, new Model.SaveImageListener() {
                        @Override
                        public void onDone(String url) {
                            //save student obj
                            Log.d("Tokyo", url);
                            recipe.avatar = url;
//                            Model.instance.addStudent(recipe);
//                            getActivity().getSupportFragmentManager().popBackStack();
                        }
                    });
                }
                Model.instance.addStudent(recipe);
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        /*Button cancel = view.findViewById(R.id.new_student_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });*/
        /*if (savedInstanceState != null) {
            String name = savedInstanceState.getString(ARG_NAME);
            if (name != null) {
                nameEt.setText(name);
            }
            String id = savedInstanceState.getString(ARG_ID);
            if (id != null) {
                idEt.setText(id);
            }
        }*/

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
