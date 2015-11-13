package messageBroker;

import model.Car;

public interface RentMessageTopicPublisher {
	
	public void publish(Car car) throws Exception;

}
