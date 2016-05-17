package com.example.iansangines.appnimals;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ProfileActivity extends AppCompatActivity {
    private Pet profilePet;
    private PetDBController dbController;
    private RecyclerView eventList;
    private ArrayList<Event> petEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarprofile);
        setSupportActionBar(toolbar);


        String petChip = getIntent().getStringExtra("chip");
        dbController = new PetDBController(this);
        profilePet = dbController.queryPet(petChip);
        assert collapsingToolbar != null;
        collapsingToolbar.setTitle(profilePet.getName());
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        Bitmap petBitmap = BitmapFactory.decodeFile(profilePet.getPhotoPath());

        ImageView petImage = (ImageView) findViewById(R.id.toolbarimg);
        assert petImage != null;
        petImage.setImageBitmap(petBitmap);

        TextView chipTextView = (TextView) findViewById(R.id.profChip);
        assert chipTextView != null;
        chipTextView.setText(petChip);
        TextView bdTextView = (TextView) findViewById(R.id.profBd);
        String petBd = profilePet.getBornDate();
        assert bdTextView != null;
        bdTextView.setText(petBd);
        TextView typeTextView = (TextView) findViewById(R.id.profType);
        String petType = profilePet.getPetType();
        assert typeTextView != null;
        typeTextView.setText(petType);

        eventList = (RecyclerView) findViewById(R.id.eventlist);
        petEvents = dbController.queryPetEvents(profilePet.getChipNumber());
        for(int i = 0; i < petEvents.size() ; i++){
            petEvents.get(i).getName();
        }
        EventRecyclerAdapter adapter = new EventRecyclerAdapter(petEvents);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        eventList.setLayoutManager(mLayoutManager);
        eventList.setItemAnimator(new DefaultItemAnimator());
        eventList.setNestedScrollingEnabled(false);
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
                edit.putExtra("chip",profilePet.getChipNumber());
                startActivity(edit);
                return true;
            case R.id.add_event:
                Intent addevent = new Intent(ProfileActivity.this, AddEventActivity.class);
                addevent.putExtra("chip",profilePet.getChipNumber());
                startActivityForResult(addevent,1);
            default: return super.onOptionsItemSelected(item);

        }
    }
}
