package rentingService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import messageBroker.RentMessageTopicPublisher;
import messageBroker.RentMessageTopicPublisherImpl;
import model.Car;

import org.eclipse.persistence.jpa.jpql.Assert.AssertException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import dto.CarDTO;

public class RentalTest {
	
	class RentMessageTopicPublisherMock implements RentMessageTopicPublisher{

		@Override
		public void publish(Car car) throws Exception {
		}
		
	}
	
	@Before
	public void init(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("inMemoryManager");
		EntityManager entityManager = emf.createEntityManager();
		
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		
		Car car = new Car();
		car.setPlateNumber("11AA22");
		car.setRented(false);	
		
		entityManager.persist(car);
		
		car = new Car();
		car.setPlateNumber("22BB33");
		car.setRented(false);
		
		entityManager.persist(car);
		
		tx.commit();
	}
	
	@Test
	public void test(){
		try {
			Rental rental = new RentalImpl("inMemoryManager", new RentMessageTopicPublisherMock());
			List<CarDTO> cars = rental.unrentedCars();
			assertNotNull(cars);
			
			CarDTO car = cars.get(0);
			assertNotNull(car);
			
			String plateNumber = car.getPlateNumber();
			assertNotNull(plateNumber);
			
			CarDTO car1 = rental.carSpecifications(plateNumber);
			assertNotNull(car1);
			assertEquals(car1.getPlateNumber(),car.getPlateNumber());
			
			rental.rent(car1);
			cars = rental.unrentedCars();
			assertNotNull(cars);
			assertFalse(cars.contains(car1));
			
			rental.getBack(car1);
			cars = rental.unrentedCars();
			assertNotNull(cars);
			assertTrue(cars.contains(car1));
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail(e.getMessage());
		}
	}

}
