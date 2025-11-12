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
