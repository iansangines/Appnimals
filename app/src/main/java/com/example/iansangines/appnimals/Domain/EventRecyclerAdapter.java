package com.example.iansangines.appnimals.Domain;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iansangines.appnimals.Activities.AddEventActivity;
import com.example.iansangines.appnimals.Controllers.PetDBController;
import com.example.iansangines.appnimals.Activities.R;

import java.util.ArrayList;

/**
 * Created by iansangines on 17/05/2016.
 */
public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.MyViewHolder> {

    private ArrayList<Event> eventList;
    private final String[] MONTHS = {"Gen", "Feb", "Març", "Abr", "Maig", "Juny", "Jul", "Ag", "Set", "Oct", "Nov", "Des"};
    private final Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        public TextView day, month, year, eventName, eventPet, eventHourLoc;
        public ImageView deleteImg;

        public MyViewHolder(View view) {
            super(view);
            this.view = view;
            day = (TextView) view.findViewById(R.id.eventday);
            month = (TextView) view.findViewById(R.id.eventmonth);
            year = (TextView) view.findViewById(R.id.eventyear);
            eventName = (TextView) view.findViewById(R.id.eventname);
            eventPet = (TextView) view.findViewById(R.id.eventpet);
            eventHourLoc = (TextView) view.findViewById(R.id.eventhourloc);
            deleteImg = (ImageView) view.findViewById(R.id.deletevent);
        }
    }

    public EventRecyclerAdapter(ArrayList<Event> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listed_event, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Event e = eventList.get(position);
        final int index = position;

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                alertDialog.setMessage("Vols editar l'Esdeveniment?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent editEvent = new Intent(context, AddEventActivity.class);
                        editEvent.putExtra("eventId", e.getId());
                        ((Activity) context).startActivityForResult(editEvent,5);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                return false;
            }
        });
        holder.day.setText(e.getDay());
        holder.month.setText(MONTHS[Integer.parseInt(e.getMonth()) - 1]);
        holder.year.setText(e.getYear());
        holder.eventName.setText(e.getName());
        String epet = e.getPetName() + " - " + e.getEventType();
        holder.eventPet.setText(epet);
        String hourLoc = e.getHour() + ":" + e.getMinute();
        if(!e.getEventLocation().equals("") || e.getEventLocation() != null){
            hourLoc = hourLoc  + " - " + e.getEventLocation();
        }
        holder.eventHourLoc.setText(hourLoc);
        holder.deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View aux = v;
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                alertDialog.setMessage("Estas segur d'eliminar l'esdeveniment?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        PetDBController db = new PetDBController(aux.getContext());
                        boolean deleted = db.deleteEvent(e.getId());
                        if (deleted) {
                            eventList.remove(index);
                            Toast.makeText(aux.getContext(), "S'ha eliminat l'esdeveniment", Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void swap(ArrayList<Event> eventList){
        this.eventList.clear();
        this.eventList.addAll(eventList);
        notifyDataSetChanged();
    }

}

