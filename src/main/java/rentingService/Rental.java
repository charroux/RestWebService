package rentingService;

import java.util.List;

import dto.CarDTO;

public interface Rental {
	
	/**
	 * Create e new car
	 * @return the new car
	 */
	CarDTO newCar();
	
	/**
	 * Remove a car
	 * @param plateNumber
	 * @throws Exception no car with such a plate number
	 */
	void removeACar(String plateNumber) throws Exception;
	
	/**
	*
	* @return all cars not rented
	*/
	List<CarDTO> unrentedCars();
	
	/**
	* Return specifications of a car.
	* @param plateNumber
	* @return car specifications
	* @throws Exception no car with such a plate number
	*/
	CarDTO carSpecifications(String plateNumber) throws Exception;
	
	/**
	 * Rent a car
	 * @param car to be rented
	 * @throws Exception no car with such a plate number or already rented
	 */
	void rent(CarDTO car) throws Exception;
	
	/**
	 * Get back a car
	 * @param car
	 * @throws Exception no car with this plate number or not rented
	 */
	void getBack(CarDTO car) throws Exception;
	
}
