package com.example.iansangines.appnimals.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.iansangines.appnimals.Controllers.ImageFileController;
import com.example.iansangines.appnimals.Controllers.PetDBController;
import com.example.iansangines.appnimals.Domain.Pet;
import com.example.iansangines.appnimals.R;

import java.io.File;
import java.util.Calendar;


public class InsertPetActivity extends AppCompatActivity {
    private boolean clicked = false;
    static final int INSERTED = 1;
    private ImageView imgView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PICK_IMAGE = 2;
    ImageFileController imageController = new ImageFileController();
    File fullSizeImage;
    File thumbnailImage;

    static Calendar c = Calendar.getInstance();
    static int year = c.get(Calendar.YEAR);
    static int month = c.get(Calendar.MONTH);
    static int day = c.get(Calendar.DAY_OF_MONTH);
    Pet petToInsert = new Pet();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_pet);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("");


        imageController.CreateDirectories();

        fullSizeImage = imageController.getFullSizeFile();
        thumbnailImage = imageController.getThumbnailFile();


        ImageView datebutton = (ImageView) findViewById(R.id.calendar_imgbutton);
        assert datebutton != null;
        datebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new StartDatePicker();
                dialogFragment.show(getFragmentManager(), "start_date_picker");
                Toast.makeText(getApplicationContext(), Integer.toString(year) + "/" + Integer.toString(month) + "/" + Integer.toString(day), Toast.LENGTH_LONG).show();
            }
        });

       final TextInputLayout especialsInput = (TextInputLayout) findViewById(R.id.editesp);
        assert especialsInput != null;
        final EditText especialsEdit = especialsInput.getEditText();
        assert especialsEdit != null;
        CheckBox chk = (CheckBox) findViewById(R.id.especial);
        assert chk != null;
        chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(InsertPetActivity.this, "HOLI PUTICORNIOS", Toast.LENGTH_SHORT).show();
                if(isChecked){
                    Toast.makeText(InsertPetActivity.this, "is chked madafaca", Toast.LENGTH_SHORT).show();
                    especialsInput.setVisibility(View.VISIBLE);
                }
            }
        });

        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.fab);
        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.fab) {
                    TextInputLayout nameinputlayout = (TextInputLayout) findViewById(R.id.layout_input_nom);
                    assert nameinputlayout != null;
                    EditText nameinput = nameinputlayout.getEditText();
                    assert nameinput != null;
                    String name = nameinput.getText().toString();
                    if (name == null || name.equals("")) {
                        AlertDialog.Builder nameDialog = new AlertDialog.Builder(InsertPetActivity.this);
                        nameDialog.setMessage("Introdueix un nom").setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                        return;
                    } else {
                        petToInsert.setName(name);
                    }

                    TextInputLayout datainputlayout = (TextInputLayout) findViewById(R.id.layout_input_date);
                    assert datainputlayout != null;
                    EditText datainput = datainputlayout.getEditText();
                    assert datainput != null;
                    String data = datainput.getText().toString();
                    if (data == null || data.equals("")) {

                        AlertDialog.Builder dateDialog = new AlertDialog.Builder(InsertPetActivity.this);
                        dateDialog.setMessage("Introdueix una data").setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                        return;

                    } else {
                        petToInsert.setBornDate(data);
                    }

                    TextInputLayout xipinputlayout = (TextInputLayout) findViewById(R.id.layout_input_xip);
                    assert xipinputlayout != null;
                    EditText xipinput = xipinputlayout.getEditText();
                    assert xipinput != null;
                    String xip = (xipinput.getText().toString());
                    if (xip == null || xip.equals("")) {
                        AlertDialog.Builder xipDialog = new AlertDialog.Builder(InsertPetActivity.this);
                        xipDialog.setMessage("Introdueix una número de xip").setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                        return;
                    } else {
                        petToInsert.setChipNumber(xip);
                    }

                    TextInputLayout subtypeinputlayout = (TextInputLayout) findViewById(R.id.layout_input_rasa);
                    assert subtypeinputlayout != null;
                    EditText subtypeinput = subtypeinputlayout.getEditText();
                    assert subtypeinput != null;
                    String subtype = subtypeinput.getText().toString();
                    if (subtype == null || subtype.equals("")) {

                        AlertDialog.Builder typeDialog = new AlertDialog.Builder(InsertPetActivity.this);
                        typeDialog.setMessage("Introdueix una raça/tipus").setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                        return;
                    } else {
                        petToInsert.setPetType(subtype);
                    }

                    if (petToInsert.getPhotoPath() == null || petToInsert.getPhotoPath().equals("")) {
                        AlertDialog.Builder photoDialog = new AlertDialog.Builder(InsertPetActivity.this);
                        photoDialog.setMessage("Es requereix una imatge").setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                        return;
                    }

                    String especials = especialsEdit.getText().toString();
                    petToInsert.setEspecial(especials);

                    PetDBController db = new PetDBController(InsertPetActivity.this);
                    int id = db.insertPet(petToInsert.getName(), petToInsert.getBornDate(), petToInsert.getPetType(),
                            petToInsert.getChipNumber(), petToInsert.getPhotoPath(), petToInsert.getthumbnailPath(), petToInsert.getEspecial());
                    Toast.makeText(InsertPetActivity.this, "Mascota guardada", Toast.LENGTH_SHORT).show();
                    //Retorna el numero de id per fer query al petlistactivity
                    Intent returned = new Intent();
                    returned.putExtra("id", id);
                    setResult(INSERTED, returned);
                    finish();
                }
            }
        });

        imgView = (ImageView) findViewById(R.id.imgview);
        assert imgView != null;
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(InsertPetActivity.this, imgView);
                popup.getMenuInflater().inflate(R.menu.photo_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
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
        //FINAL ON CREate
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fullSizeImage));
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void pickPictureGalleryIntent() {
        Intent pickPictureIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pickPictureIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(pickPictureIntent, "Selecciona la imatge"), PICK_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {

                String fullSizeImagePath = fullSizeImage.getAbsolutePath();
                petToInsert.setPetPhotoPath(fullSizeImagePath);

                Bitmap imageBitmap = BitmapFactory.decodeFile(fullSizeImagePath);

                Bitmap thumbnail = imageController.saveThumbnailImage(imageBitmap, thumbnailImage);
                petToInsert.setPetthumbnailPath(thumbnailImage.getAbsolutePath());
                imgView.setImageBitmap(thumbnail);
            } catch (Exception e) {
                e.printStackTrace();

            }


        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode != RESULT_OK) {
            petToInsert.setPetPhotoPath(null);
            petToInsert.setPetthumbnailPath(null);
        } else if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            try {

                Uri photoUri = data.getData();
                if (photoUri != null) Log.d("PICK_IMAGE", "URI:" + photoUri.getPath());
                else Log.d("URI", "null");
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

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
                petToInsert.setPetPhotoPath(fullSizeImage.getAbsolutePath());

                Bitmap thumbnail = imageController.saveThumbnailImage(imageBitmap, thumbnailImage);
                petToInsert.setPetthumbnailPath(thumbnailImage.getAbsolutePath());
                imgView.setImageBitmap(thumbnail);


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE && resultCode != RESULT_OK) {
            petToInsert.setPetPhotoPath(null);
            petToInsert.setPetthumbnailPath(null);
        }
    }

    public static class StartDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, this, year, month, day);
            return dialog;

        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            TextInputLayout datainputlayout = (TextInputLayout) getActivity().findViewById(R.id.layout_input_date);
            assert datainputlayout != null;
            EditText datainput = datainputlayout.getEditText();
            assert datainput != null;
            String date = Integer.toString(day) + "/" + Integer.toString(month + 1) + "/" + Integer.toString(year);
            datainput.setText(date);
        }
    }


}
