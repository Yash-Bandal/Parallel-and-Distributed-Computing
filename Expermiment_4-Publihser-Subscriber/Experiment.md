## Objective
To demonstrate message-oriented communication between two Java programs using a Message Broker (Apache ActiveMQ) based on JMS (Java Message Service).
You will create:
- Publisher (Producer) → sends messages
- Subscriber (Consumer) → receives messages
Messages don’t go directly from Publisher → Subscriber.
Instead, they go through  **ActiveMQ Broker**.


<br>


Publisher → sends a message → broker stores it → Subscriber picks it up.
ActiveMQ = The Post Office.

### Publisher.java
```java
// Publisher.java - SEnds message
import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Publisher {
    public static void main(String[] args) {

        String brokerURL = "tcp://localhost:61616";  // Broker server

        try {
            // 1. Create factory → used to connect to ActiveMQ server
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);

            // 2. Create connection + start it
            Connection connection = factory.createConnection();
            connection.start();

            // 3. Create session (no transaction, auto acknowledge)
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 4. Create Topic (name must match subscriber)
            Topic topic = session.createTopic("MyTopic");

            // 5. Create producer to send message
            MessageProducer producer = session.createProducer(topic);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // 6. Create text message
            String text = "Hello Subscribers! Welcome to JMS Publish-Subscribe Example.";
            TextMessage message = session.createTextMessage(text);

            // 7. Send message to topic
            producer.send(message);

            System.out.println(" Publisher Sent Message: " + text);

            // 8. Cleanup
            session.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


```



### Subscriber.java
```java
import javax.jms.*;                             // JMS (Java Messaging Service) classes
import org.apache.activemq.ActiveMQConnectionFactory;  // ActiveMQ specific class

public class Subscriber {
    public static void main(String[] args) {
        try {
            // URL of ActiveMQ broker
            String brokerURL = "tcp://localhost:61616";

            // Step 1: Create connection factory
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);

            // Step 2: Create connection
            Connection connection = factory.createConnection();

            // Start the connection
            connection.start();

            // Step 3: Create a session (no transactions, auto acknowledge)
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Step 4: Create Topic (same name as Publisher)
            Topic topic = session.createTopic("MyTopic");

            // Step 5: Create a consumer (subscriber)
            MessageConsumer consumer = session.createConsumer(topic);

            // Step 6: When message arrives, this block is triggered
            consumer.setMessageListener(new MessageListener() {
                public void onMessage(Message msg) {
                    try {
                        // If received message is of type TextMessage, print its content
                        if (msg instanceof TextMessage) {
                            System.out.println("Subscriber Received Message: " 
                                + ((TextMessage) msg).getText());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            System.out.println("Subscriber is waiting for messages...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



/*

sudo apt install openjdk-17-jdk

wget https://archive.apache.org/dist/activemq/5.19.1/apache-activemq-5.19.1-bin.tar.gz


or https://activemq.apache.org/components/classic/download/ 
Unix/Linux/Cygwin	apache-activemq-5.19.1-bin.tar.gz


tar -xvzf apache-activemq-5.19.1-bin.tar.gz



javac -cp "apache-activemq-5.19.1/lib/*:apache-activemq-5.19.1/lib/optional/*:." Publisher.java Subscriber.java



./apache-activemq-5.19.1/bin/activemq start

./apache-activemq-5.19.1/bin/activemq status


Terminal1
java -cp "apache-activemq-5.19.1/lib/*:apache-activemq-5.19.1/lib/optional/*:." Subscriber


Terminal2
java -cp "apache-activemq-5.19.1/lib/*:apache-activemq-5.19.1/lib/optional/*:." Publisher



 */
```

<br>

# Commands - Perform in One filder all
1. STEP–1: Download ActiveMQ
```bash
wget https://archive.apache.org/dist/activemq/5.19.1/apache-activemq-5.19.1-bin.tar.gz
```

2. STEP–2: Extract the tar.gz file
```bash
tar -xvzf apache-activemq-5.19.1-bin.tar.gz
```
```bash
ls
```

3. STEP–3: Compile Publisher & Subscriber
```bash
javac -cp "apache-activemq-5.19.1/lib/*:apache-activemq-5.19.1/lib/optional/*:." Publisher.java Subscriber.java
```

4. STEP–4: Start ActiveMQ Broker
```bash
./apache-activemq-5.19.1/bin/activemq start
```
Check if running:
```bash
./apache-activemq-5.19.1/bin/activemq status
```

5. STEP–5: Run Subscriber (Terminal-1)
```bash
java -cp "apache-activemq-5.19.1/lib/*:apache-activemq-5.19.1/lib/optional/*:." Subscriber
```

6. STEP–6: Run Publisher (Terminal-2)
```bash
java -cp "apache-activemq-5.19.1/lib/*:apache-activemq-5.19.1/lib/optional/*:." Publisher
```

