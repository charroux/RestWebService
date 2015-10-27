package dto;

import model.Car;

public class CarDTO {
	
	String plateNumber;

	public CarDTO(Car car) {
		plateNumber = car.getPlateNumber();
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

}
