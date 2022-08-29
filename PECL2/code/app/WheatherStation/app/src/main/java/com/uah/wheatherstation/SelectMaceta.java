package com.uah.wheatherstation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.uah.wheatherstation.data.City;
import com.uah.wheatherstation.data.Maceta;
import com.uah.wheatherstation.data.Station;
import com.uah.wheatherstation.tasks.TaskSelectMaceta;
import com.uah.wheatherstation.tasks.TaskSelectStation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectMaceta extends AppCompatActivity {

    private String tag = "SelectMaceta";
    private Spinner spinnerMacetas;
    private Button buttonMaceta;
    private ArrayList<Maceta> listMacetas;
    private final Context context;
    private int usuario_id = 0;
    private int maceta_id = 0;
    private String descripcion = "";

    public SelectMaceta() {
        super();
        this.context = this;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_maceta);

        //Init the spinners and the button
        this.spinnerMacetas = this.findViewById(R.id.spinnerMacetas);
        this.buttonMaceta = this.findViewById(R.id.buttonMaceta);
        //init the arraylist to incorpore the information
        this.listMacetas = new ArrayList<>();

        usuario_id = getIntent().getIntExtra("usuario_id", 0);

        //Add action when the spinner of the stations changes
        spinnerMacetas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    maceta_id = listMacetas.get(i).getId();//Get the id of the selected position
                    descripcion = listMacetas.get(i).getDescripcion();
                    Log.i(tag, "Maceta selected:" + descripcion);
                }catch (Exception e)
                {
                    Log.e(tag, "Error selecting the maceta:" + e);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        buttonMaceta.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectMaceta.this, MacetaActivity.class);
                i.putExtra("maceta_id", maceta_id);
                i.putExtra("usuario_id", usuario_id);
                i.putExtra("descripcion", descripcion);
                startActivity(i);
            }
        });
        loadMacetas();
    }

    //Search the cities and fill the spinner with the information
    private void loadMacetas()
    {
        new TaskSelectMaceta(this).execute("http://uah-plantas.tech/GetMacetasUsuario?usuario_id="+usuario_id);
    }

    public void setListMacetas(JSONArray jsonMacetas)
    {
        Log.e(tag,"Loading macetas " + jsonMacetas);
        try {
            ArrayList<String> arrayMacetas = new ArrayList<>();
            for (int i = 0; i < jsonMacetas.length(); i++) {
                JSONObject jsonobject = jsonMacetas.getJSONObject(i);
                listMacetas.add(new Maceta(jsonobject.getInt("id"), jsonobject.getString("descripcion"), jsonobject.getInt("usuario_id"), jsonobject.getInt("tipo_id")));
                arrayMacetas.add(jsonobject.getString("descripcion"));
            }
            spinnerMacetas.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayMacetas));
        }catch (Exception e)
        {
            Log.e(tag,"Error: " + e);
        }
    }
}
