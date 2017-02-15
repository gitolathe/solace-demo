package com.myola;

import com.solacesystems.integration.spring.MessageProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.jndi.JndiTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

/**
 * Created by olath on 2017-02-14.
 */
@Configuration
public class SenderConfig {

    @Bean("destination")
    public JndiObjectFactoryBean createDestinationJndiBean(JndiTemplate jndiTemplate,
                                                           @Value("${sender.destination}") String destination) {
        final JndiObjectFactoryBean factoryBean = new JndiObjectFactoryBean();
        factoryBean.setJndiTemplate(jndiTemplate);
        factoryBean.setJndiName(destination);
        return factoryBean;
    }

    @Bean("jmsTemplate")
    public JmsTemplate createSenderTemplate(ConnectionFactory connectionFactory,
                                            Destination destination) {
        final JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory);
        jmsTemplate.setDefaultDestination(destination);
        jmsTemplate.setDeliveryPersistent(true);
        jmsTemplate.setExplicitQosEnabled(true);
        return jmsTemplate;
    }

    @Bean("")
    public SolaceProducer createMessageProducer(JmsTemplate jmsTemplate) {
        return new SolaceProducer();
    }
}
