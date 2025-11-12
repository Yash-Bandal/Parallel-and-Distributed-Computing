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
