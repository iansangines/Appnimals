package com.example.iansangines.appnimals;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Random;

public class PetListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            toolbar.setLogo(R.drawable.appnimals_inside);
            toolbar.setTitle("Appnimals");

        }
        //dades del parentactivity
        Intent in = getIntent();
        String rand = in.getStringExtra("Random");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "no va", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
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
        petListView.setAdapter(adapter);

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
