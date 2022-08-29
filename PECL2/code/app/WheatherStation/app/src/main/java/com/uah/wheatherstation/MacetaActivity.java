package com.uah.wheatherstation;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.uah.wheatherstation.data.Maceta;
import com.uah.wheatherstation.data.Medida;
import com.uah.wheatherstation.data.Sensor;
import com.uah.wheatherstation.data.SensorType;
import com.uah.wheatherstation.tasks.TaskSensorValores;
import com.uah.wheatherstation.tasks.TaskSensores;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;


public class MacetaActivity extends AppCompatActivity {

    private MqttAndroidClient client;
    private final static String CHANNEL_ID = "stationId";
    private final static int NOTIFICATION_ID=0;
    private String station = "";
    private int maceta_id = 0;
    private String tag = "MacetaActivity";
    private TextView descripcion;
    private TextView sensores;
    private ArrayList<Sensor> listSensores;
    private ArrayList<Medida> listMedidas;

    public MacetaActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        descripcion = (TextView) findViewById(R.id.descripcion);
        sensores = (TextView) findViewById(R.id.sensores);
        //init the arraylist to incorpore the information
        this.listSensores = new ArrayList<>();

        //Get the value of the station id from the previous activity
        maceta_id = getIntent().getIntExtra("maceta_id", 0);
        Log.i(tag, "Maceta =" + maceta_id);
        descripcion.setText(getIntent().getStringExtra("descripcion"));

        // Get sensorss
        new TaskSensores(this).execute("http://uah-plantas.tech/GetSensoresMaceta?maceta_id="+maceta_id);
    }

    public void setListSensores(JSONArray jsonSensores)
    {
        Log.e(tag,"Loading sensores " + jsonSensores);
        try {
            for (int i = 0; i < jsonSensores.length(); i++) {
                JSONObject jsonobject = jsonSensores.getJSONObject(i);
                listSensores.add(new Sensor(jsonobject.getInt("id"), jsonobject.getString("descripcion"), jsonobject.getInt("minValue"), jsonobject.getInt("maxValue"), jsonobject.getString("unidad"), jsonobject.getInt("maceta_id")));

                //Get values of sensor
                new TaskSensorValores(this).execute("http://uah-plantas.tech/GetMedidasMaceta?sensor_id="+jsonobject.getInt("id"));
            }
        }catch (Exception e)
        {
            Log.e(tag,"Error: " + e);
        }
    }

    public void printValues(JSONArray jsonMedidas)
    {
        String values = "";
        try {
            JSONObject jsonobject = jsonMedidas.getJSONObject(jsonMedidas.length() - 1);
            for (int i = 0; i < listSensores.size(); i++) {
                Sensor sensor = listSensores.get(i);

                if (sensor.getId() == jsonobject.getInt("sensor_id"))
                {
                    values = values + sensor.getDescripcion() + " = " + jsonobject.getDouble("valor") + " " + sensor.getUnidad() +"\n";
                    break;
                }
            }
            sensores.setText(sensores.getText().toString() + values);
        }catch (Exception e)
        {
            Log.e(tag,"error parsing JSON= " +e);
        }
    }

    //Method to create the notification channel in new versions
    private void createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            CharSequence name = "Notificación";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    //Method to create a notfication with the title and the message
    private void createNotification(String title, String msn)
    {
        //Configure the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        builder.setSmallIcon(R.drawable.logo);
        builder.setContentTitle(title);
        builder.setContentText(msn);
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.BLUE, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        //Show the notification
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    //MQTT topics to suscribe the application
    private void suscripcionTopics(String station){
        try{
            Log.i(tag, "station = " + station);
            //TODO suscribir a todos los topics de los sensores
            client.subscribe(station,0);
            client.subscribe(station + "/alert",0);
            client.subscribe(station + "/temperature",0);

        }catch (MqttException e){
            e.printStackTrace();
        }
    }

    //Publish a new MQTT message in a topic
    private void publish(String topic, String payload)
    {
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            Log.e(tag, "Error mqtt "+ e);
        }
    }
}

