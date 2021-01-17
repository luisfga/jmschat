package br.com.luisfga.jmschat;

import javafx.scene.control.TextArea;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer implements MessageListener{

    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    private static String NO_GREETING = "no greeting";

    private MessageConsumer messageConsumer;
    
    private TextArea stdout;

    public void create(Session session, Queue queue, TextArea stdout) throws JMSException {

        // create a MessageConsumer for receiving messages
        messageConsumer = session.createConsumer(queue);
        
        //set listener
        messageConsumer.setMessageListener(this);

        this.stdout = stdout;
    }

    @Override
    public void onMessage(Message message) {
        try {

            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage)message;
                
                LOGGER.info("Mensagem de "+ textMessage.getStringProperty("senderID") + ": " +textMessage.getText());
                stdout.appendText("Mensagem de "+ textMessage.getStringProperty("senderID") + ": " +textMessage.getText()+"\n");
                textMessage.acknowledge();
                
            } else {
                LOGGER.info(message.toString());
            }

        } catch (JMSException ex) {
            LOGGER.error("Erro ao receber mensagem");
        }
        
    }
}
