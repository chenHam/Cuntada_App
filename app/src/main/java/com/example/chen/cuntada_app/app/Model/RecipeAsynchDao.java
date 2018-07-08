package com.example.chen.cuntada_app.app.Model;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class RecipeAsynchDao {


    interface RecipeAsynchDaoListener<T>{
        void onComplete(T data);
    }

    static public void getAll(final RecipeAsynchDaoListener<List<Recipe>> listener) {
        class MyAsynchTask extends AsyncTask<String,String,List<Recipe>>{
            @Override
            protected List<Recipe> doInBackground(String... strings) {
                List<Recipe> stList = AppLocalDb.db.recipeDao().getAll();
                return stList;
            }

            @Override
            protected void onPostExecute(List<Recipe> recipes) {
                super.onPostExecute(recipes);
                listener.onComplete(recipes);
            }
        }
        MyAsynchTask task = new MyAsynchTask();
        task.execute();
    }

    static public void getRecipesByPublisherId(final String publisherId, final RecipeAsynchDaoListener<List<Recipe>> listener) {
        class MyAsynchTask extends AsyncTask<String,String,List<Recipe>>{
            @Override
            protected List<Recipe> doInBackground(String... strings) {
                List<Recipe> stList = AppLocalDb.db.recipeDao().getRecipesByPublisherId(publisherId);
                return stList;
            }

            @Override
            protected void onPostExecute(List<Recipe> recipes) {
                super.onPostExecute(recipes);
                listener.onComplete(recipes);
            }
        }
        MyAsynchTask task = new MyAsynchTask();
        task.execute();
    }



    static void insertAll(final List<Recipe> recipes, final RecipeAsynchDaoListener<Boolean> listener){
        class MyAsynchTask extends AsyncTask<List<Recipe>,String,Boolean>{
            @Override
            protected Boolean doInBackground(List<Recipe>... recipes) {
                for (Recipe re:recipes[0]) {
                    AppLocalDb.db.recipeDao().insertAll(re);
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPostExecute(success);
                listener.onComplete(success);
            }
        }
        MyAsynchTask task = new MyAsynchTask();
        task.execute(recipes);
    }

    static public void deleteRecipeByName(final String name, final RecipeAsynchDaoListener<List<Recipe>> listener) {
        class MyAsynchTask extends AsyncTask<String,String,List<Recipe>>{
            @Override
            protected List<Recipe> doInBackground(String... strings) {
                //List<Recipe> stList = AppLocalDb.db.recipeDao().getRecipesByPublisherId(publisherId);
                AppLocalDb.db.recipeDao().deleteByName(name);
                return null;
            }

            @Override
            protected void onPostExecute(List<Recipe> recipes) {
                super.onPostExecute(recipes);
                listener.onComplete(recipes);
            }
        }
        MyAsynchTask task = new MyAsynchTask();
        task.execute();
    }


}
