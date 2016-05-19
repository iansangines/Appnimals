package com.example.iansangines.appnimals;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by iansangines on 17/05/2016.
 */
public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.MyViewHolder>  {

    private ArrayList<Event> eventList;
    private final String[] MONTHS = {"Gen", "Feb", "Març", "Abr", "Maig", "Juny", "Jul", "Ag", "Set", "Oct", "Nov", "Des"};

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView day, month, year, eventName, eventPet, eventHourLoc;
        public ImageView deleteImg;

        public MyViewHolder(View view) {
            super(view);
            day = (TextView) view.findViewById(R.id.eventday);
            month = (TextView) view.findViewById(R.id.eventmonth);
            year = (TextView) view.findViewById(R.id.eventyear);
            eventName = (TextView) view.findViewById(R.id.eventname);
            eventPet =(TextView) view.findViewById(R.id.eventpet);
            eventHourLoc = (TextView) view.findViewById(R.id.eventhourloc);
            deleteImg = (ImageView) view.findViewById(R.id.deletevent);
        }
    }

    public EventRecyclerAdapter(ArrayList<Event> eventList){
        this.eventList = eventList;
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
        holder.day.setText(e.getDay());
        holder.month.setText(MONTHS[Integer.parseInt(e.getMonth())-1]);
        holder.year.setText(e.getYear());
        holder.eventName.setText(e.getName());
        String epet = e.getPetName() + " - " + e.getPetChip();
        holder.eventPet.setText(epet);
        String hourloc = e.getHour() + ":" + e.getMinute() + " - " + e.getEventLocation();
        holder.eventHourLoc.setText(hourloc);
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
}

