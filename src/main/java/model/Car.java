package model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Car implements Serializable{
	
	String plateNumber;
	boolean rented;

	@Id
	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;

	}

	public boolean isRented() {
		return rented;
	}

	public void setRented(boolean rented) {
		this.rented = rented;
	}

	@Override
	public String toString() {
		return "Car [plateNumber=" + plateNumber + ", rented=" + rented + "]";
	}
	
	

}
