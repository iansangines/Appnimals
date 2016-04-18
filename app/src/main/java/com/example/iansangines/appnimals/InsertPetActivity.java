package com.example.iansangines.appnimals;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;



public class InsertPetActivity extends AppCompatActivity {
    private boolean clicked = false;
    static final int INSERTED = 1;
    static ImageView imgView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PICK_IMAGE = 2;




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
                if (v.getId() == R.id.buttonafegir) {
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
                    String xip = xipinput.getText().toString();
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
                        petToInsert.setChipNumber(xip);
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


                    PetDBController db = new PetDBController(InsertPetActivity.this);
                    db.insertPet(petToInsert.getName(), petToInsert.getBornDate(), petToInsert.getPetType(), petToInsert.getPetSubtype(),
                            petToInsert.getChipNumber(), petToInsert.getPhotoPath().toString(), petToInsert.getthumbnailPath().toString());
                    Toast.makeText(InsertPetActivity.this, "Mascota guardada", Toast.LENGTH_SHORT).show();
                    //Retorna el numero de xip per fer query al petlistactivity
                    Intent returned = new Intent();
                    returned.putExtra("xip", "'" + petToInsert.getChipNumber() + "'");
                    setResult(INSERTED, returned);
                    finish();


                }
            }
        });

        imgView = (ImageView) findViewById(R.id.imgview);
        assert imgView!= null;
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onclick image", "entra");
                PopupMenu popup = new PopupMenu(InsertPetActivity.this, imgView);
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
        //FINAL ON CREate
    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
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
        ImageFileController imageController = new ImageFileController();
        imageController.CreateDirectories();

        Log.d("reslt_ok", Integer.toString(resultCode));
        imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.d("result", "enter result_ok");
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("Data");

            String fullSizeImagePath = imageController.saveFullSizeImage(imageBitmap);
            petToInsert.setPetPhotoPath(Uri.parse(fullSizeImagePath));

            Pair<String,Bitmap> thumbnail = imageController.saveThumbnailImage(imageBitmap);
            petToInsert.setPetthumbnailPath(Uri.parse(thumbnail.first));
            imgView.setImageBitmap(thumbnail.second);


        }
        else if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode != RESULT_OK){
            Log.d("result", "enter NOT result_ok");
            petToInsert.setPetPhotoPath(null);
            petToInsert.setPetthumbnailPath(null);
        }

        else if (requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            Log.d("result", "pick image");
            try {
            Uri photoUri = data.getData();
            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);

                String fullSizeImagePath = imageController.saveFullSizeImage(imageBitmap);
                petToInsert.setPetPhotoPath(Uri.parse(fullSizeImagePath));

                Pair<String,Bitmap> thumbnail = imageController.saveThumbnailImage(imageBitmap);
                petToInsert.setPetthumbnailPath(Uri.parse(thumbnail.first));
                imgView.setImageBitmap(thumbnail.second);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(requestCode == PICK_IMAGE && resultCode != RESULT_OK){
            Log.d("result", "enter NOT result_ok");
            petToInsert.setPetPhotoPath(null);
            petToInsert.setPetthumbnailPath(null);
        }
    }



}
