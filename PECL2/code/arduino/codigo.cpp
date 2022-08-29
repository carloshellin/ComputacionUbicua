#include <DHT.h>
const int sensorHumedadSuelo = A0;       //mido la humedad al analogico 0
//DHT dht;
#define DHT11_PIN 13
#define DHT11_TYPE 11
DHT dht(DHT11_PIN, DHT11_TYPE);
const int echoPin = 9;                  //para medir distancias
const int triggerPin = 10;              //para medir distancias
const int motorBomba=6;                 //para bomba de agua
int cm = 0;
int humedadSuelo=0;
float humedadSuelo_percentage;  //humedad del suelo en porcentaje
float temp;       //temperatura en el ambiente
float humedad;    //humedad en el ambiente
int tipoPlanta=49;//puede ser de tipo 1,2 o 3 si es secano, de regadío o tropical, pasado con tabla de conversión ascii obtenemos 49, 50 y 51
int incomingByte=0;


void setup() {
  Serial.begin(9600);
  dht.begin();
  pinMode(motorBomba, OUTPUT);  
}

void loop() {
  //obtencion de datos de sensores y envío al puerto serie
  humedadSuelo = analogRead(sensorHumedadSuelo);
  humedadSuelo_percentage = ( 100 - ( (humedadSuelo/1023.00) * 100 ) );
  Serial.print("[1,\"Maceta1/Sensor3\"," );//mandamos dato humedad suelo
  Serial.print(humedadSuelo_percentage);
  Serial.println("]");
  cm = 0.01723 * leerDistancia(triggerPin, echoPin);// measure the ping time in cm
  Serial.print("[1,\"Maceta1/Sensor1\"," );//mandamos el dato profundidad depósito
  Serial.print(cm);
  Serial.println("]");
  float h=dht.readHumidity();
  float t=dht.readTemperature();
  Serial.print("[1,\"Maceta1/Sensor4\"," );//mandamos dato temperatura
  Serial.print(t);
  Serial.println("]");
  Serial.print("[1,\"Maceta1/Sensor2\"," );//mandamos datos humedad ambiente
  Serial.print(h);
  Serial.println("]");
  if(Serial.available()>0){//en caso de que haya información enviada al puerto serial(mqtt), entra en el if
    incomingByte=Serial.read(); //leemos el mensaje mandado mediante mqtt2serial por el servidor
    tipoPlanta=incomingByte;
  }
  if(tipoPlanta!=-1){ //comprueba que hay un tipo de planta establecido
    if(tipoPlanta==49){//quiere decir que es de tipo 1(secano)
      if(humedadSuelo_percentage<10){
        activarBomba(800); 
      }
      //activarBomba();
      //Serial.println("Planta de secano");
    }
    if(tipoPlanta==50){//quiere decir que es de tipo 2 (regadío)
      if(humedadSuelo_percentage<30){
        activarBomba(800); 
      }
    }
    if(tipoPlanta==51){//quiere decir que es de tipo 3 (tropical)
      if(humedadSuelo_percentage<55){
        activarBomba(800); //hacemos pequeños riegos de poco tiempo para no inundar la planta
      }
    }
  }  
  delay(20000);
  /*
  lcd.setCursor(0, 0);//poner el cursor en columna 0, linea 0 código desechado por la inutilización de la pantalla LCD
  lcd.setCursor(0, 1);//poner el cursor en columna 0, linea 1
  lcd.print("Distancia:");
  lcd.print(cm);
  lcd.print("cm");
  */
  
}

long leerDistancia(int triggerPin, int echoPin)//función para realizar la lectura de distancia por parte del sensor
{
  pinMode(triggerPin, OUTPUT);  // Clear the trigger
  digitalWrite(triggerPin, LOW);
  delayMicroseconds(2);
  // Sets the trigger pin to HIGH state for 10 microseconds
  digitalWrite(triggerPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(triggerPin, LOW);
  pinMode(echoPin, INPUT);
  // Reads the echo pin, and returns the sound wave travel time in microseconds
  return pulseIn(echoPin, HIGH);
}
void activarBomba(int tiempo)//función para activar la bomba tanto tiempo como se introduzca como parámetro
{
  digitalWrite(motorBomba, 50);
  delay(tiempo);
  digitalWrite(motorBomba, 0);
  
}
