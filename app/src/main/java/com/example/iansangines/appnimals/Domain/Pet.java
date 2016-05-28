package com.example.iansangines.appnimals.Domain;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by ian on 09/04/2016.
 */
public class Pet {
    private int id;
    private String name;
    private String petType;
    private String bornDate;
    private String chipNumber;
    private String thumbnailPath;
    private String photoPath;
    private String especial;

    public Pet() {
        name = petType = bornDate = chipNumber = "";
        photoPath = thumbnailPath = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getBornDate() {
        return this.bornDate;
    }

    public String getPetType() {
        return this.petType;
    }

    public String getChipNumber() {
        return this.chipNumber;
    }

    public String getPhotoPath() {
        return this.photoPath;
    }

    public String getthumbnailPath() {
        return this.thumbnailPath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }


    public void setChipNumber(String chipNumber) {
        this.chipNumber = chipNumber;
    }

    public void setPetPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public void setPetthumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getEspecial() {
        return especial;
    }

    public void setEspecial(String especial) {
        this.especial = especial;
    }

}
