package org.example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.ejb3.annotation.DeliveryActive;
import org.jboss.ejb3.annotation.ResourceAdapter;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.*;

@MessageDriven(name = "PayTmMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/PaytmQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "user", propertyValue = "admin"),
        @ActivationConfigProperty(propertyName = "password", propertyValue = "admin"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
@ResourceAdapter(value="remote-artemis")
@DeliveryActive(true)

public class PaytmMDB implements MessageListener {
    private static Log logger = LogFactory.getLog(PaytmMDB.class);
    @Override
    public void onMessage(Message message) {
        logger.debug("Got message {}");
        if(message instanceof ObjectMessage){
            ObjectMessage objectMessage = (ObjectMessage)message;
            try {
                PayTmDTO dto = objectMessage.getBody(PayTmDTO.class);
                logger.debug("Got message paytm:"+dto);
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        }
    }
}