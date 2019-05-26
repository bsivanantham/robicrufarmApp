#include <Wire.h>
#include <Adafruit_ADS1015.h>

Adafruit_ADS1015 ads;     /* Use this for the 12-bit version */

uint16_t adc0;
uint16_t adc1;
uint16_t adc2;
uint16_t adc3;


// make these global so if you need to change them they will be easy to find
// you want to avoid "magic" numbers in your code
// you can give a variable a meaningful name, a number is just a number
// if each transmitter is different you will need variables for all
// adc0Max, adc1Max etc.
uint16_t adc0Max  = 1634;
uint16_t adc0Zero = 332;
uint16_t adc1Max  = 1634;
uint16_t adc1Zero = 332;
uint16_t adc2Max  = 1634;
uint16_t adc2Zero = 332;
uint16_t adc3Max  = 1634;
uint16_t adc3Zero = 332;
uint16_t adc0Kout = 5000;
uint16_t adc1Kout = 5000;
uint16_t adc2Kout = 5000;
uint16_t adc3Kout = 5000;



uint16_t adc0Map;
uint16_t adc1Map;
uint16_t adc2Map;
uint16_t adc3Map;



uint16_t mapPressure( uint16_t Input, uint16_t Zero, uint16_t Max, uint16_t MaxOut )
{

  return (map( Input, Zero, Max, 0, MaxOut));

} // mapPressure



void setup(void)
{
  Serial.begin(115200);
  Serial.println("Hello!");
 
  Serial.println("Getting single-ended readings from AIN0..3");
  Serial.println("ADC Range: +/- 6.144V (1 bit = 3mV/ADS1015, 0.1875mV/ADS1115)");

  ads.begin();
 
}  // setup           

void loop(void)
{
  adc0 = ads.readADC_SingleEnded(0);
  adc1 = ads.readADC_SingleEnded(1);
  adc2 = ads.readADC_SingleEnded(2);
  adc3 = ads.readADC_SingleEnded(3);



 
 //   adc0Map = mapPressure ( adc0, adc0Zero, adc0Max, adc0Kout);
//  adc1Map = mapPressure ( adc1, adcZero, adcMax );
//  adc2Map = mapPressure ( adc2, adcZero, adcMax );
//  adc3Map = mapPressure ( adc3, adcZero, adcMax );

  if (adc0 < adc0Zero || adc0 > adc0Max)
  {
    adc0Map = 0;
  }
    else
    {
    adc0Map = mapPressure ( adc0, adc0Zero, adc0Max, adc0Kout ); 
  }

 if (adc1 < adc1Zero || adc1 > adc1Max)
  {
    adc1Map = 0;
  }
    else
    {
    adc1Map = mapPressure ( adc1, adc1Zero, adc1Max, adc1Kout ); 
  }

 if (adc2 < adc2Zero || adc2 > adc2Max)
  {
    adc2Map = 0;
  }
    else
    {
    adc2Map = mapPressure ( adc2, adc2Zero, adc2Max, adc2Kout ); 
  }
  
 if (adc3 < adc3Zero || adc3 > adc3Max)
  {
    adc3Map = 0;
  }
    else
    {
    adc3Map = mapPressure ( adc3, adc3Zero, adc3Max, adc3Kout ); 
  }
  Serial.print("adc0Map = ");
   Serial.print(adc0Map);
   Serial.print(adc0);

   Serial.println("adc1Map = ");
   Serial.println(adc1Map);
   Serial.println(adc1);

    Serial.println("adc2Map = ");
   Serial.println(adc2Map);
   Serial.println(adc2);

    Serial.println("adc3Map = ");
   Serial.println(adc3Map);
   Serial.println(adc3);

   delay(1000);

} // loop   
