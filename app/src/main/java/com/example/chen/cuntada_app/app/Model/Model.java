package com.example.chen.cuntada_app.app.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.webkit.URLUtil;

import com.example.chen.cuntada_app.app.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    class StudentListData extends MutableLiveData<List<Recipe>> {
        @Override
        protected void onActive() {
            super.onActive();
            // new thread tsks
            // 1. get the students list from the local DB
            RecipeAsynchDao.getAll(new RecipeAsynchDao.RecipeAsynchDaoListener<List<Recipe>>() {
                @Override
                public void onComplete(List<Recipe> data) {
                    // 2. update the live data with the new student list
                    setValue(data);
                    Log.d("TAG","got students from local DB " + data.size());

                    // 3. get the student list from firebase
                    modelFirebase.getAllRecipes(new ModelFirebase.GetAllRecipesListener() {
                        @Override
                        public void onSuccess(List<Recipe> studentslist) {
                            // 4. update the live data with the new student list
                            setValue(studentslist);
                            Log.d("TAG","got students from firebase " + studentslist.size());

                            // 5. update the local DB
                            RecipeAsynchDao.insertAll(studentslist, new RecipeAsynchDao.RecipeAsynchDaoListener<Boolean>() {
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
            Log.d("TAG","cancellGetAllStudents");
        }

        public StudentListData() {
            super();
            //setValue(AppLocalDb.db.studentDao().getAll());
            setValue(new LinkedList<Recipe>());
        }
    }

    StudentListData studentListData = new StudentListData();

    public LiveData<List<Recipe>> getAllStudents(){
        return studentListData;
    }

    public void addStudent(Recipe recipe){
        modelFirebase.addRecipe(recipe);
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

    public void addUser(User user){

    }
}
