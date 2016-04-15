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
    private ListAdapter adapter;
    private ListView petListView;
    private PetDBController dbController;
    static final int INSERT_PET_ACTIVITY = 0;
    static final int INSERTED = 1;



    @Override
    public void onCreate(Bundle savedInstanceState) {
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
                                    startActivityForResult(insertPet,INSERT_PET_ACTIVITY);
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


        petListView = (ListView) findViewById(R.id.listview);

        //BASE DE DADES
        dbController = new PetDBController(this);
        ArrayList<Pet> petList = dbController.queryAll();

        for(int i = 0; i < petList.size(); i++){
            Log.d("petList", petList.get(i).getName());
        }

        Log.d("activity", "cursor returned");

        adapter = new ListAdapter(getApplicationContext(), R.layout.listed_pet, petList);
        assert petListView != null;
        petListView.setAdapter(adapter);

        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //PERFILACTIVITY
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == INSERT_PET_ACTIVITY && resultCode == INSERTED){
            String xip = data.getStringExtra("xip");
            Pet pet = dbController.queryPet(xip);
            if(pet == null) Log.d("peeeeeeeeeeeeeeeeeeeeeeeeet","nuuuuuuuuuuuuuuuull");
            else {
                Log.d("peeeeeeeeeeeeeeeeeeet nom", pet.getName());
                adapter.add(pet);
                petListView.setAdapter(adapter);
                Toast.makeText(PetListActivity.this, "Mascota Guardada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onResume(){
        super.onResume();
    }
}
