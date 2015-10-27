package messageBroker;

import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainAsynchronousReceiver {

	public static void main(String[] args) {
		try{
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextJMS.xml");
			QueueConnectionFactory factory = (QueueConnectionFactory) applicationContext.getBean("connectionFactory");
			QueueConnection connection = factory.createQueueConnection() ;
			QueueSession session = connection.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
			Queue queue = (Queue) applicationContext.getBean("queue");
			QueueReceiver receiver = session.createReceiver(queue) ;
			receiver.setMessageListener( new RentMessageListener() ) ;
			connection.start();
		}catch(Exception e){
			e.printStackTrace();
		}
		


	}

}
