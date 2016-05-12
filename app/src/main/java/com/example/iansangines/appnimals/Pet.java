package com.example.iansangines.appnimals;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by ian on 09/04/2016.
 */
public class Pet{
    String name;
    String petType;
    String bornDate;
    String chipNumber;
    String thumbnailPath;
    String photoPath;

    public Pet(){
        name = petType = bornDate = chipNumber = "";
        photoPath = thumbnailPath = "";
    }

    public Pet(String name,  String bornDate, String petType , String chipNumber, String photoPath, String thumbnailPath) {
        this.name = name;
        this.bornDate = bornDate;
        this.petType = petType;
        this.chipNumber = chipNumber;
        this.photoPath = photoPath;
        this.thumbnailPath = thumbnailPath;
    }

    public String getName(){
        return this.name;
    }

    public String getBornDate(){
        return this.bornDate;
    }

    public String getPetType(){
        return this.petType;
    }

    public String getChipNumber(){return this.chipNumber;}

    public String getPhotoPath(){ return this.photoPath; }

    public String getthumbnailPath(){
        return this.thumbnailPath;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setBornDate(String bornDate){
        this.bornDate = bornDate;
    }

    public void setPetType(String petType){
        this.petType = petType;
    }


    public void setChipNumber(String chipNumber){
        this.chipNumber = chipNumber;
    }

    public void setPetPhotoPath(String photoPath){
        this.photoPath = photoPath;
    }

    public void setPetthumbnailPath(String thumbnailPath) { this.thumbnailPath = thumbnailPath; }
}
