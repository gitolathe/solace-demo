package com.solacesystems.integration.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

public class MessageConsumer implements MessageListener {
    public void onMessage(Message message) {
        // Application specific handling code would follow.
        // For this example print the topic of each message
        try {
            System.out.println("Received message on destination: " +
                    message.getJMSDestination().toString());
        } catch (JMSException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"SolResources.xml"});
        MessageConsumer consumer = (MessageConsumer) context.getBean("messageConsumer");
    }
}