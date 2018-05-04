package com.gmail.dmytro.backend.data.entity;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
public class Ship extends AbstractEntity {

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String type;

    @Min(0)
    @Max(1000000000)
    private double massCap;

    @Min(0)
    @Max(1000000000)
    private double volumeCap;

    @Min(0)
    @Max(1000)
    private double speed;

    @Min(1)
    @Max(3)
    private double moneyMultiplier;

    public Ship() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMassCap() {
        return massCap;
    }

    public void setMassCap(double massCap) {
        this.massCap = massCap;
    }

    public double getVolumeCap() {
        return volumeCap;
    }

    public void setVolumeCap(double volumeCap) {
        this.volumeCap = volumeCap;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getMoneyMultiplier() {
        return moneyMultiplier;
    }

    public void setMoneyMultiplier(double moneyMultiplier) {
        this.moneyMultiplier = moneyMultiplier;
    }
}
