package com.example.iansangines.appnimals;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
 * Created by iansangines on 15/05/2016.
 */
public class EventListAdapter extends ArrayAdapter<Event> {
    private Context context;
    private int resource;
    private ArrayList<Event> events;
    private final String[] MONTHS = {"Gen", "Feb", "Març", "Abr", "Maig", "Juny", "Jul", "Ag", "Set", "Oct", "Nov", "Des"};


    public EventListAdapter(Context context, int resource, ArrayList<Event> events) {
        super(context, resource, events);
        this.context = context;
        this.resource = resource; //id de l'Xml amb le layout
        this.events = events;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final Event itemEvent = events.get(position);
        final int index = position;
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.listed_event, parent, false);

        TextView eventDay = (TextView) convertView.findViewById(R.id.eventday);
        TextView eventMonth = (TextView) convertView.findViewById(R.id.eventmonth);
        TextView eventYear = (TextView) convertView.findViewById(R.id.eventyear);
        TextView eventName = (TextView) convertView.findViewById(R.id.eventname);
        TextView eventPet = (TextView) convertView.findViewById(R.id.eventpet);
        TextView eventHourLoc = (TextView) convertView.findViewById(R.id.eventhourloc);
        ImageView deleteImg = (ImageView) convertView.findViewById(R.id.deletevent);

        eventDay.setText(itemEvent.getDay());
        eventMonth.setText(MONTHS[Integer.parseInt(itemEvent.getMonth()) - 1]);
        eventYear.setText(itemEvent.getYear());
        eventName.setText(itemEvent.getName());
        String pet = itemEvent.getPetName() + " - " + itemEvent.getPetChip();
        eventPet.setText(pet);
        String hourLoc = itemEvent.getHour() + ":" + itemEvent.getMinute() + " - " + itemEvent.getEventLocation();
        eventHourLoc.setText(hourLoc);

        deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setMessage("Estas segur d'eliminar l'esdeveniment?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        PetDBController db = new PetDBController(context);
                        boolean deleted = db.deleteEvent(itemEvent.getId());
                        if (deleted) {
                            events.remove(index);
                            Toast.makeText(context, "S'ha eliminat l'esdeveniment", Toast.LENGTH_SHORT).show();
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
