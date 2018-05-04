package com.gmail.dmytro.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class EquipmentLine extends AbstractEntity{


    private String info;

    private String equipmentCount;

    @ManyToOne
    private Equipment equipment;

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getEquipmentCount() {
        return equipmentCount;
    }

    public void setEquipmentCount(String equipmentCount) {
        this.equipmentCount = equipmentCount;
    }
}
