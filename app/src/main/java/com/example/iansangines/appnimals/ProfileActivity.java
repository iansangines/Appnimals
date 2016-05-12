package com.example.iansangines.appnimals;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;


public class ProfileActivity extends AppCompatActivity {
    private Pet profilePet;
    private PetDBController dbController;

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

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

}
