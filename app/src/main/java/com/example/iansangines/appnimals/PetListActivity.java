package com.example.iansangines.appnimals;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Random;

import static android.app.PendingIntent.getActivity;
import static java.security.AccessController.getContext;

public class PetListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Les teves mascotes");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    AlertDialog.Builder newPetDialog = new AlertDialog.Builder(PetListActivity.this);
                    newPetDialog.setTitle("Nova mascota").setMessage("Vols afegir una nova mascota?");
                            newPetDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent insertPet = new Intent(PetListActivity.this, InsertPetActivity.class);
                                    startActivity(insertPet);
                                }
                            });
                    newPetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    newPetDialog.show();
                }
            });
        }


        ListView petListView = (ListView) findViewById(R.id.listview);

        //BASE DE DADES
        PetDBController dbController = new PetDBController(this);
        dbController.insertPet("Marta", "ahir", "gat", "pataner", "xxx",0);
        Pet returned = dbController.queryAll();
        Log.d("activity", "cursor returned");
        //

        ArrayList<Pet> petList = new ArrayList<Pet>();
        petList.add(returned);
        ListAdapter adapter = new ListAdapter(getApplicationContext(), R.layout.listed_pet, petList);
        assert petListView != null;
        petListView.setAdapter(adapter);

        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //PERFILACTIVITY
            }
        });



    }
}
