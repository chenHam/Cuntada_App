package com.example.chen.cuntada_app.app.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.webkit.URLUtil;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Model {
    public static Model instance = new Model();
    ModelFirebase modelFirebase;
    private Model(){
        modelFirebase = new ModelFirebase();
    }

    public void cancellGetAllRecipes() {
        modelFirebase.cancellGetAllRecipes();
    }

    class RecipesListData extends MutableLiveData<List<Recipe>> {
        @Override
        protected void onActive() {
            super.onActive();
            // new thread tsks
            // 1. get the recipes list from the local DB
            RecipeAsynchDao.getAll(new RecipeAsynchDao.RecipeAsynchDaoListener<List<Recipe>>() {
                @Override
                public void onComplete(List<Recipe> data) {
                    // 2. update the live data with the new recipes list
                    setValue(data);
                    Log.d("TAG","got recipes from local DB " + data.size());

                    // 3. get the recipes list from firebase
                    modelFirebase.getAllRecipes(new ModelFirebase.GetAllRecipesListener() {
                        @Override
                        public void onSuccess(List<Recipe> recipeslist) {
                            // 4. update the live data with the new recipes list
                            setValue(recipeslist);
                            Log.d("TAG","got recipes from firebase " + recipeslist.size());

                            // 5. update the local DB
                            RecipeAsynchDao.insertAll(recipeslist, new RecipeAsynchDao.RecipeAsynchDaoListener<Boolean>() {
                                @Override
                                public void onComplete(Boolean data) {

                                }
                            });
                        }
                    });
                }
            });
        }

        @Override
        protected void onInactive() {
            super.onInactive();
            modelFirebase.cancellGetAllRecipes();
            Log.d("TAG","cancellGetAllRecipes");
        }

        public RecipesListData() {
            super();
            setValue(new LinkedList<Recipe>());
        }
    }

    RecipesListData recipesListData = new RecipesListData();

    public LiveData<List<Recipe>> getAllRecipes(){
        return recipesListData;
    }

    public void addRecipe(Recipe recipe){
        modelFirebase.addRecipe(recipe);
    }

    public void deleteReciple(final String name){
        RecipeAsynchDao.deleteRecipeByName(name, new RecipeAsynchDao.RecipeAsynchDaoListener<List<Recipe>>() {
            @Override
            public void onComplete(List<Recipe> data) {
                modelFirebase.deleteRecipe(name);
            }
        });
    }

    public void updateRecipe(String name, HashMap keyValues){
        modelFirebase.updateRecipe(name, keyValues);
    }

    ////////////////////////////////////////////////////////
    //  getting recipes by id
    ////////////////////////////////////////////////////////

    class RecipesByPublisherIdListData extends MutableLiveData<List<Recipe>> {

        private String publisherId;

        @Override
        protected void onActive() {

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            final String userId = firebaseAuth.getCurrentUser().getUid();

            super.onActive();
            // new thread tsks
            // 1. get the recipes list from the local DB
            RecipeAsynchDao.getRecipesByPublisherId(userId, new RecipeAsynchDao.RecipeAsynchDaoListener<List<Recipe>>() {
                @Override
                public void onComplete(List<Recipe> data) {
                    // 2. update the live data with the new recipes list
                    setValue(data);
                    Log.d("TAG","got recipes from local DB " + data.size());

                    // 3. get the recipes list from firebase
                    modelFirebase.getAllRecipesByPublisherId(userId, new ModelFirebase.GetAllRecipesListener() {
                        @Override
                        public void onSuccess(List<Recipe> recipeslist) {

                            // 4. update the live data with the new recipe list
                            setValue(recipeslist);
                            Log.d("TAG","got recipes from firebase " + recipeslist.size());

                            // 5. update the local DB
                            RecipeAsynchDao.insertAll(recipeslist, new RecipeAsynchDao.RecipeAsynchDaoListener<Boolean>() {
                                @Override
                                public void onComplete(Boolean data) {

                                }
                            });
                        }
                    });
                }
            });
        }

        @Override
        protected void onInactive() {
            super.onInactive();
            modelFirebase.cancellGetAllRecipes();
            Log.d("TAG","cancellGetAllRecipes");
        }

        public RecipesByPublisherIdListData() {
            super();
            //this.publisherId = publisherId;
            setValue(new LinkedList<Recipe>());
        }
    }


    RecipesByPublisherIdListData recipesByIdListData = new RecipesByPublisherIdListData();

    public LiveData<List<Recipe>> getAllRecipesByPublisherId(String publisherId){
        return recipesByIdListData;
    }


    ////////////////////////////////////////////////////////
    //  HAndle Image Files
    ////////////////////////////////////////////////////////



    public interface SaveImageListener{
        void onDone(String url);
    }

    public void saveImage(Bitmap imageBitmap, SaveImageListener listener) {
        modelFirebase.saveImage(imageBitmap,listener);
    }



    public interface GetImageListener{
        void onDone(Bitmap imageBitmap);
    }
    public void getImage(final String url, final GetImageListener listener ){
        String localFileName = URLUtil.guessFileName(url, null, null);
        final Bitmap image = loadImageFromFile(localFileName);
        if (image == null) {                                      //if image not found - try downloading it from parse
            modelFirebase.getImage(url, new GetImageListener() {
                @Override
                public void onDone(Bitmap imageBitmap) {
                    if (imageBitmap == null) {
                        listener.onDone(null);
                    }else {
                        //2.  save the image localy
                        String localFileName = URLUtil.guessFileName(url, null, null);
                        Log.d("TAG", "save image to cache: " + localFileName);
                        saveImageToFile(imageBitmap, localFileName);
                        //3. return the image using the listener
                        listener.onDone(imageBitmap);
                    }
                }
            });
        }else {
            Log.d("TAG","OK reading cache image: " + localFileName);
            listener.onDone(image);
        }
    }

    // Store / Get from local mem
    private void saveImageToFile(Bitmap imageBitmap, String imageFileName){
        if (imageBitmap == null) return;
        try {
            File dir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File imageFile = new File(dir,imageFileName);
            imageFile.createNewFile();

            OutputStream out = new FileOutputStream(imageFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

            //addPicureToGallery(imageFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap loadImageFromFile(String imageFileName){
        Bitmap bitmap = null;
        try {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File imageFile = new File(dir,imageFileName);
            InputStream inputStream = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(inputStream);
            Log.d("tag","got image from cache: " + imageFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    ////////////////////////////////////////////////////////
    //  Handle User
    ////////////////////////////////////////////////////////

    public void addUser(String userId, User user){
        modelFirebase.addUser(userId, user);
    }

    public void updateUser(String userId, HashMap keyValues){
        modelFirebase.updateUser(userId, keyValues);
    }
}
