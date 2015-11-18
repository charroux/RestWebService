package web;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import messageBroker.RentMessageTopicPublisherImpl;
import model.Car;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import rentingService.Rental;
import rentingService.RentalImpl;
import dto.CarDTO;

@Controller
public class MyRentController implements RentService{

	Logger logger = Logger.getLogger(web.MyRentController.class);
	Rental rental;
	
	public MyRentController() throws Exception{
		rental = new RentalImpl("dataBaseManager", new RentMessageTopicPublisherImpl());  		
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
		return rental.unrentedCars();
	}

	/**
	* Return specifications of a car.
	* @param plateNumber
	* @return car specifications
	* @throws Exception no car with this plate number
	*/
	@RequestMapping(value = "/car/{plateNumber}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@Override
	public CarDTO getCar(@PathVariable("plateNumber") String plateNumber) throws Exception {
		return rental.carSpecifications(plateNumber);
	}

	/**
	 * Rent or get back a car depending on the parameter rent.
	 * @param plateNumber
	 * @param rent true for renting and false to get back
	 * @throws Exception no car with this plate number or already rented
	 */
	@RequestMapping(value = "/car/{plateNumber}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	@Override
	public void rentCar(@PathVariable("plateNumber") String plateNumber, @RequestParam(value="rent", required = false)boolean rent) throws Exception {
		if(rent == true){
			rental.rent(new CarDTO(plateNumber));
		} else {
			rental.getBack(new CarDTO(plateNumber));
		}
		
	}

	/**
	 * Create a new car
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/car/{plateNumber}}", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@Override
	public void createACar(HttpServletRequest req, HttpServletResponse resp, @PathVariable("plateNumber") String plateNumber, String a) throws Exception {
		rental.newCar();
	}

	/**
	 * Delete a car
	 * @param plateNumber
	 * @throws Exception no car with this plate number
	 */
	@RequestMapping(value = "/car/{plateNumber}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	@Override
	public void deleteACar(@PathVariable("plateNumber") String plateNumber) throws Exception {
		rental.removeACar(plateNumber);
	}

}
