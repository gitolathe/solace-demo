package com.myola;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.jndi.JndiTemplate;

import java.util.Properties;

/**
 * Created by olath on 2017-02-14.
 */
@Configuration
public class SolaceConfig {

    @Bean("solaceJndiTemplate")
    public JndiTemplate solaceJndiTemplate(@Value("${solace.url}") String url,
                                           @Value("${solace.user}") String user,
                                           @Value("${solace.vpn}") String vpn) {

        final Properties properties = new Properties();
        properties.setProperty("java.naming.provider.url", url);
        properties.setProperty("java.naming.factory.initial", "com.solacesystems.jndi.SolJNDIInitialContextFactory");
        properties.setProperty("java.naming.security.principal",
                String.format("%s@%s", user, vpn));

        return new JndiTemplate(properties);
    }

    @Bean("solaceConnectionFactory")
    public JndiObjectFactoryBean solaceJndiObjectFactoryBean(JndiTemplate jndiTemplate,
                                                             @Value("${solace.jndi.name}") String jndiName) {
        final JndiObjectFactoryBean factoryBean = new JndiObjectFactoryBean();
        factoryBean.setJndiTemplate(jndiTemplate);
        factoryBean.setJndiName(jndiName);
        return factoryBean;
    }

    @Bean("solaceCachedConnectionFactory")
    @Primary
    public CachingConnectionFactory solaceConnectionFactory(javax.jms.ConnectionFactory connectionFactory,
                                                            @Value("${solace.connector.cache.size}") Integer cacheSize) {
        final CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setTargetConnectionFactory(cachingConnectionFactory);
        cachingConnectionFactory.setSessionCacheSize(cacheSize);
        return cachingConnectionFactory;
    }
}
