package com.example.iansangines.appnimals;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;


public class ProfileActivity extends AppCompatActivity {
    private Pet profilePet;
    private PetDBController dbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);


        String petChip = getIntent().getStringExtra("chip");
        dbController = new PetDBController(this);
        profilePet = dbController.queryPet(petChip);
        collapsingToolbar.setTitle(profilePet.getName());
        Bitmap petBitmap = BitmapFactory.decodeFile(profilePet.getPhotoPath());

        ImageView petImage = (ImageView) findViewById(R.id.toolbarimg);
        petImage.setImageBitmap(petBitmap);
    }

}
