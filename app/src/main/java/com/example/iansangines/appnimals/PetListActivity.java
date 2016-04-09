package com.example.iansangines.appnimals;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
        //dades del parentactivity
        Intent in = getIntent();
        String rand = in.getStringExtra("Random");

        AlertDialog.Builder insertQuestion = new AlertDialog.Builder(this.getApplicationContext());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                AlertDialog.Builder insertDialog = new AlertDialog.Builder(getApplicationContext());

                @Override
                public void onClick(View view) {
                    insertDialog.setTitle("Nova mascota").setMessage("Vols afegir una nova mascota?");
                    insertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent insertPet = new Intent(PetListActivity.this, InsertPetActivity.class);
                            startActivity(insertPet);
                        }
                    });
                    insertDialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                          dialog.cancel();
                        }
                    });
                   insertDialog.show();
                    Log.d("alertdialog", "abans del show");
                    //dialog.show();
                    Log.d("alertdialog", "dspres del show");
                }
            });
        }


        ListView petListView = (ListView) findViewById(R.id.listview);
        ArrayList<Pet> petList = new ArrayList<Pet>();
        Pet Marti = new Pet("Marti", "Insecte podrit", "29/11/1995", "33", R.drawable.appnimals_inside);
        petList.add(Marti);
        Pet Vidal = new Pet("Vidal", "gos pollós", "5/09/1995", "69", R.drawable.common_full_open_on_phone);
        petList.add(Vidal);
        ListAdapter adapter = new ListAdapter(getApplicationContext(), R.layout.listed_pet, petList);
        assert petListView != null;
        petListView.setAdapter(adapter);

        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //PERFILACTIVITY
            }
        });

        Button petButton = (Button) findViewById(R.id.buttonew);
        //start activity onclick
        assert petButton != null;
        petButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent insertactivity = new Intent(PetListActivity.this, InsertPetActivity.class);
                startActivity(insertactivity);
            }
        });

        //tractar de listview

    }
}
