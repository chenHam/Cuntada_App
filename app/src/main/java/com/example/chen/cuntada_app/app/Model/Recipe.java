package com.example.chen.cuntada_app.app.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Recipe {

    @NonNull
    @PrimaryKey
    public String name;
    public String category;
    public String ingredients;
    public String instructions;
    public String publisherId;
    public String avatar;

    public String getName(){
        return name;
    }
    public String getCategory(){
        return category;
    }
    public String getIngredients(){
        return ingredients;
    }
    public String getInstructions(){
        return instructions;
    }
    public String getPublisherId(){
        return publisherId;
    }
    public String getAvatar() {
        return avatar;
    }

    //set functions
    public void setName(String name){
        this.name = name;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public void setIngredients(String ingredients){
        this.ingredients = ingredients;
    }
    public void setInstructions(String instructions){
        this.instructions = instructions;
    }
    public void setPublisherId(String publisherId){
        this.publisherId = publisherId;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
