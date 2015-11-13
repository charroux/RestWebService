package dto;

import java.io.Serializable;

import model.Car;

public class CarDTO implements Serializable{
	
	String plateNumber;

	public CarDTO(Car car) {
		plateNumber = car.getPlateNumber();
	}
	
	public CarDTO(){
	}

	public CarDTO(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	@Override
	public String toString() {
		return "CarDTO [plateNumber=" + plateNumber + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((plateNumber == null) ? 0 : plateNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarDTO other = (CarDTO) obj;
		if (plateNumber == null) {
			if (other.plateNumber != null)
				return false;
		} else if (!plateNumber.equals(other.plateNumber))
			return false;
		return true;
	}

	
}
