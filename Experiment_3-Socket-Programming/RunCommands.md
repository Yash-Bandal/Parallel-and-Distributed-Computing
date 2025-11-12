### Install `Java` and `Javac` on Linux
```wsl
sudo apt install openjdk-17-jdk -y
```


### `/Part1` - Socket Server and Client
1. Open two terminals in VS Code (Terminal → New Terminal).
   - Terminal 1: Run the server
     ```bash
     javac SocketServer.java
     java SocketServer
     ```
    - Terminal 2: Run the client
      ```bash
      javac SocketClient.java
      java SocketClient
      ```
2. Enter numbers on the client, server will return factorials. Type exit to quit.

<br>

### `/Part2` - RMI Remote Method Invocation
#### Running RMI Programs
---
>  Note: RMI requires an RMI registry running in the background.
---

1. Compile all files:
```
javac *.java
```

2. Start RMI Registry (terminal 1):
   -  Windows:
      ```
      start rmiregistry
      ```
   -  Linux/WSL:
      ```
      rmiregistry &
      ```
       Keep this terminal open.\
 
3. Register server (terminal 2):
```
java RegisterMe
```

4. Run client (terminal 3):
```
java AddClient
```

Client will display the addition result from the RMI server.
