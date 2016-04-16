package com.example.iansangines.appnimals;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by iansangines on 06/04/2016.
 */
public class PetDBController extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "AppnimalsDB";
    private static final String PET_TABLE_NAME = "PETS";
    private static final String PET_COLUMN_ID = "ID";
    private static final String PET_COLUMN_NAME = "NAME";
    private static final String PET_COLUMN_DATA = "DATA";
    private static final String PET_COLUMN_TYPE = "TYPE";
    private static final String PET_COLUMN_SUBTYPE = "SUBTYPE";
    private static final String PET_COLUMN_CHIP= "CHIP";
    private static final String PET_COLUMN_IMGPATH= "IMGPATH";
    private static final String CREATE_PET_TABLE = "CREATE TABLE " + PET_TABLE_NAME + " (" +
            PET_COLUMN_ID +  " INTEGER PRIMARY KEY AUTOINCREMENT, " + PET_COLUMN_NAME + " TEXT, " +
            PET_COLUMN_DATA + " TEXT, " + PET_COLUMN_TYPE + " TEXT, " +PET_COLUMN_SUBTYPE +" TEXT NULL, " + PET_COLUMN_CHIP +" TEXT, " +
            PET_COLUMN_IMGPATH +" INTEGER NULL);";


    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + PET_TABLE_NAME;

    public PetDBController (Context context){
        super(context, DATABASE_NAME,null,1);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_PET_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(DELETE_TABLE);
        onCreate(db);
    }

    public boolean insertPet(String nom, String data, String type,String subtype, String chip, String imgpath){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PET_COLUMN_NAME, nom);
        values.put(PET_COLUMN_DATA,data);
        values.put(PET_COLUMN_TYPE, type);
        values.put(PET_COLUMN_SUBTYPE,subtype);
        values.put(PET_COLUMN_CHIP, chip);
        values.put(PET_COLUMN_IMGPATH, imgpath);
        long ret = db.insert(PET_TABLE_NAME,null,values);
        db.close();
        return ret != -1;
    }

    public ArrayList<Pet> queryAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT * FROM " + PET_TABLE_NAME;
        Cursor c = db.rawQuery(q ,null);
        ArrayList<Pet> petArrayList = new ArrayList<Pet>();
        if(c.moveToFirst()){
            do {
                Pet pet = new Pet();
                pet.setName(c.getString(c.getColumnIndex(PET_COLUMN_NAME)));
                pet.setBornDate(c.getString(c.getColumnIndex(PET_COLUMN_DATA)));
                pet.setPetType(c.getString(c.getColumnIndex(PET_COLUMN_TYPE)));
                pet.setPetSubtype(c.getString(c.getColumnIndex(PET_COLUMN_SUBTYPE)));
                pet.setChipNumber(c.getString(c.getColumnIndex(PET_COLUMN_CHIP)));
                pet.setPetPhotoPath(Uri.parse(c.getString(c.getColumnIndex(PET_COLUMN_IMGPATH)))); //String path -> Uri path
                petArrayList.add(pet);
                Log.d("queyall", pet.getName() + pet.getBornDate() + pet.getPetType());
            } while(c.moveToNext());
        }
        db.close();
        c.close();
        Log.d("Queryall", "after close");
        return petArrayList;
    }

    public Pet queryPet(String xip){
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT * FROM " + PET_TABLE_NAME + " WHERE " + PET_COLUMN_CHIP + " = " + xip;
        Cursor c = db.rawQuery(q,null);
        Pet pet = new Pet();
        if(c.moveToFirst()) {
            pet.setName(c.getString(c.getColumnIndex(PET_COLUMN_NAME)));
            pet.setBornDate(c.getString(c.getColumnIndex(PET_COLUMN_DATA)));
            pet.setPetType(c.getString(c.getColumnIndex(PET_COLUMN_TYPE)));
            pet.setPetSubtype(c.getString(c.getColumnIndex(PET_COLUMN_SUBTYPE)));
            pet.setChipNumber(c.getString(c.getColumnIndex(PET_COLUMN_CHIP)));
            pet.setPetPhotoPath(Uri.parse(c.getString(c.getColumnIndex(PET_COLUMN_IMGPATH))));
        }
        db.close();
        c.close();
        return pet;
    }
}
