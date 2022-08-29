package com.uah.wheatherstation;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.uah.wheatherstation.data.SensorType;
import com.uah.wheatherstation.tasks.TaskStationValues;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class StationActivity extends AppCompatActivity {

    private MqttAndroidClient client;
    private final static String CHANNEL_ID = "stationId";
    private final static int NOTIFICATION_ID=0;
    private String station = "";
    private int idStation = 0;
    private String tag = "StationActivity";
    private TextView nameStation;
    private TextView valueSensors;

    public StationActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameStation = (TextView) findViewById(R.id.descripcion);
        valueSensors = (TextView) findViewById(R.id.sensores);

        //Get the value of the station id from the previous activity
        station = getIntent().getStringExtra("stationId");
        idStation = Integer.parseInt(station);
        Log.i(tag, "Station =" + idStation);
        nameStation.setText(getIntent().getStringExtra("stationName"));

        //Get values of sensors
        new TaskStationValues(this).execute("http://192.168.1.131:8080/UbicompServerExample/GetStationSensors?stationId="+idStation);

        //get values of sensors from the server
        //TODO crear asynctask para enviar los valores de los sensores de la estación mostrada

        //Configure the mqtt client who will send the notifications about the selected station
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), "tcp://192.168.1.131:1883", clientId);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    //If the connection is ok
                    Log.i(tag, "MQTT connected");
                    //Suscribe the topics
                    suscripcionTopics(station);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.i(tag, "Error connecting MQTT");
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

        //Callback of MQTT to process the information received by MQTT
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
            }
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception
            {
                //New alert from the wheater station
                if(topic.contains("alert"))
                {
                    String mqttText = new String(message.getPayload());
                    Log.i(tag, "New Alert: + " + (new String(message.getPayload())));

                    //Create a notification with the alert
                    createNotificationChannel();
                    createNotification("Alert", mqttText);
                }else
                {
                    //The message is about a sensor type
                    String mqttText = new String(message.getPayload());
                    Log.i(tag, "New Message: + " + (new String(message.getPayload())));

                    //TODO Modificar el valor del sensor en la pantalla
                }
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });
    }

    //Method to print the sensors values:
    public void printValues(JSONArray jsonStations)
    {
        String values = "";
        try {
            ArrayList<SensorType> arraySensors = new ArrayList<>();
            for (int i = 0; i < jsonStations.length(); i++) {
                JSONObject jsonobject = jsonStations.getJSONObject(i);
                SensorType sensor = new SensorType();
                sensor.setId(jsonobject.getInt("id"));
                sensor.setName(jsonobject.getString("name"));
                sensor.setUnits(jsonobject.getString("units"));
                sensor.setValue(jsonobject.getInt("value"));
                arraySensors.add(sensor);
                values = values + sensor.getName() + " = " + sensor.getValue() + " " + sensor.getUnits() +"\n";
            }
            valueSensors.setText(values);
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

