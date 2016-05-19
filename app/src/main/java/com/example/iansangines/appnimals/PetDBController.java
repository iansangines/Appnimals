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
public class PetDBController extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "AppnimalsDB";
    private static final String COLUMN_ID = "ID";
    private static final String PET_TABLE_NAME = "PETS";
    private static final String PET_COLUMN_NAME = "NAME";
    private static final String PET_COLUMN_DATA = "DATA";
    private static final String PET_COLUMN_TYPE = "TYPE";
    private static final String PET_COLUMN_CHIP = "CHIP";
    private static final String PET_COLUMN_IMGPATH = "IMGPATH";
    private static final String PET_COLUMN_THUMBNAILPATH = "THUMBNAILPATH";

    private static final String CREATE_PET_TABLE = "CREATE TABLE " + PET_TABLE_NAME +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PET_COLUMN_NAME + " TEXT, "
            + PET_COLUMN_DATA + " TEXT, "
            + PET_COLUMN_TYPE + " TEXT, "
            + PET_COLUMN_CHIP + " TEXT, "
            + PET_COLUMN_IMGPATH + " TEXT, "
            + PET_COLUMN_THUMBNAILPATH + " TEXT );";

    private static final String EVENT_TABLE_NAME = "EVENTS";
    private static final String EVENT_COLUMN_NAME = "NAME";
    private static final String EVENT_COLUMN_DAY = "DAY";
    private static final String EVENT_COLUMN_MONTH = "MONTH";
    private static final String EVENT_COLUMN_YEAR = "YEAR";
    private static final String EVENT_COLUMN_TYPE = "TYPE"; // vacunacio|desparasitacio|veterinari
    private static final String EVENT_COLUMN_HOUR = "HOUR";
    private static final String EVENT_COLUMN_MINUTE = "MINUTE";
    private static final String EVENT_COLUMN_LOC = "UBIC";
    private static final String EVENT_COLUMN_DESC = "DESC";
    private static final String EVENT_COLUMN_PETNAME = "TYPE"; // vacunacio|desparasitacio|veterinari
    private static final String EVENT_COLUMN_PETCHIP = "PETCHIP";
    private static final String EVENT_COLUMN_PETID = "PETID";

    private static final String CREATE_EVENT_TABLE = "CREATE TABLE " + EVENT_TABLE_NAME
            + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + EVENT_COLUMN_NAME + " TEXT, "
            + EVENT_COLUMN_DAY + " TEXT, "
            + EVENT_COLUMN_MONTH + " TEXT, "
            + EVENT_COLUMN_YEAR + " TEXT, "
            + EVENT_COLUMN_TYPE + " TEXT, "
            + EVENT_COLUMN_HOUR + " TEXT, "
            + EVENT_COLUMN_MINUTE + " TEXT, "
            + EVENT_COLUMN_PETCHIP + " TEXT, "
            + EVENT_COLUMN_PETID + " INTEGER, "
            + EVENT_COLUMN_LOC + " TEXT, "
            + EVENT_COLUMN_DESC + " TEXT, "
            + " FOREIGN KEY (" + EVENT_COLUMN_PETCHIP + ") REFERENCES " + PET_TABLE_NAME + "(" + PET_COLUMN_CHIP + ") "
            + " FOREIGN KEY (" + EVENT_COLUMN_PETID + ") REFERENCES " + PET_TABLE_NAME + "(" + COLUMN_ID + ") "
            + "FOREIGN KEY (" + EVENT_COLUMN_PETNAME + ") REFERENCES " + PET_TABLE_NAME + "(" + PET_COLUMN_NAME + " ) );";

    private static final String DELETE_PET_TABLE = "DROP TABLE IF EXISTS " + PET_TABLE_NAME;
    private static final String DELETE_EVENT_TABLE = "DROP TABLE IF EXISTS " + EVENT_TABLE_NAME;

    public PetDBController(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PET_TABLE);
        db.execSQL(CREATE_EVENT_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_PET_TABLE);
        db.execSQL(DELETE_PET_TABLE);
        onCreate(db);
    }

    public int insertPet(String nom, String data, String type, String chip, String imgpath, String thumbnailpath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PET_COLUMN_NAME, nom);
        values.put(PET_COLUMN_DATA, data);
        values.put(PET_COLUMN_TYPE, type);
        values.put(PET_COLUMN_CHIP, chip);
        values.put(PET_COLUMN_IMGPATH, imgpath);
        values.put(PET_COLUMN_THUMBNAILPATH, thumbnailpath);
        long ret = db.insert(PET_TABLE_NAME, null, values);
        db.close();
        return getPetId(chip);
    }

    private int getPetId(String chip) {
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT " + COLUMN_ID + " FROM " + PET_TABLE_NAME + " WHERE " + PET_COLUMN_CHIP + "=" + chip;
        Cursor c = db.rawQuery(q, null);
        int id = -1;
        if (c.moveToFirst()) {
            id = c.getInt(c.getColumnIndex(COLUMN_ID));
        }
        return id;
    }

    public boolean insertEvent(Event e) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EVENT_COLUMN_NAME, e.getName());
        values.put(EVENT_COLUMN_DAY, e.getDay());
        values.put(EVENT_COLUMN_MONTH, e.getMonth());
        values.put(EVENT_COLUMN_YEAR, e.getYear());
        values.put(EVENT_COLUMN_TYPE, e.getEventType());
        values.put(EVENT_COLUMN_HOUR, e.getHour());
        values.put(EVENT_COLUMN_MINUTE, e.getMinute());
        values.put(EVENT_COLUMN_PETNAME, e.getPetName());
        values.put(EVENT_COLUMN_PETCHIP, e.getPetChip());
        values.put(EVENT_COLUMN_PETID, e.getPetId());
        values.put(EVENT_COLUMN_LOC, e.getEventLocation());
        values.put(EVENT_COLUMN_DESC, e.getEventDescription());
        long ret = db.insert(EVENT_TABLE_NAME, null, values);
        db.close();
        return ret != -1;
    }

    public ArrayList<Pet> queryAllPets() {
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT * FROM " + PET_TABLE_NAME;
        Cursor c = db.rawQuery(q, null);
        ArrayList<Pet> petArrayList = new ArrayList<Pet>();
        if (c.moveToFirst()) {
            do {
                Pet pet = new Pet();
                pet.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
                pet.setName(c.getString(c.getColumnIndex(PET_COLUMN_NAME)));
                pet.setBornDate(c.getString(c.getColumnIndex(PET_COLUMN_DATA)));
                pet.setPetType(c.getString(c.getColumnIndex(PET_COLUMN_TYPE)));
                pet.setChipNumber(c.getString(c.getColumnIndex(PET_COLUMN_CHIP)));
                pet.setPetPhotoPath(c.getString(c.getColumnIndex(PET_COLUMN_IMGPATH)));
                pet.setPetthumbnailPath(c.getString(c.getColumnIndex(PET_COLUMN_THUMBNAILPATH)));
                petArrayList.add(pet);
            } while (c.moveToNext());
        }
        db.close();
        c.close();
        return petArrayList;
    }

    public ArrayList<Event> queryAllEvents() {
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT * FROM " + EVENT_TABLE_NAME + " ORDER BY " + EVENT_COLUMN_YEAR + ", " + EVENT_COLUMN_MONTH + ", " + EVENT_COLUMN_DAY + " ," + EVENT_COLUMN_HOUR + ", " + EVENT_COLUMN_MINUTE + ";";
        Cursor c = db.rawQuery(q, null);
        ArrayList<Event> eventArrayList = new ArrayList<Event>();
        if (c.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
                event.setName(c.getString(c.getColumnIndex(EVENT_COLUMN_NAME)));
                event.setDay(c.getString(c.getColumnIndex(EVENT_COLUMN_DAY)));
                event.setMonth(c.getString(c.getColumnIndex(EVENT_COLUMN_MONTH)));
                event.setYear(c.getString(c.getColumnIndex(EVENT_COLUMN_YEAR)));
                event.setEventType(c.getString(c.getColumnIndex(EVENT_COLUMN_TYPE)));
                event.setHour(c.getString(c.getColumnIndex(EVENT_COLUMN_HOUR)));
                event.setMinute(c.getString(c.getColumnIndex(EVENT_COLUMN_MINUTE)));
                event.setPetName(c.getString(c.getColumnIndex(EVENT_COLUMN_PETNAME)));
                event.setPetChip(c.getString(c.getColumnIndex(EVENT_COLUMN_PETCHIP)));
                event.setPetId(c.getInt(c.getColumnIndex(EVENT_COLUMN_PETID)));
                Log.d("event petid", Integer.toString(event.getId()));
                event.setEventLocation(c.getString(c.getColumnIndex(EVENT_COLUMN_LOC)));
                event.setEventDescription(c.getString(c.getColumnIndex(EVENT_COLUMN_DESC)));
                eventArrayList.add(event);
                Log.d("queyall", event.getName() + event.getDay() + event.getMonth() + event.getHour());
            } while (c.moveToNext());
        }
        db.close();
        c.close();
        Log.d("Queryall", "after close");
        return eventArrayList;
    }

    public Pet queryPetById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + PET_TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id, null);
        Pet pet = new Pet();
        if (c.moveToFirst()) {
            Log.d("queryPet", "l'ha trobat");
            pet.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
            pet.setName(c.getString(c.getColumnIndex(PET_COLUMN_NAME)));
            pet.setBornDate(c.getString(c.getColumnIndex(PET_COLUMN_DATA)));
            pet.setPetType(c.getString(c.getColumnIndex(PET_COLUMN_TYPE)));
            pet.setChipNumber(c.getString(c.getColumnIndex(PET_COLUMN_CHIP)));
            pet.setPetPhotoPath(c.getString(c.getColumnIndex(PET_COLUMN_IMGPATH)));
            pet.setPetthumbnailPath(c.getString(c.getColumnIndex(PET_COLUMN_THUMBNAILPATH)));
        }
        db.close();
        c.close();
        Log.d("queryPet", pet.getPhotoPath());
        return pet;
    }

    public Pet queryPetByChip(String chip) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + PET_TABLE_NAME + " WHERE " + PET_COLUMN_CHIP + "=" + chip, null);
        Pet pet = new Pet();
        if (c.moveToFirst()) {
            Log.d("queryPet", "l'ha trobat");
            pet.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
            pet.setName(c.getString(c.getColumnIndex(PET_COLUMN_NAME)));
            pet.setBornDate(c.getString(c.getColumnIndex(PET_COLUMN_DATA)));
            pet.setPetType(c.getString(c.getColumnIndex(PET_COLUMN_TYPE)));
            pet.setChipNumber(c.getString(c.getColumnIndex(PET_COLUMN_CHIP)));
            pet.setPetPhotoPath(c.getString(c.getColumnIndex(PET_COLUMN_IMGPATH)));
            pet.setPetthumbnailPath(c.getString(c.getColumnIndex(PET_COLUMN_THUMBNAILPATH)));
        }
        db.close();
        c.close();
        Log.d("queryPet", pet.getPhotoPath());
        return pet;
    }

    public ArrayList<Event> queryPetEvents(int petId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT * FROM " + EVENT_TABLE_NAME + " WHERE " + EVENT_COLUMN_PETID + "=" + petId + " ORDER BY " + EVENT_COLUMN_YEAR + ", " + EVENT_COLUMN_MONTH + ", " + EVENT_COLUMN_DAY + " ," + EVENT_COLUMN_HOUR + ", " + EVENT_COLUMN_MINUTE + ";";
        Cursor c = db.rawQuery(q, null);
        ArrayList<Event> eventArrayList = new ArrayList<Event>();
        if (c.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
                event.setName(c.getString(c.getColumnIndex(EVENT_COLUMN_NAME)));
                event.setDay(c.getString(c.getColumnIndex(EVENT_COLUMN_DAY)));
                event.setMonth(c.getString(c.getColumnIndex(EVENT_COLUMN_MONTH)));
                event.setYear(c.getString(c.getColumnIndex(EVENT_COLUMN_YEAR)));
                event.setEventType(c.getString(c.getColumnIndex(EVENT_COLUMN_TYPE)));
                event.setHour(c.getString(c.getColumnIndex(EVENT_COLUMN_HOUR)));
                event.setMinute(c.getString(c.getColumnIndex(EVENT_COLUMN_MINUTE)));
                event.setPetName(c.getString(c.getColumnIndex(EVENT_COLUMN_PETNAME)));
                event.setPetChip(c.getString(c.getColumnIndex(EVENT_COLUMN_PETCHIP)));
                event.setPetId(c.getInt(c.getColumnIndex(EVENT_COLUMN_PETID)));
                event.setEventLocation(c.getString(c.getColumnIndex(EVENT_COLUMN_LOC)));
                event.setEventDescription(c.getString(c.getColumnIndex(EVENT_COLUMN_DESC)));
                eventArrayList.add(event);
                Log.d("queyall", event.getName() + event.getDay() + event.getMonth() + event.getHour());
            } while (c.moveToNext());
        }
        Log.d("petEvent size", Integer.toString(eventArrayList.size()));
        db.close();
        c.close();
        Log.d("Queryall", "after close");
        return eventArrayList;
    }

    public boolean updatePet(Pet pet){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PET_COLUMN_NAME, pet.getName());
        values.put(PET_COLUMN_DATA, pet.getBornDate());
        values.put(PET_COLUMN_TYPE, pet.getPetType());
        values.put(PET_COLUMN_CHIP ,pet.getChipNumber());
        values.put(PET_COLUMN_IMGPATH , pet.getPhotoPath());
        values.put( PET_COLUMN_THUMBNAILPATH, pet.getthumbnailPath());
        return db.update(PET_TABLE_NAME,values,  COLUMN_ID + "=" + pet.getId(),null) > 0;
    }

    public boolean deletePet(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PET_TABLE_NAME, COLUMN_ID + "=" + id, null) > 0;
    }

    public boolean deletePetEvents(String chip) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(EVENT_TABLE_NAME, EVENT_COLUMN_PETCHIP + "=" + chip, null) > 0;
    }

    public boolean deleteEvent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(EVENT_TABLE_NAME, COLUMN_ID + "=" + id, null) > 0;
    }
}
