package com.example.iansangines.appnimals.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.iansangines.appnimals.Controllers.PetDBController;
import com.example.iansangines.appnimals.Domain.Event;
import com.example.iansangines.appnimals.Domain.EventListAdapter;
import com.example.iansangines.appnimals.R;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {

    private ListView eventListView;
    private CalendarView calendarView;
    private PetDBController dbController;
    private ArrayList<Event> eventList;
    private EventListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarcalendar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Calendari");

        eventListView = (ListView) findViewById(R.id.eventlist);
        dbController = new PetDBController(this);
        eventList = dbController.queryAllEvents();
        adapter = new EventListAdapter(CalendarActivity.this, R.layout.listed_event, eventList);
        assert eventListView != null;
        eventListView.setAdapter(adapter);

        calendarView = (CalendarView) findViewById(R.id.calendar);

        eventListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
                alertDialog.setMessage("Vols editar l'Esdeveniment?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Event e = (Event) eventListView.getItemAtPosition(pos);
                        Intent editEvent = new Intent(CalendarActivity.this, AddEventActivity.class);
                        editEvent.putExtra("eventId", e.getId());
                        startActivityForResult(editEvent,5);
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

        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event e = (Event) eventListView.getItemAtPosition(position);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, Integer.parseInt(e.getYear()));
                calendar.set(Calendar.MONTH, Integer.parseInt(e.getMonth()) - 1);
                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(e.getDay()));
                calendarView.setDate(calendar.getTimeInMillis() - 1, false, true);
                calendarView.setDate(calendar.getTimeInMillis(), false, true);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabcalendar);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addEvent = new Intent(CalendarActivity.this, AddEventActivity.class);
                startActivityForResult(addEvent, 5);
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calendar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 5) {
            dbController = new PetDBController(CalendarActivity.this);
            eventList = dbController.queryAllEvents();
            adapter.clear();
            adapter.addAll(eventList);
            adapter.notifyDataSetChanged();

        }
    }

}
