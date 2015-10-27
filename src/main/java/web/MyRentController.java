package web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jms.ConnectionFactory;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Car;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import dto.CarDTO;

@Controller
public class MyRentController implements RentService{

	EntityManager entityManager;
	QueueSender sender;
	QueueSession session;
	
	public MyRentController() throws Exception{
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("dataBaseManager");
  		entityManager = emf.createEntityManager();
  		
  		/*ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextJMS.xml");
  		
  		QueueConnectionFactory connectionFactory = (QueueConnectionFactory)applicationContext.getBean("connectionFactory");
  		QueueConnection connection = connectionFactory.createQueueConnection();
		session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = (Queue) applicationContext.getBean("queue");
		sender = session.createSender(queue);
		connection.start();*/
		
	}

	/**
	*
	* @return all cars not rented
	*/
	@RequestMapping(value = "/car", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@Override
	public List<CarDTO> getCars() {
		Query query = entityManager.createQuery("select cars from Car cars");
		List<Car> cars = query.getResultList();
		CarDTO carDTO;
		List<CarDTO> dtos = new ArrayList<CarDTO>();
		for(Car car : cars){
			if(car.isRented() == false){
				carDTO = new CarDTO(car);
				dtos.add(carDTO);
			}
		}
		return dtos;
	}

	/**
	* Return specifications of a car.
	* @param plateNumber
	* @return car specifications only (if not rented)
	* @throws Exception no car with this plate number
	*/
	@RequestMapping(value = "/car/{plateNumber}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@Override
	public CarDTO getCar(@PathVariable("plateNumber") String plateNumber) throws Exception {
		Query query = entityManager.createQuery("select cars from Car cars");
		List<Car> cars = query.getResultList();
		int  i = 0;
		while(i<cars.size() && cars.get(i).getPlateNumber().equals(plateNumber)==false){
			i++;
		}
		if(i < cars.size()){
			return new CarDTO(cars.get(i));
		} else {
			throw new Exception("No car with such a plate number");
		}
	}

	/**
	* Rent a car.
	* @param plateNumber
	* @return car specifications
	* @throws Exception no car with this plate number or already rented
	*/
	@RequestMapping(value = "/car/{plateNumber}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	@Override
	public void rentCar(@PathVariable("plateNumber") String plateNumber) throws Exception {
		Query query = entityManager.createQuery("select cars from Car cars");
		List<Car> cars = query.getResultList();
		int  i = 0;
		while(i<cars.size() && cars.get(i).getPlateNumber().equals(plateNumber)==false){
			i++;
		}
		Car car;
		if(i < cars.size()){
			if(cars.get(i).isRented() == false){
				car = cars.get(i); 
				car.setRented(true);
				ObjectMessage objectMessage = session.createObjectMessage(car);
				sender.send(objectMessage);
			} else {
				throw new Exception("Car already rented");
			}
		} else {
			throw new Exception("No car with such a plate number");
		}
	}

	/**
	*
	** @return actions to be done
	* @throws Exception no car with this plate number or not rented
	*/
	@RequestMapping(value = "/car/{plateNumber}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	@Override
	public void renderCar(@PathVariable("plateNumber") String plateNumber) throws Exception {
		Query query = entityManager.createQuery("select cars from Car cars");
		List<Car> cars = query.getResultList();
		int  i = 0;
		while(i<cars.size() && cars.get(i).getPlateNumber().equals(plateNumber)==false){
			i++;
		}
		if(i < cars.size()){
			if(cars.get(i).isRented() == true){
				cars.get(i).setRented(false);
			} 
		} else {
			throw new Exception("No car with such a plate number");
		}
	}

}
