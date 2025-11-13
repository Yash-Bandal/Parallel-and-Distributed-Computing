```
sudo apt install openjdk-8-jdk
```
no 17

### Step 1 – Write IDL File `Hello.idl`
```idl
module HelloApp {
    interface Hello {
        string sayHello();
    };
};

```

### Step 2 – Compile the IDL File
```
idlj -fall Hello.idl
```
op
```
HelloApp/
├── Hello.java
├── HelloHelper.java
├── HelloHolder.java
├── HelloOperations.java
├── HelloPOA.java
└── _HelloStub.java
```


### Step 3 – Create the Server Program
Outside create 
HelloServer.java
```java
import HelloApp.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

class HelloServant extends HelloPOA {
    private ORB orb;

    public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    public String sayHello() {
        return "\nHello world!! (Response from CORBA Server)\n";
    }

    public void shutdown() {
        orb.shutdown(false);
    }
}

public class HelloServer {
    public static void main(String args[]) {
        try {
            ORB orb = ORB.init(args, null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            HelloServant helloRef = new HelloServant();
            helloRef.setORB(orb);

            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(helloRef);
            Hello href = HelloHelper.narrow(ref);

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            String name = "Hello";
            NameComponent path[] = ncRef.to_name(name);
            ncRef.rebind(path, href);

            System.out.println("HelloServer ready and waiting...");
            orb.run();
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }
        System.out.println("HelloServer Exiting ...");
    }
}

```
```java
// Import the generated classes from HelloApp package (created by idlj)
import HelloApp.*;

// Import CORBA and related packages
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

// ---------------------------
// Implementation of the CORBA servant
// ---------------------------
class HelloServant extends HelloPOA {   // Extends skeleton class generated from IDL
    private ORB orb;   // Reference to the ORB (Object Request Broker)

    // Method to set the ORB reference
    public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    // Implementation of the IDL method sayHello()
    public String sayHello() {
        return "\nHello world!! (Response from CORBA Server)\n";
    }

    // Method to shut down the ORB (optional)
    public void shutdown() {
        orb.shutdown(false);
    }
}

// ---------------------------
// Main Server Program
// ---------------------------
public class HelloServer {
    public static void main(String args[]) {
        try {
            // Step 1: Initialize the ORB (acts as the middleware)
            ORB orb = ORB.init(args, null);

            // Step 2: Get reference to RootPOA and activate its POA Manager
            POA rootpoa = POAHelper.narrow(
                orb.resolve_initial_references("RootPOA")
            );
            rootpoa.the_POAManager().activate();

            // Step 3: Create servant (the actual object that implements methods)
            HelloServant helloRef = new HelloServant();
            helloRef.setORB(orb);   // Give ORB reference to servant

            // Step 4: Register servant with the ORB and get CORBA object reference
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(helloRef);
            Hello href = HelloHelper.narrow(ref); // Narrow it to the Hello interface

            // Step 5: Get reference to the NameService (CORBA Naming Service)
            org.omg.CORBA.Object objRef =
                orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // Step 6: Bind the object reference in the Naming Service with a name
            String name = "Hello";  // The name clients will use to look up the object
            NameComponent path[] = ncRef.to_name(name);
            ncRef.rebind(path, href);

            System.out.println("HelloServer ready and waiting...");

            // Step 7: Wait for client invocations
            orb.run();   // Keeps server running to handle incoming requests
        } catch (Exception e) {
            // Print any errors that occur
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }

        // If server exits for some reason
        System.out.println("HelloServer Exiting ...");
    }
}
```

### Step 4 – Create the Client Program
HelloClient.java

```java
import HelloApp.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.PortableServer.*;
import java.util.Properties;

public class HelloClient {
    public static void main(String args[]) {
        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            String name = "Hello";
            Hello helloImpl = HelloHelper.narrow(ncRef.resolve_str(name));

            System.out.println("Obtained a handle on server object: " + helloImpl);
            System.out.println(helloImpl.sayHello());
        } catch (Exception e) {
            System.out.println("ERROR : " + e);
            e.printStackTrace(System.out);
        }
    }
}

```
```java
// Import the generated classes from the HelloApp package
import HelloApp.*;

// Import CORBA-related packages
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.PortableServer.*;
import java.util.Properties;

// ---------------------------
// CORBA Client Program
// ---------------------------
public class HelloClient {
    public static void main(String args[]) {
        try {
            // Step 1: Initialize the ORB (Object Request Broker)
            // ORB acts as the middleware to locate and invoke objects across the network
            ORB orb = ORB.init(args, null);

            // Step 2: Get reference to the CORBA Naming Service
            // The Naming Service keeps track of all registered CORBA objects
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");

            // Step 3: Narrow the generic reference to a NamingContextExt (extended naming context)
            // This allows us to use string names to find objects
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // Step 4: Resolve the object reference in the Naming Service
            // "Hello" is the name that the server registered earlier
            String name = "Hello";
            Hello helloImpl = HelloHelper.narrow(ncRef.resolve_str(name));

            // Step 5: Use the remote object — call its method
            System.out.println("Obtained a handle on server object: " + helloImpl);
            System.out.println(helloImpl.sayHello()); // Invokes sayHello() on the remote object

        } catch (Exception e) {
            // Catch and print any errors
            System.out.println("ERROR : " + e);
            e.printStackTrace(System.out);
        }
    }
}

```


### Step 5 – Compilation Steps
```
javac HelloServer.java HelloClient.java HelloApp/*.java
```


### Step 6 - Run the CORBA Application
```
tnameserv -ORBInitialPort 1050 &

```
(Keep this running)

Open another terminal


Run the server
```
java HelloServer -ORBInitialPort 1050 -ORBInitialHost localhost

```
HelloServer ready and waiting...



### Run the client
THird terminal
```
java HelloClient -ORBInitialPort 1050 -ORBInitialHost localhost
```


Output 
```
Hello WOrld
```
