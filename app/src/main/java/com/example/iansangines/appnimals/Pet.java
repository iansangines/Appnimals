package com.example.iansangines.appnimals;

import android.net.Uri;

/**
 * Created by ian on 09/04/2016.
 */
public class Pet {
    String name;
    String petType;
    String petSubtype;
    String bornDate;
    String chipNumber;
    Uri photoPath;

    public Pet(){
        name = petType = petSubtype = bornDate = chipNumber = "";
        photoPath = null;
    }

    public Pet(String name,  String bornDate, String petType,String petSubtype , String chipNumber, Uri photoPath) {
        this.name = name;
        this.bornDate = bornDate;
        this.petType = petType;
        this.petSubtype = petSubtype;
        this.chipNumber = chipNumber;
        this.photoPath = photoPath;
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

    public String getPetSubtype(){ return this.petSubtype;}

    public String getChipNumber(){return this.chipNumber;}

    public Uri getPhotoPath(){
        return this.photoPath;
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

    public void setPetSubtype(String petSubtype) {this.petSubtype = petSubtype;}

    public void setChipNumber(String chipNumber){
        this.chipNumber = chipNumber;
    }

    public void setPetPhotoPath(Uri photoPath){
        this.photoPath = photoPath;
    }
}
