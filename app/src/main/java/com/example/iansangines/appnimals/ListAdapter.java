package com.example.iansangines.appnimals;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ian on 09/04/2016.
 */
public class ListAdapter extends ArrayAdapter<Pet> {
    private Context context;
    private int resource;
    private ArrayList<Pet> pets;


    public ListAdapter(Context context, int resource, ArrayList<Pet> pets) {
        super(context, resource, pets);
        this.context = context;
        this.resource = resource; //id de l'Xml amb le layout
        this.pets = pets;
        Log.d("context", context.toString());
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final Pet itemPet = pets.get(position);
        final int index = position;
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.listed_pet, parent, false);

        ImageView petImage = (ImageView) convertView.findViewById(R.id.pet_image);
        TextView petName = (TextView) convertView.findViewById(R.id.animal_name);
        TextView petType = (TextView) convertView.findViewById(R.id.animal_type);
        ImageView deleteImg = (ImageView) convertView.findViewById(R.id.deletepet);

        petImage.setImageURI(Uri.parse(itemPet.getthumbnailPath()));
        petName.setText(itemPet.getName());
        petType.setText(itemPet.getPetType());

        deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setMessage("Estas segur d'eliminar la mascota?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        PetDBController db = new PetDBController(context);
                        boolean deleted = db.deletePet(itemPet.getId());
                        if (deleted) {
                            db.deletePetEvents(itemPet.getId());
                            pets.remove(index);
                            Toast.makeText(context, "S'ha eliminat la mascota", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        } else {
                            Log.d("no s'ha eliminat lindex", Integer.toString(index));
                        }
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });

        return convertView;
    }


}
