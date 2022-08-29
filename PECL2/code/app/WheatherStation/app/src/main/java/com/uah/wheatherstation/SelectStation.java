package com.uah.wheatherstation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.uah.wheatherstation.data.City;
import com.uah.wheatherstation.data.Station;
import com.uah.wheatherstation.tasks.TaskSelectStation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectStation extends AppCompatActivity {

    private String tag = "SelectStation";
    private Spinner spinnerCities;
    private Spinner spinnerStations;
    private Button buttonStation;
    private ArrayList<City> listCities;
    private ArrayList<Station> listStation;
    private final Context context;
    private int idStation = 0;
    private String nameStation = "";

    public SelectStation() {
        super();
        this.context = this;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_station);

        //Init the spinners and the button
        this.spinnerCities = this.findViewById(R.id.spinnerMacetas);
        this.spinnerStations = this.findViewById(R.id.spinnerStation);
        this.buttonStation = this.findViewById(R.id.buttonMaceta);
        //init the arraylist to incorpore the information
        this.listCities = new ArrayList<>();
        this.listStation = new ArrayList<>();

        //Add action when the spinner of the cities changes
        spinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int id = listCities.get(i).getId();//Get the id of the selected position
                Log.i(tag, "City selected:" + listCities.get(i).getName());

                //Get the list of stations of the selected city
                loadStations(listCities.get(i).getId());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //Add action when the spinner of the stations changes
        spinnerStations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    idStation = listStation.get(i).getId();//Get the id of the selected position
                    nameStation = listStation.get(i).getName();//Get the name of the selected position
                    Log.i(tag, "Station selected:" + listStation.get(i).getName());
                }catch (Exception e)
                {
                    Log.e(tag, "Error selecting the station:" + e);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        buttonStation.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectStation.this, StationActivity.class);
                i.putExtra("stationId", idStation);
                i.putExtra("stationName", nameStation);
                startActivity(i);
                finish();
            }
        });
        loadCities();
    }

    //Search the cities and fill the spinner with the information
    private void loadCities()
    {
        new TaskSelectStation(this).execute("http://192.168.1.131:8080/UbicompServerExample/GetCities");
    }

    //Search the stations of the selected city and fill the spinner with the information
    private void loadStations(final int cityId)
    {
        new TaskSelectStation(this).execute("http://192.168.1.131:8080/UbicompServerExample/GetStationsCity?cityId="+cityId);

    }

    public void setListCities(JSONArray jsonCities)
    {
        Log.e(tag,"Loading cities " + jsonCities);
        try {
            ArrayList<String> arrayCities = new ArrayList<>();
            //listStation = new ArrayList<>();
            for (int i = 0; i < jsonCities.length(); i++) {
                JSONObject jsonobject = jsonCities.getJSONObject(i);
                listCities.add(new City(jsonobject.getInt("id"), jsonobject.getString("name")));
                arrayCities.add(jsonobject.getString("name"));
            }

            spinnerCities.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayCities));
        }catch (Exception e)
        {
            Log.e(tag,"Error: " + e);
        }
    }
    public void setListStations(JSONArray jsonStations)
    {
        Log.e(tag,"Loading stations " + jsonStations);
        try {
            ArrayList<String> arrayStations = new ArrayList<>();
            //listStation = new ArrayList<>();
            for (int i = 0; i < jsonStations.length(); i++) {
                JSONObject jsonobject = jsonStations.getJSONObject(i);
                listStation.add(new Station(jsonobject.getInt("id"), jsonobject.getString("name"), Double.parseDouble(jsonobject.getString("latitude")), Double.parseDouble(jsonobject.getString("longitude"))));
                arrayStations.add(jsonobject.getString("name"));
            }
            spinnerStations.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayStations));
        }catch (Exception e)
        {
            Log.e(tag,"Error: " + e);
        }
    }
}
