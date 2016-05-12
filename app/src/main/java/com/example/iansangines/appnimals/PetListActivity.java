package com.example.iansangines.appnimals;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
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

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

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
                    startActivityForResult(insertPet,INSERT_PET_ACTIVITY);
                }
            });
        }


        petListView = (ListView) findViewById(R.id.listview);
        petListView.setClickable(true);
        //BASE DE DADES
        dbController = new PetDBController(this);
        petList = dbController.queryAll();

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
                Log.d("onItemClick" , "aaaaaaaaa");
                Intent profile = new Intent(PetListActivity.this, ProfileActivity.class);
                Pet auxPet =(Pet) petListView.getItemAtPosition(position);
                profile.putExtra("chip",auxPet.getChipNumber());
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
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String chip = null;
                        String auxName = input.getText().toString();
                        for(int i = 0; i < petList.size(); i++) if(petList.get(i).getName().equals(auxName)) chip = petList.get(i).getChipNumber();

                        if(chip != null) {
                            Intent profile = new Intent(PetListActivity.this, ProfileActivity.class);
                            profile.putExtra("chip", chip);
                            startActivity(profile);
                        }
                        else Toast.makeText(PetListActivity.this, "La mascota no existeix", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("Torna", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                return true;
            case R.id.calendaricon:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == INSERT_PET_ACTIVITY && resultCode == INSERTED){
            //Data conté dos bitmaps (fullsize, thumbnail) i en el Result es guarden en el path de la mascota que
            //retorna el data Intent.  (Aixi no es crea el fitxer de la imatge fins que s'ha acabat
            //el insertpetactivity)


            String xip = data.getStringExtra("xip");
            Pet pet = dbController.queryPet(xip);
            if(pet == null) Log.d("peeeeeeeeeet",xip);
            else {
                Log.d("peeeeeeeeet nom", pet.getName());
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
