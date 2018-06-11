package com.example.chen.cuntada_app.app;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.chen.cuntada_app.app.Model.Model;
import com.example.chen.cuntada_app.app.Model.Recipe;

import java.util.List;

public class RecipeListViewModel extends ViewModel {
    LiveData<List<Recipe>> data;

    public LiveData<List<Recipe>> getData(){
        Log.d("Tokyo", "@@@@@@@ before get all students");
        data = Model.instance.getAllStudents();
        return data;
    }
}
