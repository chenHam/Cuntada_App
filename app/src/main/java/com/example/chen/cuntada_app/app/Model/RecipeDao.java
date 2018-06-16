package com.example.chen.cuntada_app.app.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface RecipeDao {
    @Query("select * from Recipe")
    List<Recipe> getAll();


    @Query("SELECT * FROM Recipe WHERE publisherId=:publisherId")
    List<Recipe> getRecipesByPublisherId(String publisherId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Recipe... recipes);

    @Delete
    void delete(Recipe student);

    @Query("DELETE FROM Recipe WHERE name=:name")
    void deleteByName(String name);
}
