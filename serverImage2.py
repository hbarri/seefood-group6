import socket
import struct
import subprocess

HOST = ''
PORT = 9090

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((HOST, PORT))
s.listen(5)

print('SERVER STARTED RUNNING')

client, address = s.accept()
buf = ''
while len(buf) < 4:
    buf += client.recv(4 - len(buf))
size = struct.unpack('!i', buf)[0]

print "receiving %s bytes" % size

with open('testImg.jpg', 'wb') as f:
    while size > 0:
        data = client.recv(1024)
        f.write(data)
        size -= len(data)
print('Image Saved')

# send image to seeFood AI
cmdString = subprocess.check_output("python find_food.py testImg.jpg", shell=True)

print ("Sending AI response to client: ", cmdString)
client.sendall(cmdString)

client.close()
s.close()