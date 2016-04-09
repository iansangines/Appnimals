package com.example.iansangines.appnimals;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by iansangines on 06/04/2016.
 */
public class PetDBController extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "AppnimalsDB";
    private static final String PET_TABLE_NAME = "pets";
    private static final String PET_COLUMN_ID = "id";
    private static final String PET_COLUMN_NAME = "name";
    private static final String PET_COLUMN_TYPE = "type";
    private static final String PET_COLUMN_CHIP= "chip";
    private static final String CREATE_PET_TABLE = "CREATE TABLE " + PET_TABLE_NAME + " (" +
            PET_COLUMN_ID +  "INTEGER PRIMARY KEY AUTOINCREMENT," + PET_COLUMN_NAME + " TEXT," +
            PET_COLUMN_TYPE + " TEXT," + PET_COLUMN_CHIP +" TEXT);";
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

    public boolean insertPet(String nom, String type, String chip){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PET_COLUMN_NAME, nom);
        values.put(PET_COLUMN_TYPE, type);
        values.put(PET_COLUMN_CHIP, chip);
        long ret = db.insert(PET_TABLE_NAME,null,values);
        if(ret != -1) return true;
        else return false;
    }

    public void queryAll(Cursor pet){
        SQLiteDatabase db = this.getWritableDatabase();
        String q = "SELECT * FROM " + PET_TABLE_NAME;
        pet = db.rawQuery(q ,null);
    }

}
