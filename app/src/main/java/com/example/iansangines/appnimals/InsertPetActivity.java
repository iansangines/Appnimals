package com.example.iansangines.appnimals;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ExpandableListAdapter;
import android.widget.ListView;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


public class InsertPetActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_pet);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ExpandableListView petTypeList = (ExpandableListView) findViewById(R.id.type_expandiblelist);
        ArrayList<String> petTypes = new ArrayList<String>();

        petTypes.add("Amfibi");
        petTypes.add("Gat");
        petTypes.add("Gos");
        petTypes.add("Mascota Aquatica");
        petTypes.add("Ocell/Au");
        petTypes.add("Reptil");
        petTypes.add("Rosegador");
        petTypes.add("Altre");

        OneParentExpandibleAdapter MyAdapter = new OneParentExpandibleAdapter(this,"Espècie",petTypes);
        petTypeList.setAdapter(MyAdapter);
    }

}
