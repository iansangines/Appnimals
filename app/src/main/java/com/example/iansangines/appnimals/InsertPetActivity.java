package com.example.iansangines.appnimals;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.renderscript.Sampler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class InsertPetActivity extends AppCompatActivity {
    private boolean clicked = false;
    static final int INSERTED = 1;
    static ImageView newImg;

    static final int REQUEST_IMAGE_CAPTURE = 1;




    private ArrayAdapter<CharSequence> adapter;
    Pet petToInsert = new Pet();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_pet);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        Spinner petTypes = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.especies,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assert petTypes != null;
        petTypes.setAdapter(adapter);
        petTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                petToInsert.setPetType((String) parent.getSelectedItem());
                if (position > 0 && position < adapter.getCount())
                    if (!clicked) {
                        clicked = true;
                        TextInputLayout subtypeinput = (TextInputLayout) findViewById(R.id.layout_input_rasa);
                        assert subtypeinput != null;
                        subtypeinput.setVisibility(view.VISIBLE);
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        Button button = (Button) findViewById(R.id.buttonafegir);
        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {

            boolean alertdialog;

            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.buttonafegir){
                    TextInputLayout nameinputlayout = (TextInputLayout) findViewById(R.id.layout_input_nom);
                    assert nameinputlayout != null;
                    EditText nameinput = nameinputlayout.getEditText();
                    assert nameinput != null;
                    String name = nameinput.getText().toString();
                    if(name == null || name.equals("")){
                        AlertDialog.Builder nameDialog = new AlertDialog.Builder(InsertPetActivity.this);
                        nameDialog.setMessage("Introdueix un nom").setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                        return;
                    }
                    else{
                        petToInsert.setName(name);
                    }

                    TextInputLayout datainputlayout = (TextInputLayout) findViewById(R.id.layout_input_date);
                    assert datainputlayout != null;
                    EditText datainput = datainputlayout.getEditText();
                    assert datainput != null;
                    String data = datainput.getText().toString();
                    if(data == null || data.equals("")){

                        AlertDialog.Builder dateDialog = new AlertDialog.Builder(InsertPetActivity.this);
                        dateDialog.setMessage("Introdueix una data").setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                        return;

                    }
                    else{
                        petToInsert.setBornDate(data);
                    }

                    TextInputLayout xipinputlayout = (TextInputLayout) findViewById(R.id.layout_input_xip);
                    assert xipinputlayout != null;
                    EditText xipinput = xipinputlayout.getEditText();
                    assert xipinput != null;
                    String xip = xipinput.getText().toString();
                    if(xip == null || xip.equals("")){

                        AlertDialog.Builder xipDialog = new AlertDialog.Builder(InsertPetActivity.this);
                        xipDialog.setMessage("Introdueix una número de xip").setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                        return;
                    }
                    else{
                        petToInsert.setChipNumber(xip);
                    }

                    TextInputLayout subtypeinputlayout = (TextInputLayout) findViewById(R.id.layout_input_rasa);
                    assert subtypeinputlayout != null;
                    EditText subtypeinput = subtypeinputlayout.getEditText();
                    assert subtypeinput != null;
                    String subtype = subtypeinput.getText().toString();
                    if(subtype == null || subtype.equals("")){

                        AlertDialog.Builder typeDialog = new AlertDialog.Builder(InsertPetActivity.this);
                        typeDialog.setMessage("Introdueix una raça/tipus").setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                        return;
                    }
                    else{
                        petToInsert.setChipNumber(xip);
                    }

                    if(petToInsert.getPhotoPath() == null || petToInsert.getPhotoPath().equals("")){
                        AlertDialog.Builder photoDialog = new AlertDialog.Builder(InsertPetActivity.this);
                        photoDialog.setMessage("Es requereix una imatge").setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                        return;
                    }


                    PetDBController db = new PetDBController(InsertPetActivity.this);
                    db.insertPet(petToInsert.getName(), petToInsert.getBornDate(), petToInsert.getPetType(), petToInsert.getPetSubtype(),
                            petToInsert.getChipNumber(), petToInsert.getPhotoPath().toString(), petToInsert.getthumbnailPath().toString());
                    Toast.makeText(InsertPetActivity.this,"Mascota guardada",Toast.LENGTH_SHORT).show();
                    //Retorna el numero de xip per fer query al petlistactivity
                    Intent returned = new Intent();
                    returned.putExtra("xip","'"+petToInsert.getChipNumber()+"'");
                    setResult(INSERTED,returned);
                    finish();


                }
            }
        });

        newImg = (ImageView) findViewById(R.id.imgview);
        assert newImg != null;
        newImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });



    }
    private File photoPath;
    private File thumbnailPath;
    private File imagesPath;
    private File thumbnailsPath;
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        String photoName = new SimpleDateFormat("ddMMyyy_HHmmss").format(new Date());
        String thumbnailName = new SimpleDateFormat("ddMMyyy_HHmmss").format(new Date()) + "_thumbnail";
        imagesPath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/../AppnimalsImages");
        thumbnailsPath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/../AppnimalsImages/Thumbnails");


        if(!imagesPath.exists()) imagesPath.mkdir(); Log.d(" no exists imagespath", "Sha creat el directori lokoooooooooooooo");
        if(!thumbnailsPath.exists()) thumbnailsPath.mkdir(); Log.d(" no exists thumbnails", "Sha creat el directori lokoooooooooooooo");


        try{
           photoPath = File.createTempFile(photoName, ".jpg", imagesPath);
            thumbnailPath = File.createTempFile(thumbnailName, ".png", thumbnailsPath);

       }

       catch (IOException x){

       }

       petToInsert.setPetPhotoPath(Uri.parse(photoPath.toString()));  //Uri de la foto per a la bd
        Log.d("path", photoPath.getPath());
        petToInsert.setPetthumbnailPath(Uri.parse(thumbnailPath.toString()));
        Log.d("path", thumbnailPath.getPath());

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(photoPath));
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(photoPath.getPath());
            newImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
            try {
                FileOutputStream thumbnail = new FileOutputStream(thumbnailPath);
                Log.d("photo width", Integer.toString(imageBitmap.getWidth()));
                Log.d("photo width/2", Integer.toString(imageBitmap.getWidth()/10));
                Bitmap scaled = Bitmap.createScaledBitmap(imageBitmap,(Integer)imageBitmap.getWidth()/10,((Integer) imageBitmap.getHeight()/10)-20,false);
                Log.d("thumbnail width", Integer.toString(scaled.getWidth()));
                scaled.compress(Bitmap.CompressFormat.PNG, 100, thumbnail);
                newImg.setImageBitmap(scaled);
                thumbnail.flush();
                thumbnail.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



}
