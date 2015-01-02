

Author : Pradeep Anantharaman
School : The University of Texas at Dallas
Project Owner : Pradeep Anantharaman



SyncAndroid
===========


Android app to sync between PC and android in the same LAN network


 DESCRIPTION:
 
 
The project involves the TCP communication between a laptop/computer and a mobile device.
The file sync is implemented between the two devices by running a server program continuously 
and when there is a new file on either, server establishes the connection with the client when the
file exchange is required. 


The applications run on Android mobile device and Laptop computer server.
We have implemented the following scenario between the Android device and Laptop using Socket Programming for TCP connection establishment and communication.

2 way file exchange between Android device and computer server involves following steps/ processes:

1.	The server machine continuously listens, and if the client requires to get the latest copy of the file, connection establishment occurs. Client initiates the connection establishment by entering the IP address and port number of the server in the android console. 
2.	Once the connection has been established, the client puts the file to be transferred on the network and once the computer server accepts the connection, the transfer begins. 
3.	 Once the ‘send’ button is pressed in Android console, the client sends the file name to the server to initiate the download and waits for the server response.
4.	Once the server receives the file name from the client, the server gets the path on the storage directory and writes the file to the disk.
5.	The mobile device listens to accept files and once the computer server sends request, device accepts and receives the file
6.	Once the entire file is received at the client side, the client initiates the connection termination with the server.
7.	The server receives the connection termination message and terminates the connection by closing the socket and the input, output stream objects created for file transfer.
      
      
      
       The project used TCP protocol because of the following reasons:
       
1.	TCP provides for the reliable information exchange between the end systems that wishes to communicate. The file transfer in the project needs the files to be synched between two devices, therefore, it was necessary to ensure that once a file is sent from a laptop to a mobile device or vice versa, the entire file was transmitted.
TCP ensures that all datagrams sent from one end are received at the other end, thus ensuring that the quality of the file being transferred is preserved.
2.	The project required communication between the client and server to ensure the files at the both ends are the most recent copies of the files being stored. The server checks the timestamp values of the client and server copies of same file to the client if the copy of the file stored on client machine has an earlier timestamp than the one stored on the server.

1)	Socket opened on computer server as can be seen from the screenshot, its waiting for a client machine to connect to it[FileServer.java].


2)	From Android Device: When we click on ‘Browse’ to see the files present on the mobile device, we can search for the files that can be sent. These files have already been downloaded/existing on the device.

3)	Once an image is selected, we can see its physical path on the device and also an image view
 
4)	Once we click on ‘Send’ button, the android device establishes connection and the server computer accepts the same. 
File is being received by the server computer and shows transfer time in milliseconds as below
 
5)	Once the android device sends the file successfully, the “send status:” shows as success indicating completed file transfer.
 
File received successfully by PC is shown below:
 

6)	Wire shark below shows the capture of the network traffic during the above operation.

 

The packet transfer can be seen from the messages on Wireshark.
The network activity was monitored using Wireshark, as can be seen from above screenshot, and the following network activity was recorded: The computer server (Laptop) sends a TCP connection message to the in order to receive the file. TCP connection request (SYN flag) is initiated by the laptop (IP address: 192.168.0.20) to the mobile device (IP address: 192.168.0.30) by sending an initial sequence number. It sends the connection request with a Sequence No = 0 and Acknowledgement No = 1.
File transfer proceeds thereafter and the computer server receives the file from the mobile device.

7)	Syncing files from the Laptop to the Android Device.
File to be transferred is shown below :
 
8)	Once user clicks on ‘Receive’ button, android device acts as server waiting for accepting incoming connections.
 

The ClientProcess.java program on the Laptop establishes connection and sends the file. The Android device 
Receives and stores the same on device.

 
After the request for the file from the mobile device and as discussed above, the exchange of TCP messages between server and client, we see the following messages in the console which indicate that the file was successfully sent to the android device. 



9)	The file received on mobile device is shown below:
 




10)	Wireshark capture shows the network traffic during the above process:
 

After the first message, Laptop sends the TCP Acknowledgement message by sending the Acknowledge Message to the mobile device. On completion of the file transfer we see that first TCP connection termination - FIN message is sent by the mobile device with Sequence No = 5642 and Acknowledgement No = 1 which is then acknowledged by the Laptop by sending another FIN acknowledge message with its own Sequence No = 1 and an Acknowledgement No which is a Sequence No sent in the Mobile device’s FIN message + 1 i.e.  Acknowledgement No = 5643.
   
