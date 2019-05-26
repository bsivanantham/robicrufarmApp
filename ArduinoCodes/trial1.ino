#include <Wire.h>
#include <Adafruit_ADS1015.h>
Adafruit_ADS1115 ads; /* Use this for the 16-bit version */
int A=0;
int B=0;
int C=0;
void setup (void)
{
  Serial.begin (115100);
  ads.begin ();
}

void loop (void)
{
  int16_t adc0, adc1, adc2, adc3, adc4, adc5, adc6;

  adc0 = ads.readADC_SingleEnded (0);
  adc1 = ads.readADC_SingleEnded (1);
  adc2 = ads.readADC_SingleEnded (2);
  adc3 = ads.readADC_SingleEnded (3);
  adc4 = ads.readADC_SingleEnded (4);
  adc5 = ads.readADC_SingleEnded (5);
  adc6 = ads.readADC_SingleEnded (6);

  A=map(adc0,3100,6800,0,100);
  B=map(adc1,4000,9800,0,100);
  C=map(adc2,3800,9900,0,100);

  Serial.print ("AIN0:"); Serial.println (((adc0 * 0.1875)/1000),7);
  Serial.print ("AIN1:"); Serial.println (((adc1 * 0.1875)/1000),7);
  Serial.print ("AIN2:"); Serial.println (((adc2 * 0.1875)/1000),7);
  Serial.print ("AIN3:"); Serial.println (((adc3 * 0.1875)/1000),7);
  Serial.print ("AIN4:"); Serial.println (((adc4 * 0.1875)/1000),7);
  Serial.print ("AIN5:"); Serial.println (((adc5 * 0.1875)/1000),7);
  Serial.print ("AIN6:"); Serial.println (((adc6 * 0.1875)/1000),7);
  Serial.println ("");

  Serial.print ("A:"); Serial.println (A);
  Serial.print ("B:"); Serial.println (B);
  Serial.print ("C:"); Serial.println (C);
  Serial.println ("");
  delay (1000);
}
