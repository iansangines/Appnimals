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


import java.util.Random;

public class PetListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);
        Button petButton = (Button) findViewById(R.id.buttonew);
        ListView petList = (ListView) findViewById(R.id.listview);
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
        String[] values = new String[]{"Query", "els noms", "de la bd"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        assert petList != null;
        petList.setAdapter(arrayAdapter);

        petList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                String value = (String) adapter.getItemAtPosition(position);
                Toast.makeText(PetListActivity.this, "fila on click numero " + value , Toast.LENGTH_SHORT).show();
            }
        });

    }

};
