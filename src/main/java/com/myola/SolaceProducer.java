package com.myola;

import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;

/**
 * Created by olath on 2017-02-14.
 */
public class SolaceProducer {
    private final JmsTemplate jmsTemplate;

    public SolaceProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessages() throws JMSException {
        jmsTemplate.send(session -> session.createTextMessage("test"));
    }
}
