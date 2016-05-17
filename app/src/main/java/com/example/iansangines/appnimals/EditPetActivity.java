package com.example.iansangines.appnimals;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;

public class EditPetActivity extends AppCompatActivity {

    private boolean clicked = false;
    static final int INSERTED = 1;
    private ImageView imgView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PICK_IMAGE = 2;
    ImageFileController imageController = new ImageFileController();
    File fullSizeImage;
    File thumbnailImage;

    static int year;
    static int month;
    static int day;

    private PetDBController dbController = new PetDBController(this);
    private Pet petToEdit = new Pet();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_pet);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Edita la mascota");

        String petChip = getIntent().getStringExtra("chip");
        petToEdit = dbController.queryPet(petChip);
        ImageView photo = (ImageView) findViewById(R.id.imgview);
        Bitmap petBitmap = BitmapFactory.decodeFile(petToEdit.getthumbnailPath());

        photo.setImageBitmap(petBitmap);
        fullSizeImage = new File(petToEdit.getPhotoPath());
        thumbnailImage = new File(petToEdit.getthumbnailPath());

        TextInputLayout layoutname = (TextInputLayout) findViewById(R.id.layout_input_nom);
        EditText name = layoutname.getEditText();
        name.setText(petToEdit.getName());

        TextInputLayout layoutdate = (TextInputLayout) findViewById(R.id.layout_input_date);
        EditText date = layoutdate.getEditText();
        String bdate = petToEdit.getBornDate();
        date.setText(bdate);
        String[] splittedbdate = bdate.split("/");
        day = Integer.parseInt(splittedbdate[0]);
        month = Integer.parseInt(splittedbdate[1]);
        year = Integer.parseInt(splittedbdate[2]);

        TextInputLayout layoutchip = (TextInputLayout) findViewById(R.id.layout_input_xip);
        EditText chip = layoutchip.getEditText();
        chip.setText(petToEdit.getChipNumber());

        TextInputLayout layouttype = (TextInputLayout) findViewById(R.id.layout_input_rasa);
        EditText type = layouttype.getEditText();
        type.setText(petToEdit.getPetType());

        ImageView datebutton = (ImageView) findViewById(R.id.calendar_imgbutton);
        assert datebutton != null;
        datebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new StartDatePicker();
                dialogFragment.show(getFragmentManager(), "start_date_picker");
            }
        });


        imgView = (ImageView) findViewById(R.id.imgview);
        assert imgView!= null;
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onclick image", "entra");
                PopupMenu popup = new PopupMenu(EditPetActivity.this, imgView);
                popup.getMenuInflater().inflate(R.menu.photo_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Log.d("onclick popup", "entra");
                        if (item.getItemId() == R.id.camera) {
                            dispatchTakePictureIntent();
                            return true;
                        } else {
                            pickPictureGalleryIntent();
                            return false;
                        }
                    }
                });
                popup.show();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fullSizeImage));
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void pickPictureGalleryIntent(){
        Intent pickPictureIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pickPictureIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(pickPictureIntent,"Selecciona la imatge"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("reslt_ok", Integer.toString(resultCode));
        imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.d("result", "enter result_ok");
            try {

                String fullSizeImagePath = fullSizeImage.getAbsolutePath();
                petToEdit.setPetPhotoPath(fullSizeImagePath);

                Bitmap imageBitmap = BitmapFactory.decodeFile(fullSizeImagePath);

                Bitmap thumbnail = imageController.saveThumbnailImage(imageBitmap, thumbnailImage);
                petToEdit.setPetthumbnailPath(thumbnailImage.getAbsolutePath());
                imgView.setImageBitmap(thumbnail);
            }
            catch (Exception e){
                Log.d("requestimagecapture", "Peta el try");
                e.printStackTrace();
                Log.d("requestimagecapture", "Peta el try");

            }


        }
        else if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode != RESULT_OK){
            Log.d("result", "enter NOT result_ok");
            petToEdit.setPetPhotoPath(null);
            petToEdit.setPetthumbnailPath(null);
        }

        else if (requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            Log.d("result", "pick image");
            try {

                Uri photoUri = data.getData();
                if( photoUri != null) Log.d("PICK_IMAGE", "URI:" + photoUri.getPath() );
                else Log.d("URI", "null");
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(photoUri,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                Bitmap imageBitmap = BitmapFactory.decodeFile(imgDecodableString);

                imageController.saveFullSizeImage(imageBitmap, fullSizeImage);
                petToEdit.setPetPhotoPath(fullSizeImage.getAbsolutePath());

                Bitmap thumbnail = imageController.saveThumbnailImage(imageBitmap, thumbnailImage);
                petToEdit.setPetthumbnailPath(thumbnailImage.getAbsolutePath());
                imgView.setImageBitmap(thumbnail);


            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(requestCode == PICK_IMAGE && resultCode != RESULT_OK){
            Log.d("result", "enter NOT result_ok");
            petToEdit.setPetPhotoPath(null);
            petToEdit.setPetthumbnailPath(null);
        }
    }

    public static class StartDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            DatePickerDialog dialog = new DatePickerDialog(getActivity(),R.style.DialogTheme,this,year,month,day);
            return dialog;

        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            TextInputLayout datainputlayout = (TextInputLayout) getActivity().findViewById(R.id.layout_input_date);
            assert datainputlayout != null;
            EditText datainput = datainputlayout.getEditText();
            assert datainput != null;
            String date = Integer.toString(day) + "/" + Integer.toString(month+1) + "/" + Integer.toString(year);
            datainput.setText(date);
        }
    }
}
