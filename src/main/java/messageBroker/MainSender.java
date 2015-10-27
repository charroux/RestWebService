package messageBroker;

import javax.jms.DeliveryMode;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainSender {

	public static void main(String[] args) {
		try{
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextJMS.xml");
			QueueConnectionFactory factory = (QueueConnectionFactory) applicationContext.getBean("connectionFactory");
			QueueConnection connection = factory.createQueueConnection() ;
			QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = (Queue) applicationContext.getBean("queue");
			QueueSender sender = session.createSender(queue) ;
			//connection.start();
			TextMessage message = session.createTextMessage("Bonjour");
			sender.send(message);
			
			//session.close();
			//connection.stop();
			//connection.close();
			
			
			System.out.println(message);
		}catch(Exception e){
			e.printStackTrace();
		}
		


	}

}
