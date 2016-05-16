package com.example.iansangines.appnimals;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by iansangines on 15/05/2016.
 */
public class EventListAdapter extends ArrayAdapter<Event> {
    private Context context;
    private int resource;
    private ArrayList<Event> events;
    private final String[] MONTHS = {"Gen", "Feb", "Març", "Abr", "Maig", "Juny", "Jul", "Ag", "Set", "Oct", "Nov", "Des"};


    public EventListAdapter(Context context, int resource, ArrayList<Event> events){
        super(context,resource,events);
        this.context = context;
        this.resource = resource; //id de l'Xml amb le layout
        this.events = events;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Event itemEvent = events.get(position);
        if(convertView ==  null) convertView = LayoutInflater.from(context).inflate(R.layout.listed_event, parent, false);

        TextView eventDay = (TextView) convertView.findViewById(R.id.eventday);
        TextView eventMonth= (TextView) convertView.findViewById(R.id.eventmonth);
        TextView eventYear = (TextView) convertView.findViewById(R.id.eventyear);
        TextView eventName = (TextView) convertView.findViewById(R.id.eventname);
        TextView eventPet = (TextView) convertView.findViewById(R.id.eventpet);
        TextView eventHourLoc = (TextView) convertView.findViewById(R.id.eventhourloc);
        ImageButton delete = (ImageButton) convertView.findViewById(R.id.deletevent);

        eventDay.setText(itemEvent.getDay());
        eventMonth.setText(MONTHS[Integer.parseInt(itemEvent.getMonth())-1]);
        eventYear.setText(itemEvent.getYear());
        eventName.setText(itemEvent.getName());
        String pet = itemEvent.getPetName() +" - "+itemEvent.getPetChip();
        eventPet.setText(pet);
        String hourLoc = itemEvent.getHour() + ":" + itemEvent.getMinute() + " - " + itemEvent.getEventLocation();
        eventHourLoc.setText(hourLoc);
        Log.d("randooooom", eventHourLoc.getText().toString());
        delete.setImageBitmap(null);

        return convertView;
    }

}
