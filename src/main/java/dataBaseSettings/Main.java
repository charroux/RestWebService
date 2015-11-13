package dataBaseSettings;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import model.Car;

public class Main {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("dataBaseManager");
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
		
		car = new Car();
		car.setPlateNumber("33CC44");
		car.setRented(false);
		
		entityManager.persist(car);
		
		car = new Car();
		car.setPlateNumber("44DD55");
		car.setRented(false);
		
		entityManager.persist(car);

		tx.commit();
	}

}
