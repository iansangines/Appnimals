package com.example.iansangines.appnimals.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.iansangines.appnimals.Controllers.PetDBController;
import com.example.iansangines.appnimals.Domain.Event;
import com.example.iansangines.appnimals.Domain.EventRecyclerAdapter;
import com.example.iansangines.appnimals.Domain.Pet;
import com.example.iansangines.appnimals.Activities.R;

import java.util.ArrayList;


public class ProfileActivity extends AppCompatActivity {
    private Pet profilePet;
    private PetDBController dbController;
    private RecyclerView eventList;
    private ArrayList<Event> petEvents;
    EventRecyclerAdapter adapter;
    static final int EDIT_PET_ACTIVITY = 0;
    static final int ADD_EVENT_ACTIVITY = 1;
    static final int INSERTED = 1;

    private TextView chipTextView;
    private TextView bdTextView;
    private TextView typeTextView;
    private TextView espTextView;
    private ImageView petImage;
    private CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarprofile);
        setSupportActionBar(toolbar);


        int petId = getIntent().getIntExtra("id", -1);
        dbController = new PetDBController(this);
        profilePet = dbController.queryPetById(petId);
        assert collapsingToolbar != null;
        collapsingToolbar.setTitle(profilePet.getName());
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        Bitmap petBitmap = BitmapFactory.decodeFile(profilePet.getPhotoPath());

        petImage = (ImageView) findViewById(R.id.toolbarimg);
        assert petImage != null;
        petImage.setImageBitmap(petBitmap);

        chipTextView = (TextView) findViewById(R.id.profChip);
        assert chipTextView != null;
        String petChip = profilePet.getChipNumber();
        chipTextView.setText(petChip);
        bdTextView = (TextView) findViewById(R.id.profBd);
        String petBd = profilePet.getBornDate();
        assert bdTextView != null;
        bdTextView.setText(petBd);
        typeTextView = (TextView) findViewById(R.id.profType);
        String petType = profilePet.getPetType();
        assert typeTextView != null;
        typeTextView.setText(petType);

        espTextView = (TextView) findViewById(R.id.profEsp);
        assert espTextView != null;
        String esp = profilePet.getEspecial();
        if(esp == null || esp.equals("") ){
            RelativeLayout rl = (RelativeLayout) findViewById(R.id.especiallayout);
            assert rl != null;
            rl.setVisibility(View.GONE);
        }
        else espTextView.setText(esp);


        eventList = (RecyclerView) findViewById(R.id.eventview);
        petEvents = dbController.queryPetEvents(profilePet.getId());

        eventList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

        adapter = new EventRecyclerAdapter(petEvents,ProfileActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ProfileActivity.this);
        eventList.setLayoutManager(mLayoutManager);
        eventList.setAdapter(adapter);
        adapter.swap(petEvents);
        eventList.setAdapter(adapter);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_edit:
                Log.d("action_edit", "inside");
                Intent edit = new Intent(ProfileActivity.this, EditPetActivity.class);
                edit.putExtra("id", profilePet.getId());
                startActivityForResult(edit, EDIT_PET_ACTIVITY);
                return true;
            case R.id.add_event:
                Intent addevent = new Intent(ProfileActivity.this, AddEventActivity.class);
                addevent.putExtra("petId", profilePet.getId());
                startActivityForResult(addevent, ADD_EVENT_ACTIVITY);
                return true;
            case R.id.ajudaperfil:
                Intent i = new Intent(ProfileActivity.this, HelpActivity.class);
                startActivity(i);

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_PET_ACTIVITY && resultCode == INSERTED) {
            PetDBController dbController = new PetDBController(this);
            profilePet = dbController.queryPetById(data.getIntExtra("id", -1));
            collapsingToolbar.setTitle(profilePet.getName());
            chipTextView.setText(profilePet.getChipNumber());
            bdTextView.setText(profilePet.getBornDate());
            typeTextView.setText(profilePet.getPetType());
            Bitmap petBitmap = BitmapFactory.decodeFile(profilePet.getPhotoPath());
            petImage.setImageBitmap(petBitmap);
        }
        petEvents = dbController.queryPetEvents(profilePet.getId());
        adapter.swap(petEvents);
        eventList.setAdapter(adapter);
    }
}
