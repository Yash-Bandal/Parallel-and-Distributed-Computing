```bash
sudo apt install openjdk-17-jdk -y
```


### Server (SocketServer.java)

```java
import java.io.*;
import java.net.*;

public class SocketServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(5000); // Create server socket on port 5000
        System.out.println("Server started, waiting for client...");

        Socket client = server.accept(); // Wait for client connection
        System.out.println("Client connected!");

        InputStreamReader isr = new InputStreamReader(client.getInputStream());
        BufferedReader in = new BufferedReader(isr);
        PrintWriter out = new PrintWriter(client.getOutputStream(), true); // true = auto-flush

        String msg;
        while ((msg = in.readLine()) != null) { // Read messages from client
            if (msg.equalsIgnoreCase("exit")) break; // Stop if client wants to exit

            int num = Integer.parseInt(msg); // Convert string to integer
            int fact = 1;
            for (int i = 1; i <= num; i++) fact *= i; // Calculate factorial

            out.println("Factorial of " + num + " = " + fact); // Send result back to client
        }

        client.close(); // Close client connection
        server.close(); // Close server socket
    }
}


//OPEN 2 terminals
/*
Ensure - 
sudo apt install openjdk-17-jdk -y

T1
javac SocketServer.java
java SocketServer


T2
javac SocketClient.java
java SocketClient
 */
```



### SocketClient.java
```java
import java.io.*;
import java.net.*;

public class SocketClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5000); // Connect to server on port 5000

        //To read from terminal of client
        InputStreamReader isr1 = new InputStreamReader(System.in);
        BufferedReader userInput = new BufferedReader(isr1); // Read from console

        //To read from server
        InputStreamReader isr2 = new InputStreamReader(socket.getInputStream());
        BufferedReader in = new BufferedReader(isr2); // Read from server
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // Send to server

        System.out.println("Enter a number (type exit to quit):");
        String msg;

        while ((msg = userInput.readLine()) != null) {
            out.println(msg); // Send message to server..read by op stream
            if (msg.equalsIgnoreCase("exit")) break; // Exit condition

            System.out.println("Server Response: " + in.readLine()); // Print server response
        }

        socket.close(); // Close connection
    }
}

```

<br>

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
