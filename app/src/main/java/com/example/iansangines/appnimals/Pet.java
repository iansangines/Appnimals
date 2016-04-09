package com.example.iansangines.appnimals;

/**
 * Created by ian on 09/04/2016.
 */
public class Pet {
    String name;
    String petType;
    String bornDate;
    String chipNumber;
    int petPhoto;

    public Pet(String name, String petType, String bornDate, String chipNumber, int petPhoto) {
        this.name = name;
        this.bornDate = bornDate;
        this.petType = petType;
        this.chipNumber = chipNumber;
        this.petPhoto = petPhoto;

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

    public String getChipNumber(){
        return this.chipNumber;
    }

    public int getPetPhoto(){
        return this.petPhoto;
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

    public void setPetPhoto(int petPhoto){
        this.petPhoto = petPhoto;
    }
}
