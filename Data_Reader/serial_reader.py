from serial import Serial
serial_port  =  '/dev/ttyUSB0'
baud_rate = 115200
write_file_to_path = "data_collected.csv"

output_file = open(write_file_to_path, "a")
ser =Serial(serial_port, baud_rate)
while True:
    line = ser.readline()
    line = line.decode("utf-8")
    print(line)
    output_file.write(line)