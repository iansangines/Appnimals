package com.example.iansangines.appnimals;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

        for (int i = 0; i < eventList.size(); i++) {
            Event e = eventList.get(i);
            Log.d("eventliist posició:", Integer.toString(i));
            Log.d("nom", e.getName());
            Log.d("dia", e.getDay());
            Log.d("mes", e.getMonth());
            Log.d("year", e.getYear());
            // Log.d("tipus", e.getEventType());
            Log.d("hora", e.getHour());
            Log.d("minut", e.getMinute());
            Log.d("loc", e.getEventLocation());
            Log.d("desc", e.getEventDescription());
        }

        adapter = new EventListAdapter(CalendarActivity.this, R.layout.listed_event, eventList);
        assert eventListView != null;
        eventListView.setAdapter(adapter);

        calendarView = (CalendarView) findViewById(R.id.calendar);

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
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calendar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.add_event):
                Intent addEvent = new Intent(CalendarActivity.this, AddEventActivity.class);
                startActivityForResult(addEvent, 5);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 5) {
            dbController = new PetDBController(CalendarActivity.this);
            eventList = dbController.queryAllEvents();
            adapter = new EventListAdapter(getApplicationContext(), R.layout.listed_event, eventList);
            assert eventListView != null;
            eventListView.setAdapter(adapter);

        }
    }

}
