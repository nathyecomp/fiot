/*
UDPClient.pde - UDP Client Arduino processing sketch
 
 Copyright (C) 2012 DIYSandbox LLC
 
 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License
 as published by the Free Software Foundation; either version 2
 of the License, or (at your option) any later version.
 
 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

#include <Wirefree.h>
#include <WifiClient.h>

//rede compartilhada pelo meu note
WIFI_PROFILE wireless_prof = {
  /* SSID */  "AndroidNathy",
  /* WPA/WPA2 passphrase */  "jkrl1331",
  /* IP address */  "192.168.43.2",
  /* subnet mask */  "255.255.255.0",
  /* Gateway IP */  "192.168.43.1", };

String remote_server = "192.168.43.112"; // peer device IP address
String remote_port = "9876";


//At home:
//WIFI_PROFILE wireless_prof = {
 // /* SSID */  "PaoDeQueijo",
 // /* WPA/WPA2 passphrase */  "fornodeminas51",
 // /* IP address */  "192.168.1.223",
  ///* subnet mask */  "255.255.255.0",
  ///* Gateway IP */  "192.168.1.1", };

//String remote_server = "192.168.137.1"; // peer device IP address
//String remote_port = "9876";

//long previousMillis = 0; //
//long interval = 1000; // (milliseconds)
//WIFI_PROFILE wireless_prof = {
  //                      /* SSID */ "Arquitetura",
    //     /* WPA/WPA2 passphrase */ "@ll1n0n3",
      //            /* IP address */ "192.168.0.111",
        //         /* subnet mask */ "255.255.255.0",
          //        /* Gateway IP */ "192.168.0.1", };

//String remote_server = "192.168.0.104"; // peer device IP address
//String remote_port = "9876";

// Initialize client with IP address and port number
WifiClient client(remote_server, remote_port, PROTO_UDP);
//Analog
//int highpin = A5; //analog 
//Sensors
/*int sensor_Temp;
 float voltageAux;
 float voltage;
 float temperature;*/

//temperature and humidity
int temperatureCommand  = B00000011;  // command used to read temperature
int humidityCommand = B00000101;  // command used to read humidity

int clockPin = 8;  //digital pin used for clock
int dataPin  = 9;  //digital pin used for data
int ledPin = 7;
int ack;  // track acknowledgment for errors
int val;
float temperature;
float humidity;

//gas
int gasHPin = 3; //analog  - test H2 - chip robusto
int gasH2Pin = 5; //analog -test H2 - 
int gasMPin = 4; //analog for gas methane

//luminosity
int lumPin = 2; //analog pin used for read luminosity

int previoustimespoiled = 30;
//MSG for Server
String completeMSG = "";
void setup()
{
  //set pins
  pinMode (ledPin, OUTPUT);
  // pinMode(highpin, OUTPUT); // set pin a5 to output
  //pinMode(sensor_Temp,INPUT);
  // connect to AP
  // analogReference(INTERNAL);
  Wireless.begin(&wireless_prof);

  // if you get a connection, report back via serial:
  if (client.connect()) {
    Serial.println("connected");

    // Send message over UDP socket to peer device
    client.println("bananaController");
    Serial.println("receiving message from Manager: ");
    waitMessageGod();
  } 
  else {
    // if connection setup failed:
    Serial.println("failed");
  }
  delay(10000);
}

void loop()
{
  
  // read the temperature and convert it to centigrades
  sendCommandSHT(temperatureCommand, dataPin, clockPin);
  waitForResultSHT(dataPin);
  val = getData16SHT(dataPin, clockPin);
  skipCrcSHT(dataPin, clockPin);
  temperature = (float)val * 0.01 - 40;
  //Serial.print("temperature: ");
  //Serial.println(temperature);
  //client.print("temperature: ");
  //client.println(temperature);
  delay(500);
  
  String stT = String(val);
  completeMSG = String(stT+";");
  // read the humidity
  sendCommandSHT(humidityCommand, dataPin, clockPin);
  waitForResultSHT(dataPin);
  val = getData16SHT(dataPin, clockPin);
  skipCrcSHT(dataPin, clockPin);
  humidity = -4.0 + 0.0405 * val + -0.0000028 * val * val;
 // Serial.print(" humidity: ");
  //Serial.println(humidity);
 // client.print(" humidity: ");
  //client.println(humidity);
  delay(200); // wait for 200 milliseconds for next reading

completeMSG = String(completeMSG+val+";");
//read gas
  int gas;
  gas=analogRead(gasHPin);//Read Gas value from analog 0
  //Serial.println("gas:");
  //Serial.println(gas,DEC);//Print the value to serial port
  //client.print("gas: ");
  //client.println(gas,DEC);
  delay(100);
  completeMSG = completeMSG+gas+";";
  //read gas
  int gas2;
  gas2=analogRead(gasH2Pin);//Read Gas value from analog 0
  //Serial.println("gas2:");
  //Serial.println(gas2,DEC);//Print the value to serial port
  
  delay(100);
  
  completeMSG = completeMSG+gas2+";";
    //read gas methane
  int gas3;
  gas3=analogRead(gasMPin);//Read Gas value from analog 0
  //Serial.println("gasMethane:");
  //Serial.println(gas3,DEC);//Print the value to serial port
  
  delay(100);
  
  completeMSG = completeMSG+gas3+";";
  
  int luminosity;
  luminosity = analogRead(lumPin);
  //Serial.println("Luminosity: ");
  //Serial.println(luminosity);//Print the value to serial port
  
    delay(100);
  
  completeMSG = completeMSG+luminosity+";";
  
  completeMSG = completeMSG+previoustimespoiled;
  
  Serial.println("CompleteMessage: ");
  Serial.println(completeMSG);
  client.println(completeMSG);
  
  waitMessage();
  
  /*  while (client.available()) {
   int in;
   
   while ((in = client.read()) == -1);
   
   Serial.print((char)in);
   }*/

  delay(1);
}

// commands for reading/sending data to a SHTx sensor

// send a command to the SHTx sensor
void sendCommandSHT(int command, int dataPin, int clockPin)
{
  int ack;

  // transmission start
  pinMode(dataPin, OUTPUT);
  pinMode(clockPin, OUTPUT);
  digitalWrite(dataPin, HIGH);
  digitalWrite(clockPin, HIGH);
  digitalWrite(dataPin, LOW);
  digitalWrite(clockPin, LOW);
  digitalWrite(clockPin, HIGH);
  digitalWrite(dataPin, HIGH);
  digitalWrite(clockPin, LOW);

  // shift out the command (the 3 MSB are address and must be 000, the last 5 bits are the command)
  shiftOut(dataPin, clockPin, MSBFIRST, command);

  // verify we get the right ACK
  digitalWrite(clockPin, HIGH);
  pinMode(dataPin, INPUT);
  ack = digitalRead(dataPin);
  if (ack != LOW)
    Serial.println("ACK error 0");
  digitalWrite(clockPin, LOW);
  ack = digitalRead(dataPin);
  if (ack != HIGH)
    Serial.println("ACK error 1");
}

// wait for the SHTx answer
void waitForResultSHT(int dataPin)
{
  int ack;

  pinMode(dataPin, INPUT);
  for (int i = 0; i < 100; ++i)
  {
    delay(20);
    ack = digitalRead(dataPin);
    if (ack == LOW)
      break;
  }
  if (ack == HIGH)
    Serial.println("ACK error 2");
}

// get data from the SHTx sensor
int getData16SHT(int dataPin, int clockPin)
{
  int val;

  // get the MSB (most significant bits)
  pinMode(dataPin, INPUT);
  pinMode(clockPin, OUTPUT);
  val = shiftIn(dataPin, clockPin, MSBFIRST);
  val *= 256; // this is equivalent to val << 8;

  // send the required ACK
  pinMode(dataPin, OUTPUT);
  digitalWrite(dataPin, HIGH);
  digitalWrite(dataPin, LOW);
  digitalWrite(clockPin, HIGH);
  digitalWrite(clockPin, LOW);

  // get the LSB (less significant bits)
  pinMode(dataPin, INPUT);
  val |= shiftIn(dataPin, clockPin, MSBFIRST);
  return val;
}

// skip CRC data from the SHTx sensor
void skipCrcSHT(int dataPin, int clockPin)
{
  pinMode(dataPin, OUTPUT);
  pinMode(clockPin, OUTPUT);
  digitalWrite(dataPin, HIGH);
  digitalWrite(clockPin, HIGH);
  digitalWrite(clockPin, LOW);
}

void waitMessageGod(){
  String msgFromGod="";
  int numG = 0;
  int pos = 0;
  int contPos = 0;
  while(numG<1){
  while (client.available()) {
      int in;

      while ((in = client.read()) == -1);

      char msg = (char)in;
      numG = 2;
      
      if(msg=='-'){
        pos = contPos;
      }
      contPos++;
      if(msg=='*'||in == 0xa){
        break;
      }
      else{
        msgFromGod = msgFromGod+msg;
        Serial.print(msg);
      }
  }
  }
  Serial.println("Message from god is ");
  Serial.println(msgFromGod);
 // remote_port = msgFromGod;
  //String newIp = msgFromGod.substring(0,pos);
  Serial.println("Ip do agente adaptativo: ");
  Serial.println(remote_server);
 // int beginPos = pos+1;
  //String newPort = msgFromGod.substring(beginPos);
  Serial.println("Porta do agente adaptativo: ");
  Serial.println(msgFromGod);
  WifiClient client(remote_server, msgFromGod, PROTO_UDP);
  Wireless.begin(&wireless_prof);

  // if you get a connection, report back via serial:
  if (client.connect()) {
    Serial.println("connected again");
  }
  else{
   WifiClient client(remote_server, remote_port, PROTO_UDP);
   Wireless.begin(&wireless_prof); 
  }
//remote_server = "192.168.137.1"; // peer device IP address
//String remote_port = "9876";
}

void waitMessage(){
  Serial.println("Aguardando mensagem do adaptativo");
  String completeMsg="";
  int numA = 0;
  int infinito = 0;
  while(numA<1){
  while (client.available()) {
      int in;

      while ((in = client.read()) == -1);

      char msg = (char)in;
      
      numA = 2;

      if(msg=='*'||in == 0xa){
        break;
      }
      else{
        completeMsg = completeMsg+msg;
        Serial.print(msg);
      }
  }
  if(infinito++<100){
    break;
  }
  }
  Serial.println("Mensagem recebida adaptive agent: ");
  Serial.println(completeMsg);
 // if (completeMsg.startsWith("OUTPUT:", 0)){
     //String result = completeMsg.substring(7);
     if(infinito<100){
     int res = completeMsg.toInt();
     previoustimespoiled = res;
       Serial.println("Previous time to spoile: ");
       Serial.println(previoustimespoiled);
     if(res<10){
       digitalWrite(ledPin, HIGH);
     }
     else{
       digitalWrite(ledPin, LOW);
     }
     }
 //  }
}

