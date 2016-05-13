package com.example.iansangines.appnimals;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEventActivity extends AppCompatActivity {

    static final int INSERTED = 1;
    View.OnClickListener listener;
    static Calendar c = Calendar.getInstance();
    static int startYear = c.get(Calendar.YEAR);
    static int startMonth = c.get(Calendar.MONTH);
    static int startDay = c.get(Calendar.DAY_OF_MONTH);

    private Event eventToInsert = new Event();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarevent);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("");

        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.confevent);
        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.confevent){

                        TextInputLayout eventnamelayout = (TextInputLayout) findViewById(R.id.input_eventname);
                        assert eventnamelayout != null;
                        EditText eventname = eventnamelayout.getEditText();
                        assert eventname != null;
                        String name = eventname.getText().toString();
                        if (name == null || name.equals("")) {
                            AlertDialog.Builder nameDialog = new AlertDialog.Builder(AddEventActivity.this);
                            nameDialog.setMessage("Introdueix un nom").setNeutralButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                            return;
                        } else {
                            eventToInsert.setName(name);
                        }

                        TextView dateinput = (TextView) findViewById(R.id.data);
                        assert dateinput != null;
                        String data = dateinput.getText().toString();
                        if (data == null || data.equals("")) {

                            AlertDialog.Builder dateDialog = new AlertDialog.Builder(AddEventActivity.this);
                            dateDialog.setMessage("Introdueix una data").setNeutralButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                            return;

                        } else {
                            eventToInsert.setDate(data);
                        }

                    TextView hourinput = (TextView) findViewById(R.id.data);
                    assert hourinput != null;
                    String hour = hourinput.getText().toString();
                    if (hour == null || hour.equals("")) {

                        AlertDialog.Builder dateDialog = new AlertDialog.Builder(AddEventActivity.this);
                        dateDialog.setMessage("Introdueix una hora").setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                        return;

                    } else {
                        eventToInsert.setHour(hour);
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

                        //TRACTAR ELS RADIOBUTTONS
                        /*
                        TextInputLayout subtypeinputlayout = (TextInputLayout) findViewById(R.id.layout_input_rasa);
                        assert subtypeinputlayout != null;
                        EditText subtypeinput = subtypeinputlayout.getEditText();
                        assert subtypeinput != null;
                        String subtype = subtypeinput.getText().toString();
                        if (subtype == null || subtype.equals("")) {

                            AlertDialog.Builder typeDialog = new AlertDialog.Builder(InsertPetActivity.this);
                            typeDialog.setMessage("Introdueix una raça/tipus").setNeutralButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                            return;
                        } else {
                            petToInsert.setPetType(subtype);
                        } */

                        TextInputLayout loclayout = (TextInputLayout) findViewById(R.id.input_eventloc);
                        assert loclayout != null;
                        EditText loc = eventnamelayout.getEditText();
                        assert loc != null;
                        String ubic = eventname.getText().toString();
                        if(ubic == null) ubic = "";
                            eventToInsert.setEventLocation(ubic);

                        TextInputLayout desclayout = (TextInputLayout) findViewById(R.id.input_eventdesc);
                        assert desclayout != null;
                        EditText desc = eventnamelayout.getEditText();
                        assert desc != null;
                        String descString = eventname.getText().toString();
                        if(descString == null) descString = "";
                            eventToInsert.setEventDescription(descString);



                        PetDBController db = new PetDBController(AddEventActivity.this);
                        db.insertEvent(eventToInsert.getName(),eventToInsert.getDate(),null,eventToInsert.getHour(),null,eventToInsert.getEventLocation(),eventToInsert.getEventDescription());
                        Toast.makeText(AddEventActivity.this, "Esdeveniment Guardat", Toast.LENGTH_SHORT).show();
                        //Retorna el numero de xip per fer query al petlistactivity
                        Intent returned = new Intent();
                        returned.putExtra("date",eventToInsert.getDate());
                        returned.putExtra("hour", eventToInsert.getHour());
                        setResult(INSERTED, returned);
                        finish();


                    }
                }
            });

        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.data:
                        DialogFragment dialogFragment = new StartDatePicker();
                        dialogFragment.show(getFragmentManager(), "start_date_picker");
                }
            }
        };



    }

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
            TextView datatext = (TextView) getActivity().findViewById(R.id.data);
            assert datatext != null;
            String datetext = dayName + ", " + Integer.toString(day) + "/" + Integer.toString(month+1) + "/" + Integer.toString(year);
            datatext.setText(datetext);
        }
    }

}