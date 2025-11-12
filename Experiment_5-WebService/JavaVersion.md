```bash
sudo apt install openjdk-17-jdk -y
```
Netbeans
```bash
wget https://dlcdn.apache.org/netbeans/netbeans/27/netbeans-27-bin.zip

```

Glassfish
```bash
wget https://repo1.maven.org/maven2/org/glassfish/main/distributions/glassfish/7.0.9/glassfish-7.0.9.zip
```

```bash
unzip glassfish-7.0.9.zip
```

```bash
sudo mv glassfish7 /opt/glassfish7

/opt/glassfish7/bin/asadmin start-domain

```




```java
package org.me.calculator;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "CalculatorWS")
public class CalculatorWS {

    @WebMethod(operationName = "add")
    public int add(@WebParam(name = "num1") int num1, @WebParam(name = "num2") int num2) {
        return num1 + num2;
    }

    @WebMethod(operationName = "subtract")
    public int subtract(@WebParam(name = "num1") int num1, @WebParam(name = "num2") int num2) {
        return num1 - num2;
    }

    @WebMethod(operationName = "multiply")
    public int multiply(@WebParam(name = "num1") int num1, @WebParam(name = "num2") int num2) {
        return num1 * num2;
    }

    @WebMethod(operationName = "divide")
    public double divide(@WebParam(name = "num1") double num1, @WebParam(name = "num2") double num2) {
        if (num2 == 0) throw new IllegalArgumentException("Division by zero not allowed.");
        return num1 / num2;
    }
}

```


```java
package calculatorclientapp;
import org.me.calculator.CalculatorWS_Service;

public class MainClient {
    public static void main(String[] args) {
        try {
            CalculatorWS_Service service = new CalculatorWS_Service();
            org.me.calculator.CalculatorWS port = service.getCalculatorWSPort();

            int a = 10, b = 5;
            System.out.println("Addition Result: " + port.add(a, b));
            System.out.println("Subtraction Result: " + port.subtract(a, b));
            System.out.println("Multiplication Result: " + port.multiply(a, b));
            System.out.println("Division Result: " + port.divide(a, b));
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
```
