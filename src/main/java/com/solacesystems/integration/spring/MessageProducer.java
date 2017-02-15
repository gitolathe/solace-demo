package com.solacesystems.integration.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

public class MessageProducer {
    private JmsTemplate jmsTemplate;

    public void sendMessages() throws JMSException {
        getJmsTemplate().send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                Message message = session.createTextMessage("test");
                return message;
            }
        });
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public static void main(String[] args) throws JMSException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"SolResources.xml"});
        MessageProducer producer = (MessageProducer) context.getBean("messageProducer");
        for (int i = 0; i < 10; i++) {
            producer.sendMessages();
        }
        context.close();
    }
}