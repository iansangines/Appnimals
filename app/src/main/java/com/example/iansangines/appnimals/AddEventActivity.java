package com.example.iansangines.appnimals;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddEventActivity extends AppCompatActivity {

    static final int INSERTED = 1;
    static Calendar c = Calendar.getInstance();
    final static int startDay = c.get(Calendar.DAY_OF_MONTH);
    final static int startMonth = c.get(Calendar.MONTH);
    final static int startYear = c.get(Calendar.YEAR);
    static int startHour = c.get(Calendar.HOUR_OF_DAY);
    static int startMinute = c.get(Calendar.MINUTE);
    private Button dateinput;
    private Button hourinput;
    private Event eventToInsert = new Event();
    private PetDBController db;

    private RadioButton vacunacio;
    private RadioButton veterniari;
    private RadioButton desp;
    private RadioButton altre;
    private Spinner petSpinner;
    private ArrayList<Pet> pets = null;
    private Pet pet = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarevent);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("");

        vacunacio = (RadioButton) findViewById(R.id.vac);
        assert vacunacio != null;
        vacunacio.setOnClickListener(radiolisten);

        veterniari = (RadioButton) findViewById(R.id.vet);
        assert veterniari != null;
        veterniari.setOnClickListener(radiolisten);

        desp = (RadioButton) findViewById(R.id.desp);
        assert desp != null;
        desp.setOnClickListener(radiolisten);

        altre = (RadioButton) findViewById(R.id.altre);
        assert altre != null;
        altre.setOnClickListener(radiolisten);

        dateinput = (Button) findViewById(R.id.data);
        assert dateinput != null;
        dateinput.setOnClickListener(listen);

        hourinput = (Button) findViewById(R.id.hour);
        assert hourinput != null;
        hourinput.setOnClickListener(listen);

        petSpinner = (Spinner) findViewById(R.id.petspinner);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        db = new PetDBController(AddEventActivity.this);

        if(getIntent().getExtras() == null) {
            spinnerAdapter.add("Selecciona una mascota..");
            pets = db.queryAllPets();
            for (int i = 0; i < pets.size(); i++) {
                Pet aux = pets.get(i);
                String name = aux.getName();
                String chip = aux.getChipNumber();
                String spin = name + " - " + chip;
                spinnerAdapter.add(spin);
            }
        }
        else{
            pet = db.queryPetById(getIntent().getIntExtra("id", -1));
            String spin = pet.getName() + " - " + pet.getChipNumber();
            spinnerAdapter.add(spin);
        }
        assert petSpinner != null;
        petSpinner.setAdapter(spinnerAdapter);

        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.confevent);
        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.confevent) {

                    //NOM DE L'ESDEVENIMENT (NECESSARI)
                    TextInputLayout eventnamelayout = (TextInputLayout) findViewById(R.id.input_eventname);
                    assert eventnamelayout != null;
                    EditText eventname = eventnamelayout.getEditText();
                    assert eventname != null;
                    String name = eventname.getText().toString();
                    if (name == null || name.equals("")) {
                        Toast.makeText(AddEventActivity.this, "Introdueix el nom de l'esdeveniment", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        eventToInsert.setName(name);
                    }

                    //DATA DE L'ESDEVENIMENT (NECESSARI)
                    String data = dateinput.getText().toString();
                    if (data == null || data.equals("dia. dd/mm/aaaa")) {
                        Toast.makeText(AddEventActivity.this, "Introdueix una data", Toast.LENGTH_SHORT).show();
                        return;

                    } else {
                        eventToInsert.setDay(Integer.toString(c.get(Calendar.DAY_OF_MONTH)));
                        eventToInsert.setMonth(Integer.toString(c.get(Calendar.MONTH)));
                        eventToInsert.setYear(Integer.toString(c.get(Calendar.YEAR)));

                    }

                    //HORA DE l'ESDEVENIMENT (NECESSARI)
                    String shour = hourinput.getText().toString();
                    if (shour == null || shour.equals("hh:mm h")) {
                        Toast.makeText(AddEventActivity.this, "Introdueix una hora", Toast.LENGTH_SHORT).show();
                        return;

                    } else {
                        eventToInsert.setHour(Integer.toString(c.get(Calendar.HOUR_OF_DAY)));
                        eventToInsert.setMinute(Integer.toString(c.get(Calendar.MINUTE)));
                    }


                    //SPINNER DEL NOM DE LA MASCOTA --> Si es ve d ela mascota getExtra(nom i xip) i centrar l'spinner a la mascota i bloquejarlo, sino, Extranull i yasta
                    String aux = petSpinner.getSelectedItem().toString();
                    if(aux == null || aux.equals("Selecciona una mascota..")){
                        Toast.makeText(AddEventActivity.this, "Selecciona una mascota", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    else if(pet == null){
                        String[] splitted = aux.split(" - ");
                        eventToInsert.setPetName(splitted[0]);
                        eventToInsert.setPetChip(splitted[1]);
                        for(int i = 0; i < pets.size(); i++) {
                            if(pets.get(i).getName().equals(splitted[0])) {
                                eventToInsert.setPetId(pets.get(i).getId());
                                break;
                            }
                        }
                    }
                    else{
                        String[] splitted = aux.split(" - ");
                        eventToInsert.setPetName(splitted[0]);
                        eventToInsert.setPetChip(splitted[1]);
                        eventToInsert.setPetId(pet.getId());
                    }

                    //TIPUS DE L'ESDEVENIMENT (NECESSARI)
                    if(veterniari.isChecked()) eventToInsert.setEventType(veterniari.getText().toString());
                    else if(vacunacio.isChecked()) eventToInsert.setEventType(vacunacio.getText().toString());
                    else if(desp.isChecked()) eventToInsert.setEventType(desp.getText().toString());
                    else if(altre.isChecked()){
                        Log.d("Altre radiobut", "is checked");
                        //afegir un edittext i gardar lo escrit
                    }
                    else{
                        Toast.makeText(AddEventActivity.this, "Selecciona el tipus d'esdeveniment", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //LLOC DE L'ESDEVENIMENT
                    TextInputLayout loclayout = (TextInputLayout) findViewById(R.id.input_eventloc);
                    assert loclayout != null;
                    EditText loc = loclayout.getEditText();
                    assert loc != null;
                    String ubic = loc.getText().toString();
                    if (ubic == null) ubic = "";
                    eventToInsert.setEventLocation(ubic);


                    PetDBController db = new PetDBController(AddEventActivity.this);
                    Log.d("type selected", eventToInsert.getEventType());
                    db.insertEvent(eventToInsert);
                    Toast.makeText(AddEventActivity.this, "Esdeveniment Guardat", Toast.LENGTH_SHORT).show();
                    Intent returned = new Intent();
                    returned.putExtra("id", eventToInsert.getId());
                    setResult(INSERTED, returned);
                    finish();


                }
            }
        });
    }

    private View.OnClickListener listen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.data){
                DialogFragment newFragment = new StartDatePicker();
                newFragment.show(getFragmentManager(), "datePicker");
            }
            if(v.getId() == R.id.hour){
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "TimePicker");
            }
        }
    };

    private View.OnClickListener radiolisten = new View.OnClickListener() {
        // Is the button now checked?
        @Override
        public void onClick(View view) {
            boolean checked = ((RadioButton) view).isChecked();

            // Check which radio button was clicked
            switch (view.getId()) {
                case R.id.vac:
                    if (checked) {
                        vacunacio.setChecked(true);
                        desp.setChecked(false);
                        veterniari.setChecked(false);
                        altre.setChecked(false);
                    }
                    break;
                case R.id.vet:
                    if (checked){
                        vacunacio.setChecked(false);
                        desp.setChecked(false);
                        veterniari.setChecked(true);
                        altre.setChecked(false);
                    }
                    break;
                case R.id.desp:
                    if(checked){
                        vacunacio.setChecked(false);
                        desp.setChecked(true);
                        veterniari.setChecked(false);
                        altre.setChecked(false);
                    }
                    break;
                case R.id.altre:
                    if(checked) {
                        vacunacio.setChecked(false);
                        desp.setChecked(false);
                        veterniari.setChecked(false);
                        altre.setChecked(true);
                    }
            }
        }
    };



    public static class StartDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            DatePickerDialog dialog = new DatePickerDialog(getActivity(),R.style.DialogTheme,this,startYear,startMonth,startDay);
            return dialog;

        }
        public void onDateSet(DatePicker view, int year, int month, int day) {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
            Date date = new Date(year, month, day-1);
            String dayName = simpledateformat.format(date);
            Button datatext = (Button) getActivity().findViewById(R.id.data);
            assert datatext != null;
            if(day < startDay && month < startMonth && year < startYear){
                Toast.makeText(getActivity(), "La data introduïda ja ha passat", Toast.LENGTH_SHORT).show();
            }
            else{
                String datetext = dayName + ", " + Integer.toString(day) + "/" + Integer.toString(month+1) + "/" + Integer.toString(year);
                datatext.setText(datetext);
                c.set(year, month+1, day);
            }
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new TimePickerDialog(getActivity(),R.style.DialogTheme, this, startHour, startMinute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hour, int minute) {
            Button timetext = (Button) getActivity().findViewById(R.id.hour);
            assert timetext != null;
            String h = Integer.toString(hour);
            String m = Integer.toString(minute);
            if(hour < 10) h = "0" + h;
            if(minute < 10) m = "0" + m;
            String time = h + ":" + m + " h";
            c.set(Calendar.HOUR_OF_DAY,hour);
            c.set(Calendar.MINUTE,minute);
            timetext.setText(time);
        }
    }
}