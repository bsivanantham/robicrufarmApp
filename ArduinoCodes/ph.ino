float lc = 0.0048758553;
float pH_val;
float m;
float c;
int n = 3 , i;
float stds[3] = {4 , 7 , 9.2};
void calibrate(){
  float sig_x = 0 , sig_y = 0 , sig_xy = 0 , sig_x2 = 0;
  float volts[3];
  /*Serial.println("Place the probe in 4pH...\n");
  delay(10000);
  Serial.println("Calibrating....\n");
  delay(60000);
  volts[0] = analogRead(A0) * lc;
  
  delay(1000);
  Serial.println("Place the probe in 7pH...\n");
  delay(10000);
  Serial.println("Calibrating....\n");
  delay(60000);
  volts[1] = analogRead(A0) * lc;
  
  Serial.println("Place the probe in 9.2pH...\n");
  delay(10000);
  Serial.println("Calibrating....\n");
  delay(60000);
  volts[2] = analogRead(A0) * lc;

  Serial.print("Voltage at 4pH standerd:\t");
  Serial.println(volts[0]);
  Serial.println("\n\n");
  delay(1000);

  Serial.print("Voltage at 7pH standerd:\t");
  Serial.println(volts[1]);
  Serial.println("\n\n");
  delay(1000);

  Serial.print("Voltage at 9.2pH standerd:\t");
  Serial.println(volts[2]);
  Serial.println("\n\n");
  delay(1000);
  Serial.println("Computing m , c... \n\n");*/
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
  Serial.println("m ,c values : \t");
  Serial.print(m);
  Serial.print("\t\t");
  Serial.print(c);
  delay(2000);
  get_pH_val();
}

void get_pH_val(){
  Serial.println("\n\n");
  Serial.println("Place the probe in the solution...\n");
  delay(10000);
  Serial.println("Wait for voltage to stabilize...\n");
  delay(60000);
  float read_pH_volt = analogRead(A0) * lc;
  float pH_val1 = read_pH_volt - c;
  pH_val = pH_val1 / m;
  Serial.print("The pH value is : \t");
  Serial.println(pH_val);
}

void setup() {
  Serial.begin(9600);
  Serial.print("pH Calibration ...\n");
  delay(1000);
  calibrate();

}

void loop() {
  
}