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