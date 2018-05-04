package com.gmail.dmytro.backend.data.entity;

import javax.persistence.Entity;

@Entity
public class Carrier extends AbstractEntity {

    private String code;

    private String name;

    private String type;

    private double massCap;

    private double volumeCap;

    private double speed;

    private double moneyMultiplier;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
