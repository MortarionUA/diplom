package com.gmail.dmytro.backend.data.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
public class Product extends AbstractEntity {

	private String name;

//	@Embedded
//	private Quantity price;

	private double volume;

	private double weight;

	private double value;

	private String hazmatCode;

	private String hazmatGroup;

	private String hazmatClass;

	public String getHazmatCode() {
		return hazmatCode;
	}

	public void setHazmatCode(String hazmatCode) {
		this.hazmatCode = hazmatCode;
	}

	public String getHazmatGroup() {
		return hazmatGroup;
	}

	public void setHazmatGroup(String hazmatGroup) {
		this.hazmatGroup = hazmatGroup;
	}

	public String getHazmatClass() {
		return hazmatClass;
	}

	public void setHazmatClass(String hazmatClass) {
		this.hazmatClass = hazmatClass;
	}

	public Product() {
		// Empty constructor is needed by Spring Data / JPA
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}
