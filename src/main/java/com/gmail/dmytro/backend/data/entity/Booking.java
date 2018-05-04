package com.gmail.dmytro.backend.data.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Booking extends AbstractEntity {
    private String bookingNumber;
    private LocalDate pickupDate;
    private LocalTime pickupTime;
    private LocalDate deliveryDate;
    private LocalTime deliveryTime;
    private LocalDate cutOffDate;
    private LocalTime cutOffTime;
    @ManyToOne
    private Port loadPort;
    @ManyToOne
    private Port unloadPort;
    @ManyToOne
    private Carrier carrier;
    private String vesselName;
    private String vesselFlag;
    private String voyageNumber;
    private double freightCost;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderColumn(name = "id")
    private List<EquipmentLine> equipmentLineList;

    public List<EquipmentLine> getEquipmentLineList() {
        return equipmentLineList;
    }

    public void setEquipmentLineList(List<EquipmentLine> equipmentLineList) {
        this.equipmentLineList = equipmentLineList;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public LocalTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public LocalDate getCutOffDate() {
        return cutOffDate;
    }

    public void setCutOffDate(LocalDate cutOffDate) {
        this.cutOffDate = cutOffDate;
    }

    public LocalTime getCutOffTime() {
        return cutOffTime;
    }

    public void setCutOffTime(LocalTime cutOffTime) {
        this.cutOffTime = cutOffTime;
    }

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public String getVesselFlag() {
        return vesselFlag;
    }

    public void setVesselFlag(String vesselFlag) {
        this.vesselFlag = vesselFlag;
    }

    public String getVoyageNumber() {
        return voyageNumber;
    }

    public void setVoyageNumber(String voyageNumber) {
        this.voyageNumber = voyageNumber;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public Port getLoadPort() {
        return loadPort;
    }

    public void setLoadPort(Port loadPort) {
        this.loadPort = loadPort;
    }

    public Port getUnloadPort() {
        return unloadPort;
    }

    public void setUnloadPort(Port unloadPort) {
        this.unloadPort = unloadPort;
    }

    public double getFreightCost() {
        return freightCost;
    }

    public void setFreightCost(double freightCost) {
        this.freightCost = freightCost;
    }
}
