package com.example.iansangines.appnimals;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ExpandableListAdapter;
import android.widget.ListView;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.HashMap;
import java.util.List;


public class InsertPetActivity extends AppCompatActivity {
    SimpleExpandableListAdapter adapter;
    ExpandableListView petTypeList;
    List<String> petTypeHeader;
    HashMap<String, List<String>> petTypeChild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_pet);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
