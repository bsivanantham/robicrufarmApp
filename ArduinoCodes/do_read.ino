float zero_do_val;
float air_do_val;
float do_read;
void setup() {
  
  Serial.begin(9600);
read_do();
}

void read_do(){
  Serial.println("Place the proble in zero sol :\t");
  delay(10000);
  Serial.println("Calibrating ... \n");
  delay(60000);
  zero_do_val = analogRead(A1) * 0.0048758553;
  Serial.println("Place the proble in air sol :\t");
  delay(10000);
  Serial.println("Calibrating ... \n");
  delay(60000);
  air_do_val = analogRead(A1);
  float perc = map(do_read,zero_do_val , air_do_val , 0 , 100);
  Serial.println("Place the DO probe in the solution");
  delay(60000);
  do_read=analogRead(A1);
  Serial.println(perc);
}

void loop() {
  // put your main code here, to run repeatedly:

}
