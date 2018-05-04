package com.gmail.dmytro.backend.data.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Quantity implements Serializable {

    private Double value;

    private String unit;

    public Quantity(Double value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    public Quantity() {
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
