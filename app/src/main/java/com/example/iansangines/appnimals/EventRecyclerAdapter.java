package com.example.iansangines.appnimals;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by iansangines on 17/05/2016.
 */
public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.MyViewHolder>  {

    private ArrayList<Event> eventList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView day, month, year, eventName, eventPet, eventHourLoc;

        public MyViewHolder(View view) {
            super(view);
            day = (TextView) view.findViewById(R.id.eventday);
            month = (TextView) view.findViewById(R.id.eventmonth);
            year = (TextView) view.findViewById(R.id.eventyear);
            eventName = (TextView) view.findViewById(R.id.eventname);
            eventPet =(TextView) view.findViewById(R.id.eventpet);
            eventHourLoc = (TextView) view.findViewById(R.id.eventhourloc);
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
        Event e = eventList.get(position);
        holder.day.setText(e.getDay());
        holder.month.setText(e.getMonth());
        holder.year.setText(e.getYear());
        holder.eventName.setText(e.getName());
        String epet = e.getPetName() + " - " + e.getPetChip();
        holder.eventPet.setText(epet);
        String hourloc = e.getHour() + ":" + e.getMinute() + " - " + e.getEventLocation();
        holder.eventHourLoc.setText(hourloc);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}

