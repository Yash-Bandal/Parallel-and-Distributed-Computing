In Socket programming → we manually send/receive data.\
In RMI → we call methods directly. No manual data transmission code needed.
RMI = Calling a method on a remote JVM.

<br>


| Component                 | File              | Purpose                                       |
| ------------------------- | ----------------- | --------------------------------------------- |
| **Remote Interface**      | `AddI.java`       | Defines the method available for remote call  |
| **Server Implementation** | `AddServer.java`  | Implements the remote interface               |
| **Server Registration**   | `RegisterMe.java` | Registers remote object into RMI registry     |
| **Client Program**        | `AddClient.java`  | Looks up remote object and invokes its method |

<br>

```
Interface (make blueprint🟦) ->  Server (Implement Blueprint) -> Server (Register in Registry)📃
                                                                                            ^
                                                                                            |
                                                                                        Client (Lookup🔍 In
                                                                                         registry and
                                                                                        Invoke Method)
```


<br>

### AddI.java
```java
//AddI.java - Remote Interface
import java.rmi.*;

public interface AddI extends Remote {
    int add(int a, int b) throws RemoteException;  // Remote method for addition
}

```

### AddServer.java
```java
//Server Implementation: AddServer.java
import java.rmi.*;
import java.rmi.server.*;

public class AddServer extends UnicastRemoteObject implements AddI {
    public AddServer() throws RemoteException {}  // Constructor

    public int add(int a, int b) {
        return a + b;                              // Implementation of remote method
    }
}

```



### RegisterMe.java
```java
//Register Server: RegisterMe.java
import java.rmi.*;

public class RegisterMe {
    public static void main(String[] args) {
        try {
            AddServer obj = new AddServer();
            Naming.rebind("add", obj);     // Bind server object to RMI registry..endpoint of localhost binded
            System.out.println("RMI Server registered successfully...");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
```


### AddClient.java
```java
//Client Program: AddClient.java
import java.rmi.*;

public class AddClient {
    public static void main(String[] args) {
        try {
            AddI obj = (AddI) Naming.lookup("rmi://localhost/add"); // Lookup remote object
            int res = obj.add(10, 20);                              // Call remote method
            System.out.println("Addition result = " + res);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}


/*

### `/Part2` - RMI Remote Method Invocation
#### Running RMI Programs
---
>  Note: RMI requires an RMI registry running in the background.
---

1. Compile all files:

javac *.java


2. Start RMI Registry (terminal 1):
   -  Windows:
      
      start rmiregistry
      
   -  Linux/WSL:
      
      rmiregistry &
      
       Keep this terminal open.\
 
3. Register server (terminal 2):
java RegisterMe


4. Run client (terminal 3):

java AddClient


Client will display the addition result from the RMI server.
 */


```
