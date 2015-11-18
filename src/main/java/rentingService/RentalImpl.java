package rentingService;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import messageBroker.RentMessageTopicPublisher;
import messageBroker.RentMessageTopicPublisherImpl;
import model.Car;
import dto.CarDTO;

public class RentalImpl implements Rental{

	Logger logger = Logger.getLogger(rentingService.RentalImpl.class);
	EntityManager entityManager;
	RentMessageTopicPublisher rentMessageTopicPublisher;
	
	public RentalImpl(String dataBaseManager, RentMessageTopicPublisher rentMessageTopicPublisher) throws Exception {
		super();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(dataBaseManager);
		entityManager = emf.createEntityManager();
		Query query = entityManager.createQuery("select cars from Car cars");
		List<Car> cars = query.getResultList();
		if(cars.size() == 0){
			this.newCar();
			this.newCar();
		}
		this.rentMessageTopicPublisher = rentMessageTopicPublisher;
	}

	@Override
	public List<CarDTO> unrentedCars() {
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

	@Override
	public CarDTO carSpecifications(String plateNumber) throws Exception {
		Query query = entityManager.createQuery("select car from Car car where car.plateNumber like :plate").setParameter("plate", plateNumber);
		Car car = (Car) query.getSingleResult();
		if(car != null){
			return new CarDTO(car);
		} else {
			throw new Exception("No car with such a plate number");
		}
	}

	@Override
	public void rent(CarDTO car) throws Exception {
		Query query = entityManager.createQuery("select car from Car car where car.plateNumber like :plate").setParameter("plate", car.getPlateNumber());
		Car carModel = (Car) query.getSingleResult();
		if(carModel != null){
			if(carModel.isRented() == false){
				
				carModel.setRented(true);
				
				rentMessageTopicPublisher.publish(carModel);
				
				logger.log(Level.INFO, "Message sent to the message broker: " + car);
				
			} else {
				throw new Exception("Car already rented");
			}
		} else {
			throw new Exception("No car with such a plate number");
		}
	}

	@Override
	public void getBack(CarDTO car) throws Exception {
		Query query = entityManager.createQuery("select car from Car car where car.plateNumber like :plate").setParameter("plate", car.getPlateNumber());
		Car carModel = (Car) query.getSingleResult();
		if(carModel != null){
			if(carModel.isRented() == true){
				carModel.setRented(false);
			} 
		} else {
			throw new Exception("No car with such a plate number");
		}
	}

	@Override
	public CarDTO newCar() {
		int plateNumber = (int) (Math.random()*1000);
		Car car = new Car();
		car.setPlateNumber(new Integer(plateNumber).toString());
		car.setRented(false);
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(car);
		tx.commit();
		return new CarDTO(car);
	}

	@Override
	public void removeACar(String plateNumber) throws Exception {
		Query query = entityManager.createQuery("select car from Car car where car.plateNumber like :plate").setParameter("plate", plateNumber);
		Car car = (Car) query.getSingleResult();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.remove(car);
		tx.commit();
		
	}

}
