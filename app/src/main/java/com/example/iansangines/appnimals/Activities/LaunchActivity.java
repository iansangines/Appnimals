package com.example.iansangines.appnimals.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import com.example.iansangines.appnimals.Controllers.ImageFileController;
import com.example.iansangines.appnimals.Controllers.PetDBController;

import java.io.File;


public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                Intent petlist = new Intent(LaunchActivity.this, PetListActivity.class);
                startActivity(petlist);
            }
        }.start();
    }
}
