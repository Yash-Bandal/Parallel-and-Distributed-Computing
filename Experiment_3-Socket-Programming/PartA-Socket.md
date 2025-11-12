```bash
sudo apt install openjdk-17-jdk -y
```


### Server (Serversocket.java)



## Explanation
# Server Flow: Factorial Calculation (Socket Application)

This outlines the step-by-step sequential process the server program executes to handle client connections and calculate factorials. 


## Sequential Flow:

1.  **Start and Wait:**
    * The server program starts execution.
    * It calls the **blocking `accept()`** method on the `ServerSocket`.
    * The server remains idle, waiting for a client to initiate a connection on the specified port.

2.  **Connection Established:**
    * When a client successfully connects, the `accept()` call completes, returning a new **`Socket`** object for communication.
    * The server confirms the event by printing the message: **“Client connected!”**.

3.  **Enter Communication Loop:**
    * The server enters a continuous **`while` loop**.
    * It sets up dedicated input (`BufferedReader`) and output (`PrintWriter`) streams to facilitate reading and writing text messages to the connected client.

4.  **Read and Check Message:**
    * The server attempts to read a message (`msg`) sent by the client.
    * The server checks the content of the message:
        * If `msg` equals **“exit”** (case-insensitive), the server **breaks** out of the communication loop and proceeds to step 6.
        * Otherwise (if the message is a number), the server proceeds to the calculation step (step 5).

5.  **Calculate and Respond:**
    * The server converts the received message string into an integer (`num`).
    * It calculates the **factorial** of that integer.
    * The factorial result is sent back to the client using the output stream (`out.println()`).
    * The server then returns to step 4, waiting for the next message from the client.

6.  **Close Connection:**
    * Once the communication loop is exited (typically triggered by the client sending "exit"), the server performs necessary cleanup.
    * It calls **`client.close()`** and **`server.close()`**, shutting down the communication sockets and terminating the server application.
