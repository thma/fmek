package org.fmek.example.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fmek.example.domain.Greeting;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by thma on 17.09.2015.
 */
@Named
@MessageDriven(
    activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/test"),
        @ActivationConfigProperty(propertyName = "useJndi", propertyValue = "false")
    })
public class GreetingReceiverMDB implements MessageListener {

  Log log = LogFactory.getLog(getClass());;

  @Inject
  GreetingCodec deserializer;

  @Override
  public void onMessage(Message message) {

    TextMessage textMessage = (TextMessage) message;
    try {
      Greeting greeting = deserializer.stringToGreeting(textMessage.getText());
      log.info("onMessage(" + greeting + ")");
    } catch (JMSException e) {
      log.error(e);
    }

  }
}
