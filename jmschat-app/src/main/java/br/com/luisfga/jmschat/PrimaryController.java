package br.com.luisfga.jmschat;

import java.io.IOException;
import java.util.UUID;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrimaryController {
    
    private Connection connection;
    private Session session;

    private static final Logger LOGGER = LoggerFactory.getLogger(PrimaryController.class);
    
    private Producer producer;
    
    private Consumer consumer;
    
    //TODO ver como será a queue. Uma pra cada conversa? Uma por usuário? E como ela se chamará? É preciso id do usuário?
    @FXML
    private TextField userId;
    
    @FXML
    private TextField destId;

    @FXML
    private TextField msgTextField;
    
    @FXML
    private TextArea stdout;
    
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    
    @FXML
    private void connect() throws JMSException {
        
        //conectar
        LOGGER.info("Conectando...");
        // create a Connection Factory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);

        // create a Connection
        connection = connectionFactory.createConnection();
        connection.setClientID(userId.getText());
        
        // start the connection in order to receive messages
        connection.start();
        
        // create a Session
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // create the Queue to which messages will be sent
        Queue destQueue = session.createQueue("queue-"+destId.getText());
        
        //passar conexão para o producer
        LOGGER.info("Preparando producer...");
        try {
            producer = new Producer();
            producer.create(userId.getText(), session, destQueue);
        } catch (JMSException ex) {
            LOGGER.error("Erro ao construir o Producer");
        }
        
        //passar conexão para o consumer
        Queue userQueue = session.createQueue("queue-"+userId.getText());
        LOGGER.info("Preparando consumer...");
        try {
            consumer = new Consumer();
            consumer.create(session, userQueue, stdout);
        } catch (JMSException ex) {
            LOGGER.error("Erro ao construir o Producer");
        }

    }
    
    @FXML
    private void setJMSListener() {
        LOGGER.info("setJMSListener");
    }
    
    @FXML
    private void sendMessage(){
        LOGGER.info("Message to send: " + msgTextField.getText());
        producer.sendTxtMsg(msgTextField.getText());
    }

}
