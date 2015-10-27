package messageBroker;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

public class RentMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
			ObjectMessage car = (ObjectMessage)message;
			System.out.println("Message recu: " + car.getObject());
			message.acknowledge();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
