package com.example.chen.cuntada_app.app.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class Model {
    public static Model instance = new Model();
    ModelFirebase modelFirebase;
    private Model(){
        modelFirebase = new ModelFirebase();
    }

    public void cancellGetAllStudents() {
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

}
