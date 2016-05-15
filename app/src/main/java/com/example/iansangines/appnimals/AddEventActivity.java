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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
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

    private RadioButton vacunacio;
    private RadioButton veterniari;
    private RadioButton desp;
    private RadioButton altre;

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
                        /*
                        TextInputLayout xipinputlayout = (TextInputLayout) findViewById(R.id.layout_input_xip);
                        assert xipinputlayout != null;
                        EditText xipinput = xipinputlayout.getEditText();
                        assert xipinput != null;
                        String xip = (xipinput.getText().toString());
                        if (xip == null || xip.equals("")) {
                            AlertDialog.Builder xipDialog = new AlertDialog.Builder(InsertPetActivity.this);
                            xipDialog.setMessage("Introdueix una número de xip").setNeutralButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                            return;
                        } else {
                            petToInsert.setChipNumber(xip);
                        }
                        */


                    //TIPUS DE L'ESDEVENIMENT (NECESSARI)
                    if(veterniari.isChecked()) eventToInsert.setEventType(veterniari.getText().toString());
                    else if(vacunacio.isChecked()) eventToInsert.setEventType(vacunacio.getText().toString());
                    else if(desp.isChecked()) eventToInsert.setEventType(desp.getText().toString());
                    else if(altre.isChecked()){
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

                    //DESCRIPCIÓ DE L'ESDEVENIMENT
                    TextInputLayout desclayout = (TextInputLayout) findViewById(R.id.input_eventdesc);
                    assert desclayout != null;
                    EditText desc = desclayout.getEditText();
                    assert desc != null;
                    String descString = desc.getText().toString();
                    if (descString == null) descString = "";
                    eventToInsert.setEventDescription(descString);


                    PetDBController db = new PetDBController(AddEventActivity.this);
                    db.insertEvent(eventToInsert.getName(), eventToInsert.getDay(), eventToInsert.getMonth(), eventToInsert.getYear(), null, eventToInsert.getHour(), eventToInsert.getMinute(), null, eventToInsert.getEventLocation(), eventToInsert.getEventDescription());
                    Toast.makeText(AddEventActivity.this, "Esdeveniment Guardat", Toast.LENGTH_SHORT).show();
                    //Retorna el numero de xip per fer query al petlistactivity
                    Intent returned = new Intent();
                    returned.putExtra("day", eventToInsert.getDay());
                    returned.putExtra("hour", eventToInsert.getHour());
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