package com.example.iansangines.appnimals.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.iansangines.appnimals.Controllers.PetDBController;
import com.example.iansangines.appnimals.Domain.ListAdapter;
import com.example.iansangines.appnimals.Domain.Pet;
import com.example.iansangines.appnimals.R;

import java.util.ArrayList;

public class PetListActivity extends AppCompatActivity {
    private ListAdapter adapter;
    private ListView petListView;
    private ArrayList<Pet> petList;
    private PetDBController dbController;
    static final int INSERT_PET_ACTIVITY = 0;
    static final int INSERTED = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        assert toolbar != null;
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent insertPet = new Intent(PetListActivity.this, InsertPetActivity.class);
                    startActivityForResult(insertPet, INSERT_PET_ACTIVITY);
                }
            });
        }


        petListView = (ListView) findViewById(R.id.listview);
        petListView.setClickable(true);
        //BASE DE DADES
        dbController = new PetDBController(this);
        petList = dbController.queryAllPets();
        adapter = new ListAdapter(PetListActivity.this, R.layout.listed_pet, petList);
        assert petListView != null;
        petListView.setAdapter(adapter);

        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent profile = new Intent(PetListActivity.this, ProfileActivity.class);
                Pet auxPet = (Pet) petListView.getItemAtPosition(position);
                profile.putExtra("id", auxPet.getId());
                startActivity(profile);
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.petlist_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.filter:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(PetListActivity.this);
                final EditText input = new EditText(this);
                input.setHint("Nom mascota");
                alertDialog.setTitle("Buscar");
                alertDialog.setView(input);
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = -1;
                        String auxName = input.getText().toString();
                        for (int i = 0; i < petList.size(); i++)
                            if (petList.get(i).getName().equals(auxName))
                                id = petList.get(i).getId();

                        if (id != -1) {
                            Intent profile = new Intent(PetListActivity.this, ProfileActivity.class);
                            profile.putExtra("id", id);
                            startActivity(profile);
                        } else
                            Toast.makeText(PetListActivity.this, "La mascota no existeix", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("Torna", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                return true;
            case R.id.calendaricon:
                Intent calendarIntent = new Intent(PetListActivity.this, CalendarActivity.class);
                startActivity(calendarIntent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INSERT_PET_ACTIVITY && resultCode == INSERTED) {
            Toast.makeText(PetListActivity.this, "Mascota Guardada", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        petList = dbController.queryAllPets();
        adapter.clear();
        adapter.addAll(petList);
        adapter.notifyDataSetChanged();
    }
}
