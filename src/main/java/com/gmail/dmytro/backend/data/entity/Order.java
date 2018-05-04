package com.gmail.dmytro.backend.data.entity;

import com.gmail.dmytro.backend.data.Incoterms;
import com.gmail.dmytro.backend.data.OrderState;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


// "Order" is a reserved word
@Entity(name = "OrderInfo")
@NamedEntityGraphs({ @NamedEntityGraph(name = "Order.gridData", attributeNodes = { @NamedAttributeNode("customer") }),
		@NamedEntityGraph(name = "Order.allData", attributeNodes = { @NamedAttributeNode("customer"),
				@NamedAttributeNode("items"), @NamedAttributeNode("history") }) })
public class Order extends AbstractEntity {

	@NotNull
	private LocalDate createDate;
	@NotNull
	private LocalTime createTime;
	@NotNull
	private LocalDate pickupDate;
	@NotNull
	private LocalDate deliveryDate;
	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	private Location pickup;
	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	private Location delivery;
	@ManyToOne
	private Port loadPort;
	@ManyToOne
	private Port unloadPort;
	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	private Customer customer;

	private String orderNo;

	private Incoterms incoterms;
	@NotNull
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderColumn(name = "id")
	private List<Line> items;
	@NotNull
	private OrderState state;

	@OneToOne(cascade = CascadeType.ALL)
	private Booking booking;

	private boolean paid;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderColumn(name = "id")
	private List<HistoryItem> history;

	public Order() {
		// Empty constructor is needed by Spring Data / JPA
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public LocalTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalTime createTime) {
		this.createTime = createTime;
	}

	public LocalDate getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(LocalDate pickupDate) {
		this.pickupDate = pickupDate;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Location getPickup() {
		return pickup;
	}

	public void setPickup(Location pickup) {
		this.pickup = pickup;
	}

	public Location getDelivery() {
		return delivery;
	}

	public void setDelivery(Location delivery) {
		this.delivery = delivery;
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Incoterms getIncoterms() {
		return incoterms;
	}

	public void setIncoterms(Incoterms incoterms) {
		this.incoterms = incoterms;
	}

	public List<Line> getItems() {
		return items;
	}

	public void setItems(List<Line> items) {
		this.items = items;
	}

	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public List<HistoryItem> getHistory() {
		return history;
	}

	public void setHistory(List<HistoryItem> history) {
		this.history = history;
	}
}
