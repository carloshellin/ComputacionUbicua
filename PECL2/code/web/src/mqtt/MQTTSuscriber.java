package mqtt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import db.ConectionDDBB;
import db.Topics;
import logic.Log;
import logic.Logic;

public class MQTTSuscriber implements MqttCallback
{
	private static String userName = "ubicua";
	private static String password = "plantas2020";
	
	public void searchTopicsToSuscribe(MQTTBroker broker)
	{
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		ArrayList<String> topics = new ArrayList<>();
		
		try
		{
			con = conector.obtainConnection(true);
			Log.logmqtt.debug("Database Connected");
			
			//Obtener macetas para buscar el topic principal
			PreparedStatement psMacetas = ConectionDDBB.GetMacetas(con);
			Log.logmqtt.debug("Query to search macetas=> {}", psMacetas.toString());
			ResultSet rsMacetas = psMacetas.executeQuery();
			while (rsMacetas.next())
			{
				String topicMaceta = "Maceta" + rsMacetas.getInt("ID");
				topics.add("Maceta" + rsMacetas.getInt("ID"));
				
				// Obtener sensores de la maceta
				PreparedStatement psSensores = ConectionDDBB.GetSensoresFromMaceta(con);
				psSensores.setInt(1, rsMacetas.getInt("ID"));
				Log.logmqtt.debug("Query to search sensores=> {}", psSensores.toString());
				ResultSet rsSensores = psSensores.executeQuery();
				while (rsSensores.next()){
					String topicSensor = topicMaceta + "/Sensor" + rsSensores.getInt("ID");
					topics.add(topicSensor);
				}
			}
		suscribeTopic(broker, topics);			
		}
		catch (SQLException e)
		{
			Log.logmqtt.error("Error: {}", e);
		} 
		catch (NullPointerException e)
		{
			Log.logmqtt.error("Error: {}", e);
		} 
		catch (Exception e)
		{
			Log.logmqtt.error("Error:{}", e);
		} 
		finally
		{
			conector.closeConnection(con);
		}
	}
	
	public void suscribeTopic(MQTTBroker broker, ArrayList<String> topics)
	{
		Log.logmqtt.debug("Suscribe to topics");
        MemoryPersistence persistence = new MemoryPersistence();
        try
        {
            MqttClient sampleClient = new MqttClient(MQTTBroker.getBroker(), MQTTBroker.getClientId(), persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
		    connOpts.setUserName(userName);
		    connOpts.setPassword(password.toCharArray());
            connOpts.setCleanSession(true);
            Log.logmqtt.debug("Mqtt Connecting to broker: " + MQTTBroker.getBroker());
            sampleClient.connect(connOpts);
            Log.logmqtt.debug("Mqtt Connected");
            sampleClient.setCallback(this);
            for (int i = 0; i <topics.size(); i++) 
            {
                sampleClient.subscribe(topics.get(i));
                Log.logmqtt.info("Subscribed to {}", topics.get(i));
			}
            
        } catch (MqttException me) {
            Log.logmqtt.error("Error suscribing topic: {}", me);
        } catch (Exception e) {
            Log.logmqtt.error("Error suscribing topic: {}", e);
        }
	}
	
	@Override
	public void connectionLost(Throwable cause) 
	{	
	}
	
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception 
	{
       Log.logmqtt.info("{}: {}", topic, message.toString());
       String[] topics = topic.split("/");
       Topics newTopic = new Topics();
       newTopic.setValue(message.toString());
       if(topic.contains("Sensor"))
       {
		   newTopic.setIdMaceta(topics[0].replace("Maceta", ""));
		   newTopic.setIdSensor(topics[1].replace("Sensor", ""));
    	   Log.logmqtt.info("Mensaje from maceta{} sensor{}: {}", 
    			   newTopic.getIdMaceta(), newTopic.getIdSensor(), message.toString());
    	   
    	   // Almacena la información del sensor
    	   Logic.storeNewMedida(newTopic);
       }
       else if (topic.contains("Maceta"))
       {
		   newTopic.setIdMaceta(topics[0].replace("Maceta", ""));
		   Log.logmqtt.info("Mensaje from maceta{}: {}", 
				   newTopic.getIdMaceta(), message.toString());
	   }
       else
	   {
		   
	   }
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) 
	{		
	}
}

