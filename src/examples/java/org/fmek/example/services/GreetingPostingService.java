package org.fmek.example.services;

import org.fmek.example.domain.Greeting;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.*;
import javax.transaction.Transactional;

/**
 * Created by thma on 16.09.2015.
 */
@Named
@Transactional
public class GreetingPostingService {

    @Inject
    GreetingCodec codec;

    @Inject
    ConnectionFactory connectionFactory;

    public void sendGreeting(Greeting greeting) throws JMSException {
        Connection con = connectionFactory.createConnection();
        Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue("test");
        MessageProducer msgProducer = session.createProducer(null);
        Message msg = session.createTextMessage(codec.greetingToString(greeting));

        msgProducer.send(destination, msg);
    }

}
