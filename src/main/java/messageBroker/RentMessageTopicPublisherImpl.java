package messageBroker;

import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import model.Car;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dto.CarDTO;

public class RentMessageTopicPublisherImpl implements RentMessageTopicPublisher{

	TopicConnectionFactory factory;
	Topic topic;
	
	public RentMessageTopicPublisherImpl() throws Exception{
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextJMS.xml");
		factory = (TopicConnectionFactory) applicationContext.getBean("connectionFactory");
		topic = (Topic) applicationContext.getBean("topic");
	}
	
	public void publish(Car car) throws Exception{
		TopicConnection connection = factory.createTopicConnection();
		TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		TopicPublisher publisher = session.createPublisher(topic) ;
		ObjectMessage message = session.createObjectMessage(new CarDTO(car));
		publisher.publish(message);
		session.close();
		connection.stop();
		connection.close();
	}

}
