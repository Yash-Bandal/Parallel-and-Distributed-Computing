##  Experiment 3: Distributed Applications using Java Sockets and RMI



### 1. Objective

The main goal of this experiment is to learn how to develop **distributed applications** in Java using:
* **Sockets** – for low-level client-server communication.
* **RMI (Remote Method Invocation)** – for high-level remote method calls.


  
We will implement:
* A **Socket-based client-server application** for simple operations (like factorial, sum of digits, string reversal).
* An **RMI-based application** where the client calls a method on the server remotely (e.g., addition of two numbers).

<br>

### 2. Basics of Socket Programming

A **socket** is like a virtual "pipe" connecting two programs (**client** and **server**) over a network. 
* **Client** – requests services or data.
* **Server** – provides services or data.

#### Steps in Socket Programming

| Component | Step | Code Example (Conceptual) |
| :--- | :--- | :--- |
| **Server-side** | **1. Create Server Socket** | `ServerSocket server = new ServerSocket(port);` |
| | **2. Wait for Client** | `Socket client = server.accept();` (Blocks until connection) |
| | **3. Communicate** | `InputStream / OutputStream` – read/write data from/to the client. |
| | **4. Cleanup** | `server.close()` / `client.close()`. |
| **Client-side** | **1. Connect to Server** | `Socket socket = new Socket(host, port);` |
| | **2. Communicate** | `InputStream / OutputStream` – communicate with server. |
| | **3. Cleanup** | `socket.close()`. |

<br>

### 3. Basics of RMI (Remote Method Invocation)

**RMI** allows a Java program on one machine to invoke a method of a Java object on another machine as if it were local. 

#### Key Components of RMI

* **Remote Interface** – declares methods that can be called remotely. Must extend `java.rmi.Remote`.
* **Server Implementation** – implements the remote interface and extends `UnicastRemoteObject`.
* **RMI Registry** – a service that keeps track of remote objects and their names.
* **Client Program** – looks up remote objects in the registry and calls their methods.

#### How it Works (Flow)
1.  **Server** creates an object implementing the remote interface.
2.  **Server** registers the object in the RMI **registry** with a unique name (`Naming.rebind("serviceName", object)`).
3.  **Client** looks up the object by name (`Naming.lookup(...)`).
4.  **Client** invokes remote methods through the **stub** (Client-side proxy).
5.  **Server** executes the method (via **skeleton** internally) and returns the result.

**Stub and Skeleton:**
* **Stub**: The client-side proxy object. It handles **marshalling** (packing method parameters to send across the network).
* **Skeleton**: The server-side helper object. It handles **unmarshalling** (extracting received data) and calling the actual remote method implementation.

<br>

### 4. Why we use Sockets and RMI

| Feature | Sockets | RMI |
| :--- | :--- | :--- |
| **Level** | **Low-level** (byte or stream-based) | **High-level** (object-based) |
| **Data Transfer** | Raw bytes / strings | Java objects |
| **Ease of Use** | **More coding** required for message formatting and parsing. | **Easier**, as remote methods look like local calls. |
| **Control** | Fine-grained control over protocol and data. | Less control, more abstraction provided by the framework. |
| **Use Case** | Simple network communication, chat apps, multi-language interoperability. | Distributed Java applications, passing complex Java objects remotely. |



### 5. Summary
Experiment 3 teaches **distributed client-server communication** in Java.
* **Socket programming** requires manual data sending and receiving (low-level).
* **RMI programming** enables transparent method invocation on remote objects (high-level).
