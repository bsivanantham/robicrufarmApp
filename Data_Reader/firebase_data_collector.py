from firebase import Firebase
import getpass
import datetime
import time

email = "empty_string"
password = "empty_string"
data_sent = "empty_string"

#change api-key if required

config = {
    "apiKey": "AIzaSyBjxJRYaAmPP1yD67F4rsT1Tpr8moGvcnw",
    "authDomain": "ldr-value.firebaseapp.com",
    "databaseURL": "https://ldr-value.firebaseio.com",
    "storageBucket": "ldr-value.appspot.com"
}

firebase = Firebase(config)

#This function is responsible for getting the authentication key from the realtime database

print("Enter your email_id: ")
email = input()
password =  getpass.getpass("Enter your password: ")

#Here the authentication key is obtained

auth = firebase.auth()
user = auth.sign_in_with_email_and_password(email,password)
db = firebase.database()

write_file_to_path = "data_collected.csv"
output_file = open(write_file_to_path, "a")
output_file.write("\n")

try:
    while(True):
        
        #Here the pH and Dissolved Oxygen values are pulled from the database

        device_12334_pH  = db.child("devices").child("12334").child("pH").get().val()
        device_12334_do  = db.child("devices").child("12334").child("do").get().val()
        ts = time.time()
        st = datetime.datetime.fromtimestamp(ts).strftime('%Y-%m-%d %H:%M:%S')
        #Values are converted to string and then stored in a csv file

        data_sent = st + " , " + str(device_12334_do) + " , " + str(device_12334_pH) + "\n"
        output_file.write(data_sent)

        #Uncomment the line below if you want to have a live look at the data
        print(data_sent)

        #Uncomment the line below and change the delay interval of you want to record values in a custom time-intervals(Here it is for 5 seconds)
        #time.sleep(5)
except KeyboardInterrupt:
    pass