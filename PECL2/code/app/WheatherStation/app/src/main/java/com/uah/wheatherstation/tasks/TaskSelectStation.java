package com.uah.wheatherstation.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.uah.wheatherstation.SelectStation;
import com.uah.wheatherstation.data.City;
import com.uah.wheatherstation.data.Station;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Task to connect with the API to request the list of cities and stations
 */
public class TaskSelectStation extends AsyncTask<String,Void, String>
{
    private String tag = "TaskSelectStation";
    private SelectStation activity;
    private String urlStr = "";

    public TaskSelectStation(SelectStation activity)
    {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... uri) {
        String response = "";
        this.urlStr = uri[0];
        try {
            URL url = new URL(uri[0]);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            //Get the information from the url
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = convertStreamToString(in);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }
    //When the task is complete this method will be called to change the values in the UI
    protected void onPostExecute(String response)
    {
        super.onPostExecute(response);
        try
        {
            Log.d(tag, "get json: " + response);
            JSONArray jsonarray = new JSONArray(response);
            //Read Responses and fill the spinner
            if(urlStr.contains("GetCities"))
            {
                activity.setListCities(jsonarray);
            }else
            {
                if (urlStr.contains("GetStationsCity"))
                {
                    activity.setListStations(jsonarray);
                }
            }
        }catch (Exception e)
        {
            Log.e(tag, "Error on postExecute:" + e);
        }
    }

    //GEt the input strean and convert into String
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}