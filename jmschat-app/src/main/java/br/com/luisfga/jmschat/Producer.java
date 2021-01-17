package br.com.luisfga.jmschat;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Producer {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(Producer.class);

    private Session session;
    private MessageProducer messageProducer;
    private String senderId;

    public void create(String senderId, Session session, Queue queue) throws JMSException {
        this.senderId = senderId;
        
        // create a Session
        this.session = session;

        // create a MessageProducer for sending messages
        messageProducer = session.createProducer(queue);
    }

    public void sendTxtMsg(String msg) {

        try {
            // create a JMS TextMessage
            TextMessage textMessage = session.createTextMessage(msg);
            
            textMessage.setStringProperty("senderID", senderId);
            
            // send the message to the queue destination
            messageProducer.send(textMessage);
            
            LOGGER.debug("Mensagem enviada='{}'", msg);
        } catch (JMSException ex) {
            LOGGER.error("Erro ao tentar enviar mensagem");
        }
    }
}
