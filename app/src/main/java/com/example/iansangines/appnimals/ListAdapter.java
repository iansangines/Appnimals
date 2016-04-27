package com.example.iansangines.appnimals;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ian on 09/04/2016.
 */
public class ListAdapter extends ArrayAdapter<Pet> {
    private Context context;
    private int resource;
    private ArrayList<Pet> pets;


    public ListAdapter(Context context, int resource, ArrayList<Pet> pets){
        super(context,resource,pets);
        this.context = context;
        this.resource = resource; //id de l'Xml amb le layout
        this.pets = pets;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Pet itemPet = pets.get(position);
        if(convertView == null) convertView = LayoutInflater.from(context).inflate(R.layout.listed_pet, parent, false);

        ImageView petImage = (ImageView) convertView.findViewById(R.id.pet_image);
        TextView petName = (TextView) convertView.findViewById(R.id.animal_name);
        TextView petType = (TextView) convertView.findViewById(R.id.animal_type);
        ImageButton petDelete = (ImageButton) convertView.findViewById(R.id.delete);

        petImage.setImageURI(Uri.parse(itemPet.getthumbnailPath()));
        petName.setText(itemPet.name);
        petType.setText(itemPet.petType);
        petDelete.setImageDrawable(null);


        return convertView;
    }


}
