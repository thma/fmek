package org.fmek;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;


@Configuration
public class JmsConfiguration {

    @Bean
    public Log log() {
        Log log = LogFactory.getLog("FMEK");
        log.info("serve Log");
        return log;
    }

    @Bean
    public ConnectionFactory connectionFactory(Log log) {
        log.info("serve ConnectionFactory");
        return new ActiveMQConnectionFactory("vm://localhost");
    }

    @Bean
    public DefaultMessageListenerContainer defaultMessageListenerContainer(MessageListener messageListener, ConnectionFactory connectionFactory, Log log) {
        log.info("serve DefaultMessageListenerContainer");
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setDestinationName("test");
        container.setMessageListener(messageListener);
        return container;
    }

}