package com.example.chen.cuntada_app.app.Model;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.example.chen.cuntada_app.app.MyApplication;



@Database(entities = {Recipe.class}, version = 1)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract RecipeDao recipeDao();
}

public class AppLocalDb{
    static public AppLocalDbRepository db = Room.databaseBuilder(MyApplication.context,
            AppLocalDbRepository.class,
            "dbFileName.db").fallbackToDestructiveMigration().build();
}


