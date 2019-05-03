#include <SPI.h>
#include <Ethernet.h>
#include <PubSubClient.h>
#include <Servo.h>
 
#define SERVO 6

// Update these with values suitable for your network.
byte mac[]    = {  0xDE, 0xED, 0xBA, 0xFE, 0xFE, 0xED };
IPAddress ip(192,168,0,170);

IPAddress server(54,171,53,113); // IP DO BROKER
int server_port =  13398; // PORTA DO BROKER

 
Servo s; // Variável Servo
int pos; // Posição Servo

int rele9 = 5;
int rele10 = 9;

String a;
String b;


String luz(byte* payload, unsigned long length){
  a = "";
  for(int i=0;i<length;i++){
    a += ((char)payload[i]);
  }
  if(a == "on"){
    return "1";
  } else if (a == "off"){
     return "0";
  } else{
    return "state";
  }
}

String motor(byte* payload, unsigned long length){
  b = "";
  for(int i=0;i<length;i++){
    b += ((char)payload[i]);
  }
  if(b == "dir"){
    return "1";
  } else if (b == "esq"){
     return "0";
  } else{
    return "state";
  }
}

void callback(char* topic, byte* payload, unsigned int length) {
      Serial.print(topic);
  if (strcmp(topic, "LUZ") == 0){
     if(luz(payload, length).equals("1")== true){
      digitalWrite(5, HIGH);
      delay(1000);
    }
   if(luz(payload, length).equals("0") == true){
      digitalWrite(5, LOW);
      delay(1000);
   }
  }
  if (strcmp(topic, "MOTOR") == 0){
     if(motor(payload, length).equals("1")== true){
        for(pos = 180; pos >= 0; pos--){
        s.write(pos);
        }
    }
   if(motor(payload, length).equals("0") == true){
    for(pos = 0; pos < 180; pos++){
          s.write(pos);
        }
   }
  }
}

void girarDireita(){

}
EthernetClient ethClient;
PubSubClient client(server, server_port, callback, ethClient);

void setup()
{
  pinMode(rele9, OUTPUT);
  pinMode(rele10, OUTPUT);
  Ethernet.begin(mac, ip);
  // Note - the default maximum packet size is 128 bytes. If the
  // combined length of clientId, username and password exceed this,
  // you will need to increase the value of MQTT_MAX_PACKET_SIZE in
  // PubSubClient.h
  s.attach(SERVO);
  s.write(0); // Inicia motor posição zero
  Serial.begin(9600);
  
}

void mypombs(){
  if (client.connect("33398", "hxdzapjd", "bCtuyFO3kKn5")) {
    client.publish("fromArduino","Topico inicial. Arduino conectado");
    
    Serial.print("Conectado");
  }
}

void loop()
{
  
  client.loop();
  if(client.connected()!=true){
    mypombs();
  }
  client.subscribe("#");
}
