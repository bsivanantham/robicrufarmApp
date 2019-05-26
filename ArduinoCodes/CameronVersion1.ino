#include <ESP8266WiFi.h>
#include<ArduinoJson.h>
#include <FirebaseArduino.h>
#include <Adafruit_ADS1015.h>
/// I2C ADC EXTENDER///////////////////////
Adafruit_ADS1115 ads;
// Set these to run example.
double  tempValue;
unsigned long int avgValue; 
float lc = 0.0048758553;
float pH_val;
float Voltage = 0.0;
int thermistor_25 = 10000;
float refCurrent = 0.0001;
float m,c;
int n = 3 , i;
float stds[3] = {4 , 7 , 9.2};
int buf[10],inst;
#define FIREBASE_HOST "ldr-value.firebaseio.com"
#define WIFI_SSID "Xiaomi_D093"
#define WIFI_PASSWORD "Robicruf@rm12345"

void setup()
{
  Serial.begin(115200);
    ads.setGain(GAIN_TWOTHIRDS);
  ads.begin();
    WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED)
  {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("connected: ");
  Serial.println(WiFi.localIP());
  Firebase.begin(FIREBASE_HOST);
}

void loop() 
{
  int16_t adc0 = ads.readADC_SingleEnded(1);
 // float dov = (adc0 * 0.1875)/1000;
  float  doValue= adc0 *(5.0 / 65535);
  
  // we read from the ADC, we have a sixteen bit integer as a result
  int16_t temp = ads.readADC_SingleEnded(2); // Read ADC value from ADS1115
  Voltage = temp * (5.0 / 65535); // Replace 5.0 with whatever the actual Vcc of your Arduino is
  float resistance = (Voltage / refCurrent); // Using Ohm's Law to calculate resistance of thermistor
  float ln = log(resistance / thermistor_25); // Log of the ratio of thermistor resistance and resistance at 25 deg. C
  float kelvin = 1 / (0.0033540170 + (0.00025617244 * ln) + (0.0000021400943 * ln * ln) + (-0.000000072405219 * ln * ln * ln)); // Using the Steinhart-Hart Thermistor Equation to calculate temp in K
  float tempValue = kelvin - 273.15; // Converting Kelvin to Celcuis
   
  Serial.print("pH: ");
  Serial.println(calibrate());
  Serial.println(doValue);
  // handle error
  if (Firebase.failed()) {
    Serial.println("Firebase Pushing /sensor/failed:");
    Serial.println(Firebase.error()); 
    }
  else{
      Serial.print("Firebase Pushed /sensor/ ");
      }
  Firebase.setFloat ("/devices/12334/do", doValue);
  Firebase.setFloat ("/devices/12334/temp", tempValue);
  Firebase.setFloat ("/devices/12334/pH", calibrate());
  delay(10000);
}

float get_pH_val(){
  float tempv = (ads.readADC_SingleEnded(0) * 0.1875)/1000;
  float read_pH_volt = tempv * (5.0 / 65535);
  float pH_val1 = read_pH_volt - c;
  pH_val = pH_val1 / m;
  return pH_val;
}

float calibrate(){
  float sig_x = 0 , sig_y = 0 , sig_xy = 0 , sig_x2 = 0;
  float volts[3];
  volts[0] = 3.57;
  volts[1] = 2.58;
  volts[2] = 1.41;
  for(i=0;i<n;i++){
     sig_y = sig_y + volts[i];
     sig_x = sig_x + stds[i];
     sig_x2 = sig_x2 + (stds[i] * stds[i]);
     sig_xy = sig_xy + (stds[i] * volts[i]);
  }
  float m_num = (n * sig_xy) - (sig_x * sig_y);
  float denom = (n * sig_x2) - (sig_x * sig_x);
  m = m_num / denom;

  float c_num = (sig_x2 * sig_y) - (sig_x * sig_xy);
  float denom1 = (n * sig_x2) - (sig_x * sig_x);
  c = c_num / denom1;
  //delay(2000);
  return get_pH_val();
}
