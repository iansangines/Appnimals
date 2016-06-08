package com.example.iansangines.appnimals.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.iansangines.appnimals.Activities.R;
import com.example.iansangines.appnimals.Domain.Event;
import com.example.iansangines.appnimals.Domain.Pet;


import java.io.File;
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
    private static final String PET_COLUMN_ESPECIAL = "ESPECIAL";

    private static final String CREATE_PET_TABLE = "CREATE TABLE " + PET_TABLE_NAME +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PET_COLUMN_NAME + " TEXT, "
            + PET_COLUMN_DATA + " TEXT, "
            + PET_COLUMN_TYPE + " TEXT, "
            + PET_COLUMN_CHIP + " TEXT, "
            + PET_COLUMN_IMGPATH + " TEXT, "
            + PET_COLUMN_THUMBNAILPATH + " TEXT, "
            + PET_COLUMN_ESPECIAL + " TEXT );";

    private static final String EVENT_TABLE_NAME = "EVENTS";
    private static final String EVENT_COLUMN_NAME = "NAME";
    private static final String EVENT_COLUMN_DAY = "DAY";
    private static final String EVENT_COLUMN_MONTH = "MONTH";
    private static final String EVENT_COLUMN_YEAR = "YEAR";
    private static final String EVENT_COLUMN_TYPE = "TYPE"; // vacunacio|desparasitacio|veterinari|altre
    private static final String EVENT_COLUMN_HOUR = "HOUR";
    private static final String EVENT_COLUMN_MINUTE = "MINUTE";
    private static final String EVENT_COLUMN_LOC = "UBIC";
    private static final String EVENT_COLUMN_PETNAME = "PETNAME";
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
            + EVENT_COLUMN_PETNAME + " TEXT, "
            + EVENT_COLUMN_PETCHIP + " TEXT, "
            + EVENT_COLUMN_PETID + " INTEGER, "
            + EVENT_COLUMN_LOC + " TEXT, "
            + " FOREIGN KEY (" + EVENT_COLUMN_PETCHIP + ") REFERENCES " + PET_TABLE_NAME + "(" + PET_COLUMN_CHIP + "), "
            + " FOREIGN KEY (" + EVENT_COLUMN_PETID + ") REFERENCES " + PET_TABLE_NAME + "(" + COLUMN_ID + "), "
            + "FOREIGN KEY (" + EVENT_COLUMN_PETNAME + ") REFERENCES " + PET_TABLE_NAME + "(" + PET_COLUMN_NAME + " ) );";

    private static final String DELETE_PET_TABLE = "DROP TABLE IF EXISTS " + PET_TABLE_NAME;
    private static final String DELETE_EVENT_TABLE = "DROP TABLE IF EXISTS " + EVENT_TABLE_NAME;

    private Context context;
    static boolean inserted;

    public PetDBController(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    public boolean getInserted() {return inserted;}

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PET_TABLE);
        Log.d("conCreat", "holi");
        db.execSQL(CREATE_EVENT_TABLE);

        ImageFileController ifc = new ImageFileController();
        ifc.CreateDirectories();
        Bitmap img = BitmapFactory.decodeResource(context.getResources(), R.drawable.inu);
        File fullImage1 = ifc.getFullSizeFile();
        ifc.saveFullSizeImage(img, fullImage1);
        File thumbnailFile1 = ifc.getThumbnailFile();
        ifc.saveThumbnailImage(img, thumbnailFile1);

        ContentValues values = new ContentValues();
        values.put(PET_COLUMN_NAME, "Inu");
        values.put(PET_COLUMN_DATA, "14/11/2010");
        values.put(PET_COLUMN_TYPE, "Gos");
        values.put(PET_COLUMN_CHIP, "977000123456789");
        values.put(PET_COLUMN_IMGPATH, fullImage1.getAbsolutePath());
        values.put(PET_COLUMN_THUMBNAILPATH, thumbnailFile1.getAbsolutePath());
        values.put(PET_COLUMN_ESPECIAL, "Necessita calci");
        long ret = db.insert(PET_TABLE_NAME, null, values);

        img = BitmapFactory.decodeResource(context.getResources(), R.drawable.jimi);
        File fullImage2 = ifc.getFullSizeFile();
        ifc.saveFullSizeImage(img, fullImage2);
        File thumbnailFile2 = ifc.getThumbnailFile();
        ifc.saveThumbnailImage(img, thumbnailFile2);

        values = new ContentValues();
        values.put(PET_COLUMN_NAME, "Jimi");
        values.put(PET_COLUMN_DATA,  "6/4/2015");
        values.put(PET_COLUMN_TYPE, "Conill");
        values.put(PET_COLUMN_CHIP, "973529155362754");
        values.put(PET_COLUMN_IMGPATH, fullImage2.getAbsolutePath());
        values.put(PET_COLUMN_THUMBNAILPATH, thumbnailFile2.getAbsolutePath());
        ret = db.insert(PET_TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(EVENT_COLUMN_NAME, "Revisió trimestral");
        values.put(EVENT_COLUMN_DAY, "4");
        values.put(EVENT_COLUMN_MONTH, "7");
        values.put(EVENT_COLUMN_YEAR, "2016");
        values.put(EVENT_COLUMN_TYPE, "Veterinari");
        values.put(EVENT_COLUMN_HOUR, "11");
        values.put(EVENT_COLUMN_MINUTE, "30");
        values.put(EVENT_COLUMN_PETNAME, "Inu");
        values.put(EVENT_COLUMN_PETCHIP, "977000123456789");
        values.put(EVENT_COLUMN_PETID, 1);
        values.put(EVENT_COLUMN_LOC, "Granollers");
        ret = db.insert(EVENT_TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(EVENT_COLUMN_NAME, "Concurs bellesa");
        values.put(EVENT_COLUMN_DAY, "17");
        values.put(EVENT_COLUMN_MONTH, "8");
        values.put(EVENT_COLUMN_YEAR, "2016");
        values.put(EVENT_COLUMN_TYPE, "Concurs");
        values.put(EVENT_COLUMN_HOUR, "18");
        values.put(EVENT_COLUMN_MINUTE, "00");
        values.put(EVENT_COLUMN_PETNAME, "Jimi");
        values.put(EVENT_COLUMN_PETCHIP, "973529155362754");
        values.put(EVENT_COLUMN_PETID, 2);
        values.put(EVENT_COLUMN_LOC, "Mataró");
        ret = db.insert(EVENT_TABLE_NAME, null, values);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_PET_TABLE);
        db.execSQL(DELETE_EVENT_TABLE);
        onCreate(db);
    }

    public int insertPet(String nom, String data, String type, String chip, String imgpath, String thumbnailpath, String especials) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PET_COLUMN_NAME, nom);
        values.put(PET_COLUMN_DATA, data);
        values.put(PET_COLUMN_TYPE, type);
        values.put(PET_COLUMN_CHIP, chip);
        values.put(PET_COLUMN_IMGPATH, imgpath);
        values.put(PET_COLUMN_THUMBNAILPATH, thumbnailpath);
        values.put(PET_COLUMN_ESPECIAL, especials);
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
                pet.setEspecial(c.getString(c.getColumnIndex(PET_COLUMN_ESPECIAL)));
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
                event.setEventLocation(c.getString(c.getColumnIndex(EVENT_COLUMN_LOC)));
                eventArrayList.add(event);
            } while (c.moveToNext());
        }
        db.close();
        c.close();
        return eventArrayList;
    }

    public Pet queryPetById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + PET_TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id, null);
        Pet pet = new Pet();
        if (c.moveToFirst()) {
            pet.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
            pet.setName(c.getString(c.getColumnIndex(PET_COLUMN_NAME)));
            pet.setBornDate(c.getString(c.getColumnIndex(PET_COLUMN_DATA)));
            pet.setPetType(c.getString(c.getColumnIndex(PET_COLUMN_TYPE)));
            pet.setChipNumber(c.getString(c.getColumnIndex(PET_COLUMN_CHIP)));
            pet.setPetPhotoPath(c.getString(c.getColumnIndex(PET_COLUMN_IMGPATH)));
            pet.setPetthumbnailPath(c.getString(c.getColumnIndex(PET_COLUMN_THUMBNAILPATH)));
            pet.setEspecial(c.getString(c.getColumnIndex(PET_COLUMN_ESPECIAL)));
        }
        db.close();
        c.close();
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
                eventArrayList.add(event);
            } while (c.moveToNext());
        }
        db.close();
        c.close();
        return eventArrayList;
    }

    public Event queryEvent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + EVENT_TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id, null);
        Event e = new Event();
        if (c.moveToFirst()) {
            e.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
            e.setName(c.getString(c.getColumnIndex(EVENT_COLUMN_NAME)));
            e.setEventType(c.getString(c.getColumnIndex(EVENT_COLUMN_TYPE)));
            e.setDay(c.getString(c.getColumnIndex(EVENT_COLUMN_DAY)));
            e.setMonth(c.getString(c.getColumnIndex(EVENT_COLUMN_MONTH)));
            e.setYear(c.getString(c.getColumnIndex(EVENT_COLUMN_YEAR)));
            e.setHour(c.getString(c.getColumnIndex(EVENT_COLUMN_HOUR)));
            e.setMinute(c.getString(c.getColumnIndex(EVENT_COLUMN_MINUTE)));
            e.setEventLocation(c.getString(c.getColumnIndex(EVENT_COLUMN_LOC)));
            e.setPetChip(c.getString(c.getColumnIndex(EVENT_COLUMN_PETCHIP)));
            e.setPetName(c.getString(c.getColumnIndex(EVENT_COLUMN_PETNAME)));
            e.setPetId(c.getInt(c.getColumnIndex(EVENT_COLUMN_PETID)));
        }
        db.close();
        c.close();
        return e;
    }

    public boolean updatePet(Pet pet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PET_COLUMN_NAME, pet.getName());
        values.put(PET_COLUMN_DATA, pet.getBornDate());
        values.put(PET_COLUMN_TYPE, pet.getPetType());
        values.put(PET_COLUMN_CHIP, pet.getChipNumber());
        values.put(PET_COLUMN_IMGPATH, pet.getPhotoPath());
        values.put(PET_COLUMN_THUMBNAILPATH, pet.getthumbnailPath());
        values.put(PET_COLUMN_ESPECIAL, pet.getEspecial());
        return db.update(PET_TABLE_NAME, values, COLUMN_ID + "=" + pet.getId(), null) > 0;
    }

    public boolean updateEvent(Event e) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EVENT_COLUMN_NAME, e.getName());
        values.put(EVENT_COLUMN_TYPE, e.getEventType());
        values.put(EVENT_COLUMN_DAY, e.getDay());
        values.put(EVENT_COLUMN_MONTH, e.getMonth());
        values.put(EVENT_COLUMN_YEAR, e.getYear());
        values.put(EVENT_COLUMN_HOUR, e.getHour());
        values.put(EVENT_COLUMN_MINUTE, e.getMinute());
        values.put(EVENT_COLUMN_LOC, e.getEventLocation());
        values.put(EVENT_COLUMN_PETNAME, e.getPetName());
        values.put(EVENT_COLUMN_PETCHIP, e.getPetChip());
        return db.update(EVENT_TABLE_NAME, values, COLUMN_ID + "=" + e.getId(), null) > 0;
    }

    public boolean deletePet(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PET_TABLE_NAME, COLUMN_ID + "=" + id, null) > 0;
    }

    public boolean deletePetEvents(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(EVENT_TABLE_NAME, EVENT_COLUMN_PETID + "=" + id, null) > 0;
    }

    public boolean deleteEvent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(EVENT_TABLE_NAME, COLUMN_ID + "=" + id, null) > 0;
    }
}
