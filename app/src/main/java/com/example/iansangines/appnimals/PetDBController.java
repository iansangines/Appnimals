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
    private static final String PET_COLUMN_NAME = "NAME";
    private static final String PET_COLUMN_DATA = "DATA";
    private static final String PET_COLUMN_TYPE = "TYPE";
    private static final String PET_COLUMN_CHIP= "CHIP";
    private static final String PET_COLUMN_IMGPATH= "IMGPATH";
    private static final String PET_COLUMN_THUMBNAILPATH= "THUMBNAILPATH";

    private static final String CREATE_PET_TABLE = "CREATE TABLE " + PET_TABLE_NAME +
            " (" + PET_COLUMN_NAME + " TEXT, "
            + PET_COLUMN_DATA + " TEXT, "
            + PET_COLUMN_TYPE + " TEXT, "
            + PET_COLUMN_CHIP +" TEXT PRIMARY KEY, "
            + PET_COLUMN_IMGPATH +" TEXT, "
            + PET_COLUMN_THUMBNAILPATH + " TEXT);";

    private static final String EVENT_TABLE_NAME = "EVENTS";
    private static final String EVENT_COLUMN_NAME = "NAME";
    private static final String EVENT_COLUMN_DAY = "DAY";
    private static final String EVENT_COLUMN_MONTH = "MONTH";
    private static final String EVENT_COLUMN_YEAR = "YEAR";
    private static final String EVENT_COLUMN_TYPE = "TYPE"; // vacunacio|desparasitacio|veterinari
    private static final String EVENT_COLUMN_HOUR= "HOUR";
    private static final String EVENT_COLUMN_LOC= "UBIC";
    private static final String EVENT_COLUMN_DESC= "DESC";

    private static final String EVENT_COLUMN_PETCHIP = "PETCHIP";

    private static final String CREATE_EVENT_TABLE = "CREATE TABLE " + EVENT_TABLE_NAME
            + " (" + EVENT_COLUMN_NAME + " TEXT, "
            + EVENT_COLUMN_DAY + " TEXT, "
            + EVENT_COLUMN_MONTH + " TEXT, "
            + EVENT_COLUMN_YEAR + " TEXT, "
            + EVENT_COLUMN_TYPE + " TEXT, "
            + EVENT_COLUMN_HOUR +" TEXT, "
            + EVENT_COLUMN_PETCHIP+" TEXT, "
            + EVENT_COLUMN_LOC + "TEXT, "
            + EVENT_COLUMN_DESC + "TEXT, "
            + " PRIMARY KEY (" + EVENT_COLUMN_DAY + ", " + EVENT_COLUMN_MONTH + ", " + EVENT_COLUMN_YEAR + ", " + EVENT_COLUMN_HOUR + ")"
            + " FOREIGN KEY (" + EVENT_COLUMN_PETCHIP + ") REFERENCES " + PET_TABLE_NAME + "(" + PET_COLUMN_CHIP + ") );";

    private static final String DELETE_PET_TABLE = "DROP TABLE IF EXISTS " + PET_TABLE_NAME;
    private static final String DELETE_EVENT_TABLE = "DROP TABLE IF EXISTS " + EVENT_TABLE_NAME;

    public PetDBController (Context context){
        super(context, DATABASE_NAME,null,1);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_PET_TABLE);
        db.execSQL(CREATE_EVENT_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(DELETE_PET_TABLE);
        db.execSQL(DELETE_PET_TABLE);
        onCreate(db);
    }

    public boolean insertPet(String nom, String data, String type, String chip, String imgpath, String thumbnailpath){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PET_COLUMN_NAME, nom);
        values.put(PET_COLUMN_DATA,data);
        values.put(PET_COLUMN_TYPE, type);
        values.put(PET_COLUMN_CHIP, chip);
        values.put(PET_COLUMN_IMGPATH, imgpath);
        values.put(PET_COLUMN_THUMBNAILPATH, thumbnailpath);
        long ret = db.insert(PET_TABLE_NAME,null,values);
        db.close();
        return ret != -1;
    }

    public boolean insertEvent(String nom, String day, String month , String year ,String type, String hour, String petChip,String loc, String desc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EVENT_COLUMN_NAME, nom);
        values.put(EVENT_COLUMN_DAY,day);
        values.put(EVENT_COLUMN_MONTH,month);
        values.put(EVENT_COLUMN_YEAR,year);
        values.put(EVENT_COLUMN_TYPE, type);
        values.put(EVENT_COLUMN_HOUR, hour);
        values.put(EVENT_COLUMN_PETCHIP, petChip);
        values.put(EVENT_COLUMN_LOC, loc);
        values.put(EVENT_COLUMN_DESC, desc);
        long ret = db.insert(EVENT_TABLE_NAME,null,values);
        db.close();
        return ret != -1;
    }

    public ArrayList<Pet> queryAllPets(){
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
                pet.setChipNumber(c.getString(c.getColumnIndex(PET_COLUMN_CHIP)));
                pet.setPetPhotoPath(c.getString(c.getColumnIndex(PET_COLUMN_IMGPATH)));
                pet.setPetthumbnailPath(c.getString(c.getColumnIndex(PET_COLUMN_THUMBNAILPATH)));
                petArrayList.add(pet);
                Log.d("queyall", pet.getName() + pet.getBornDate() + pet.getPetType());
            } while(c.moveToNext());
        }
        db.close();
        c.close();
        Log.d("Queryall", "after close");
        return petArrayList;
    }

    public ArrayList<Event> queryAllEvents(){
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT * FROM " + EVENT_TABLE_NAME;
        Cursor c = db.rawQuery(q ,null);
        ArrayList<Event> eventArrayList = new ArrayList<Event>();
        if(c.moveToFirst()){
            do {
                Event event = new Event();
                event.setName(c.getString(c.getColumnIndex(EVENT_COLUMN_NAME)));
                event.setDay(c.getString(c.getColumnIndex(EVENT_COLUMN_DAY)));
                event.setMonth(c.getString(c.getColumnIndex(EVENT_COLUMN_MONTH)));
                event.setYear(c.getString(c.getColumnIndex(EVENT_COLUMN_YEAR)));
                event.setEventType(c.getString(c.getColumnIndex(EVENT_COLUMN_TYPE)));
                event.setHour(c.getString(c.getColumnIndex(EVENT_COLUMN_HOUR)));
                event.setPetChip(c.getString(c.getColumnIndex(EVENT_COLUMN_PETCHIP)));
                event.setEventLocation(c.getString(c.getColumnIndex(EVENT_COLUMN_LOC)));
                event.setEventDescription(c.getString(c.getColumnIndex(EVENT_COLUMN_DESC)));
                eventArrayList.add(event);
                Log.d("queyall", event.getName() + event.getDay() + event.getPetChip());
            } while(c.moveToNext());
        }
        db.close();
        c.close();
        Log.d("Queryall", "after close");
        return eventArrayList;
    }

    public Pet queryPet(String xip){
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT * FROM " + PET_TABLE_NAME + " WHERE " + PET_COLUMN_CHIP + " = " + xip;
        Cursor c = db.rawQuery("SELECT * FROM " + PET_TABLE_NAME + " WHERE " + PET_COLUMN_CHIP + "=" + xip, null);
        Pet pet = new Pet();
        if(c.moveToFirst()) {
            Log.d("queryPet", "l'ha trobat");
            pet.setName(c.getString(c.getColumnIndex(PET_COLUMN_NAME)));
            pet.setBornDate(c.getString(c.getColumnIndex(PET_COLUMN_DATA)));
            pet.setPetType(c.getString(c.getColumnIndex(PET_COLUMN_TYPE)));
            pet.setChipNumber(c.getString(c.getColumnIndex(PET_COLUMN_CHIP)));
            pet.setPetPhotoPath(c.getString(c.getColumnIndex(PET_COLUMN_IMGPATH)));
            pet.setPetthumbnailPath(c.getString(c.getColumnIndex(PET_COLUMN_THUMBNAILPATH)));
        }
        db.close();
        c.close();
        Log.d("queryPet",pet.getPhotoPath());
        return pet;
    }
}
