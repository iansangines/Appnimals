package com.example.iansangines.appnimals;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.LinearLayout;
import android.widget.Toast;


public class InsertPetActivity extends AppCompatActivity {
    private boolean clicked = false;

    private ArrayAdapter<CharSequence> adapter;
    Pet petToInsert = new Pet();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_pet);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner petTypes = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.especies,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assert petTypes != null;
        petTypes.setAdapter(adapter);
        petTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                petToInsert.setPetType((String) parent.getSelectedItem());
                if (position > 0 && position < adapter.getCount())
                    if (!clicked) {
                        clicked = true;
                        TextInputLayout subtypeinput = (TextInputLayout) findViewById(R.id.layout_input_rasa);
                        assert subtypeinput != null;
                        subtypeinput.setVisibility(view.VISIBLE);
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button button = (Button) findViewById(R.id.buttonafegir);
        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.buttonafegir){
                    TextInputLayout nameinputlayout = (TextInputLayout) findViewById(R.id.layout_input_nom);
                    assert nameinputlayout != null;
                    EditText nameinput = nameinputlayout.getEditText();
                    assert nameinput != null;
                    String name = nameinput.getText().toString();
                    if(name == null || name.equals("")){
                        //ALERTDIALOG: no has posat nom
                        return;
                    }
                    else{
                        petToInsert.setName(name);
                    }

                    TextInputLayout datainputlayout = (TextInputLayout) findViewById(R.id.layout_input_date);
                    assert datainputlayout != null;
                    EditText datainput = datainputlayout.getEditText();
                    assert datainput != null;
                    String data = datainput.getText().toString();
                    if(data == null || data.equals("")){
                        //ALERTDIALOG: no has posat nom
                        return;
                    }
                    else{
                        petToInsert.setBornDate(data);
                    }

                    TextInputLayout xipinputlayout = (TextInputLayout) findViewById(R.id.layout_input_xip);
                    assert xipinputlayout != null;
                    EditText xipinput = xipinputlayout.getEditText();
                    assert xipinput != null;
                    String xip = xipinput.getText().toString();
                    if(xip == null || xip.equals("")){
                        //ALERTDIALOG: no has posat nom
                        return;
                    }
                    else{
                        petToInsert.setChipNumber(xip);
                    }

                    TextInputLayout subtypeinputlayout = (TextInputLayout) findViewById(R.id.layout_input_rasa);
                    assert subtypeinputlayout != null;
                    EditText subtypeinput = subtypeinputlayout.getEditText();
                    assert subtypeinput != null;
                    String subtype = subtypeinput.getText().toString();
                    if(subtype == null || xip.equals("")){
                        //ALERTDIALOG: no has posat nom
                        return;
                    }
                    else{
                        petToInsert.setChipNumber(xip);
                    }
                    PetDBController db = new PetDBController(InsertPetActivity.this);
                    db.insertPet(petToInsert.getName(),petToInsert.getBornDate(),petToInsert.getPetType(),petToInsert.getPetSubtype(),petToInsert.getChipNumber(),0);
                    Toast.makeText(InsertPetActivity.this,"Mascota guardada",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
