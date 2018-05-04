package com.gmail.dmytro.backend.service;

import com.gmail.dmytro.backend.BookingRepository;
import com.gmail.dmytro.backend.LocationRepository;
import com.gmail.dmytro.backend.OrderRepository;
import com.gmail.dmytro.backend.data.DeliveryStats;
import com.gmail.dmytro.backend.data.OrderState;
import com.gmail.dmytro.backend.data.entity.HistoryItem;
import com.gmail.dmytro.backend.data.entity.Order;
import com.gmail.dmytro.backend.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final LocationRepository locationRepository;
	private final BookingRepository bookingRepository;

	private static Set<OrderState> notAvailableStates;

	static {
		notAvailableStates = new HashSet<>(Arrays.asList(OrderState.values()));
		notAvailableStates.remove(OrderState.DELIVERED);
		notAvailableStates.remove(OrderState.BOOKED);
		notAvailableStates.remove(OrderState.READY);
		notAvailableStates.remove(OrderState.CANCELLED);
	}

	@Autowired
	public OrderService(OrderRepository orderRepository, LocationRepository locationRepository, BookingRepository bookingRepository) {
		this.orderRepository = orderRepository;
		this.locationRepository = locationRepository;
		this.bookingRepository = bookingRepository;
	}

	public Order findOrder(Long id) {
		return orderRepository.findOne(id);
	}

	public Order changeState(Order order, OrderState state, User user) {
		if (order.getState() == state) {
			throw new IllegalArgumentException("Order state is already " + state);
		}
		order.setState(state);
		addHistoryItem(order, state, user);

		return orderRepository.save(order);
	}

	private void addHistoryItem(Order order, OrderState newState, User user) {
		String comment = "Order " + newState.getDisplayName();

		HistoryItem item = new HistoryItem(user, comment);
		item.setNewState(newState);
		if (order.getHistory() == null) {
			order.setHistory(new ArrayList<>());
		}
		order.getHistory().add(item);
	}

	@Transactional(rollbackOn = Exception.class)
	public Order saveOrder(Order order, User user) {
		if (order.getHistory() == null) {
			String comment = "Order placed";
			order.setHistory(new ArrayList<>());
			HistoryItem item = new HistoryItem(user, comment);
			item.setNewState(OrderState.NEW);
			order.getHistory().add(item);
		}
		if(order.getDelivery() != null) {
			order.setDelivery(locationRepository.save(order.getDelivery()));
		}
		if(order.getPickup() != null) {
			order.setPickup(locationRepository.save(order.getPickup()));
		}
		if(order.getBooking() != null) {
			order.setBooking(bookingRepository.save(order.getBooking()));
		}
		return orderRepository.save(order);
	}

	public Order addHistoryItem(Order order, String comment, User user) {
		HistoryItem item = new HistoryItem(user, comment);

		if (order.getHistory() == null) {
			order.setHistory(new ArrayList<>());
		}

		order.getHistory().add(item);

		return orderRepository.save(order);
	}

	public Page<Order> findAnyMatchingAfterCreateDate(Optional<String> optionalFilter,
			Optional<LocalDate> optionalFilterDate, Pageable pageable) {
		if (optionalFilter.isPresent()) {
			if (optionalFilterDate.isPresent()) {
				return orderRepository.findByCustomerFullNameContainingIgnoreCaseAndCreateDateAfter(optionalFilter.get(),
						optionalFilterDate.get(), pageable);
			} else {
				return orderRepository.findByCustomerFullNameContainingIgnoreCase(optionalFilter.get(), pageable);
			}
		} else {
			if (optionalFilterDate.isPresent()) {
				return orderRepository.findBycreateDateAfter(optionalFilterDate.get(), pageable);
			} else {
				return orderRepository.findAll(pageable);
			}
		}
	}

	public long countAftercreateDateWithState(LocalDate filterDate, List<OrderState> states) {
		return orderRepository.countBycreateDateAfterAndStateIn(filterDate, states);
	}

	public long countAnyMatchingAfterCreateDate(Optional<String> optionalFilter, Optional<LocalDate> optionalFilterDate) {
		if (optionalFilter.isPresent() && optionalFilterDate.isPresent()) {
			return orderRepository.countByCustomerFullNameContainingIgnoreCaseAndCreateDateAfter(optionalFilter.get(),
					optionalFilterDate.get());
		} else if (optionalFilter.isPresent()) {
			return orderRepository.countByCustomerFullNameContainingIgnoreCase(optionalFilter.get());
		} else if (optionalFilterDate.isPresent()) {
			return orderRepository.countBycreateDateAfter(optionalFilterDate.get());
		} else {
			return orderRepository.count();
		}
	}

	private DeliveryStats getDeliveryStats() {
		DeliveryStats stats = new DeliveryStats();
		LocalDate today = LocalDate.now();
		stats.setDueToday((int) orderRepository.countBycreateDate(today));
		stats.setDueTomorrow((int) orderRepository.countBycreateDate(today.plusDays(1)));
		stats.setDeliveredToday(
				(int) orderRepository.countBycreateDateAndStateIn(today, Collections.singleton(OrderState.DELIVERED)));

		stats.setNotAvailableToday((int) orderRepository.countBycreateDateAndStateIn(today, notAvailableStates));
		stats.setNewOrders((int) orderRepository.countByState(OrderState.NEW));

		return stats;
	}

	private List<Number> getDeliveriesPerDay(int month, int year) {
		int daysInMonth = YearMonth.of(year, month).lengthOfMonth();
		return flattenAndReplaceMissingWithNull(daysInMonth,
				orderRepository.countPerDay(OrderState.DELIVERED, year, month));
	}

	private List<Number> getDeliveriesPerMonth(int year) {
		return flattenAndReplaceMissingWithNull(12, orderRepository.countPerMonth(OrderState.DELIVERED, year));
	}

	private List<Number> flattenAndReplaceMissingWithNull(int length, List<Object[]> list) {
		List<Number> counts = new ArrayList<>();
		for (int i = 0; i < length; i++) {
			counts.add(null);
		}

		for (Object[] result : list) {
			counts.set((Integer) result[0] - 1, (Number) result[1]);
		}
		return counts;
	}

}
