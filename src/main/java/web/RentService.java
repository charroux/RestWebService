package web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import dto.CarDTO;

public interface RentService {
	
	/**
	*
	* @return all cars not rented
	*/
	@RequestMapping(value = "/car", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<CarDTO> getCars();
	
	/**
	* Return specifications of a car.
	* @param plateNumber
	* @return car specifications only (if not rented)
	* @throws Exception no car with this plate number or already rented
	*/
	@RequestMapping(value = "/car/{plateNumber}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public CarDTO getCar(@PathVariable("plateNumber") String plateNumber) throws Exception;
	
	/**
	 * Rent or get back a car depending on the parameter rent.
	 * @param plateNumber
	 * @param rent true for renting and false to get back
	 * @throws Exception no car with this plate number or already rented
	 */
	@RequestMapping(value = "/car/{plateNumber}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void rentCar(@PathVariable("plateNumber") String plateNumber, @RequestParam(value="rent", required = false)boolean rent) throws Exception;

	/**
	 * Create a new car
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/car/{plateNumber}}", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void createACar(HttpServletRequest req, HttpServletResponse resp, @PathVariable("plateNumber") String plateNumber, String a) throws Exception;
	
	
	/**
	 * Delete a car
	 * @param plateNumber
	 * @throws Exception no car with this plate number
	 */
	@RequestMapping(value = "/car/{plateNumber}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteACar(@PathVariable("plateNumber") String plateNumber) throws Exception;

}
