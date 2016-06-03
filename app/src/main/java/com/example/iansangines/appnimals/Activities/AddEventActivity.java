package com.example.iansangines.appnimals.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.iansangines.appnimals.Controllers.PetDBController;
import com.example.iansangines.appnimals.Domain.Event;
import com.example.iansangines.appnimals.Domain.Pet;
import com.example.iansangines.appnimals.R;

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
    private TextInputLayout otherType;
    private TextInputLayout loclayout;
    private TextInputLayout eventnamelayout;
    private ArrayList<Pet> pets = null;
    private Pet pet = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarevent);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Afegir Esdeveniment");

        vacunacio = (RadioButton) findViewById(R.id.vac);
        assert vacunacio != null;
        vacunacio.setOnCheckedChangeListener(radiolisten);

        veterniari = (RadioButton) findViewById(R.id.vet);
        assert veterniari != null;
        veterniari.setOnCheckedChangeListener(radiolisten);

        desp = (RadioButton) findViewById(R.id.desp);
        assert desp != null;
        desp.setOnCheckedChangeListener(radiolisten);

        altre = (RadioButton) findViewById(R.id.altre);
        assert altre != null;
        altre.setOnCheckedChangeListener(radiolisten);

        dateinput = (Button) findViewById(R.id.data);
        assert dateinput != null;
        dateinput.setOnClickListener(listen);

        hourinput = (Button) findViewById(R.id.hour);
        assert hourinput != null;
        hourinput.setOnClickListener(listen);

        otherType = (TextInputLayout) findViewById(R.id.input_eventtype);

        petSpinner = (Spinner) findViewById(R.id.petspinner);

        loclayout = (TextInputLayout) findViewById(R.id.input_eventloc);

        eventnamelayout = (TextInputLayout) findViewById(R.id.input_eventname);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        db = new PetDBController(AddEventActivity.this);

        if (getIntent().getExtras() == null) {
            spinnerAdapter.add("Selecciona una mascota..");
            pets = db.queryAllPets();
            for (int i = 0; i < pets.size(); i++) {
                Pet aux = pets.get(i);
                String name = aux.getName();
                String chip = aux.getChipNumber();
                String spin = name + " - " + chip;
                spinnerAdapter.add(spin);
            }
        } else {
            int id = getIntent().getIntExtra("petId",-1);
            if(id != -1) {
                pet = db.queryPetById(getIntent().getIntExtra("petId", -1));
                String spin = pet.getName() + " - " + pet.getChipNumber();
                spinnerAdapter.add(spin);
            }
            else{
                id = getIntent().getIntExtra("eventId",-1);
                eventToInsert = db.queryEvent(id);
                getSupportActionBar().setTitle("Editar Esdeveniment");
                eventnamelayout.getEditText().setText(eventToInsert.getName());
                Date date = new Date(Integer.parseInt(eventToInsert.getYear()), Integer.parseInt(eventToInsert.getMonth()), Integer.parseInt(eventToInsert.getDay()) - 1);
                SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                String dayName = simpledateformat.format(date);
                String datetext = dayName + ", " + Integer.toString(date.getDay()) + "/" + Integer.toString(date.getMonth() -1 ) + "/" + Integer.toString(date.getYear());
                dateinput.setText(datetext);
                String hour = eventToInsert.getHour() + ":" + eventToInsert.getMinute();
                hourinput.setText(hour);
                switch(eventToInsert.getEventType()){
                    case("Veterinar"):
                        veterniari.setChecked(true);
                        break;
                    case("Vacunació"):
                        vacunacio.setChecked(true);
                        break;
                    case("Desparacitació"):
                        desp.setChecked(true);
                        break;
                    default:
                        altre.setChecked(true);
                        otherType.setVisibility(View.VISIBLE);
                        otherType.getEditText().setText(eventToInsert.getEventType());
                        break;
                }
            }
            String spin = eventToInsert.getPetName() + " - " + eventToInsert.getPetChip();
            spinnerAdapter.add(spin);
            loclayout.getEditText().setText(eventToInsert.getEventLocation());
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
                    if (aux == null || aux.equals("Selecciona una mascota..")) {
                        Toast.makeText(AddEventActivity.this, "Selecciona una mascota", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (pet == null) {
                        String[] splitted = aux.split(" - ");
                        eventToInsert.setPetName(splitted[0]);
                        eventToInsert.setPetChip(splitted[1]);
                        for (int i = 0; i < pets.size(); i++) {
                            if (pets.get(i).getName().equals(splitted[0])) {
                                eventToInsert.setPetId(pets.get(i).getId());
                                break;
                            }
                        }
                    } else {
                        String[] splitted = aux.split(" - ");
                        eventToInsert.setPetName(splitted[0]);
                        eventToInsert.setPetChip(splitted[1]);
                        eventToInsert.setPetId(pet.getId());
                    }

                    //TIPUS DE L'ESDEVENIMENT (NECESSARI)
                    assert otherType.getEditText() != null;
                    if (otherType.getEditText().getVisibility() == View.VISIBLE){
                        String eventType = otherType.getEditText().getText().toString();
                        if(eventType == null || eventType.equals("")){
                            Toast.makeText(AddEventActivity.this, "Selecciona el tipus d'esdeveniment", Toast.LENGTH_SHORT).show();
                        }
                        else eventToInsert.setEventType(eventType);
                    }

                    //LLOC DE L'ESDEVENIMENT
                    assert loclayout != null;
                    EditText loc = loclayout.getEditText();
                    assert loc != null;
                    String ubic = loc.getText().toString();
                    if (ubic == null) ubic = "";
                    eventToInsert.setEventLocation(ubic);


                    PetDBController db = new PetDBController(AddEventActivity.this);
                    if(getIntent().getIntExtra("eventId",-1) != -1)
                    db.insertEvent(eventToInsert);
                    else{
                        db.updateEvent(eventToInsert);
                    }
                    Toast.makeText(AddEventActivity.this, "Esdeveniment Guardat", Toast.LENGTH_SHORT).show();
                    Intent returned = new Intent();
                    setResult(INSERTED, returned);
                    finish();


                }
            }
        });
    }

    private View.OnClickListener listen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.data) {
                DialogFragment newFragment = new StartDatePicker();
                newFragment.show(getFragmentManager(), "datePicker");
            }
            if (v.getId() == R.id.hour) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "TimePicker");
            }
        }
    };

    private CompoundButton.OnCheckedChangeListener radiolisten = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            switch (buttonView.getId()) {
                case R.id.vac:
                    if (isChecked) {
                        vacunacio.setChecked(true);
                        desp.setChecked(false);
                        veterniari.setChecked(false);
                        altre.setChecked(false);
                        eventToInsert.setEventType("Vacunació");
                        otherType.setVisibility(View.GONE);
                    }
                    break;
                case R.id.vet:
                    if (isChecked) {
                        vacunacio.setChecked(false);
                        desp.setChecked(false);
                        veterniari.setChecked(true);
                        altre.setChecked(false);
                        eventToInsert.setEventType("Veterinari");
                        otherType.setVisibility(View.GONE);
                    }
                    break;
                case R.id.desp:
                    if (isChecked) {
                        vacunacio.setChecked(false);
                        desp.setChecked(true);
                        veterniari.setChecked(false);
                        altre.setChecked(false);
                        eventToInsert.setEventType("Desparacitació");
                        otherType.setVisibility(View.GONE);
                    }
                    break;
                case R.id.altre:
                    if (isChecked) {
                        Toast.makeText(AddEventActivity.this, "S'ha clickat altre", Toast.LENGTH_SHORT).show();
                        vacunacio.setChecked(false);
                        desp.setChecked(false);
                        veterniari.setChecked(false);
                        altre.setChecked(true);
                        otherType.setVisibility(View.VISIBLE);
                    }
            }
        }
    };


    public static class StartDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, this, startYear, startMonth, startDay);
            return dialog;

        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
            Date date = new Date(year, month, day - 1);
            String dayName = simpledateformat.format(date);
            Button datatext = (Button) getActivity().findViewById(R.id.data);
            assert datatext != null;
            if (day < startDay && month < startMonth && year < startYear) {
                Toast.makeText(getActivity(), "La data introduïda ja ha passat", Toast.LENGTH_SHORT).show();
            } else {
                String datetext = dayName + ", " + Integer.toString(day) + "/" + Integer.toString(month + 1) + "/" + Integer.toString(year);
                datatext.setText(datetext);
                c.set(year, month + 1, day);
            }
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new TimePickerDialog(getActivity(), R.style.DialogTheme, this, startHour, startMinute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hour, int minute) {
            Button timetext = (Button) getActivity().findViewById(R.id.hour);
            assert timetext != null;
            String h = Integer.toString(hour);
            String m = Integer.toString(minute);
            if (hour < 10) h = "0" + h;
            if (minute < 10) m = "0" + m;
            String time = h + ":" + m + " h";
            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, minute);
            timetext.setText(time);
        }
    }
}