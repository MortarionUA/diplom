package com.gmail.dmytro.ui.view.orderedit;

import com.gmail.dmytro.backend.data.OrderState;
import com.gmail.dmytro.backend.data.entity.EquipmentLine;
import com.gmail.dmytro.backend.service.OrderService;
import com.gmail.dmytro.ui.navigation.NavigationManager;
import com.vaadin.data.HasValue;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Component.Focusable;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.gmail.dmytro.app.HasLogger;
import com.gmail.dmytro.app.security.SecurityUtils;
import com.gmail.dmytro.backend.data.entity.Customer;
import com.gmail.dmytro.backend.data.entity.Line;
import com.gmail.dmytro.backend.data.entity.Order;
import com.gmail.dmytro.backend.service.LocationService;
import com.gmail.dmytro.backend.service.UserService;
import com.gmail.dmytro.ui.view.storefront.StorefrontView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.vaadin.spring.events.EventBus.ViewEventBus;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;

import javax.annotation.PreDestroy;
import javax.validation.ValidationException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@SpringComponent
@ViewScope
public class OrderEditPresenter implements Serializable, HasLogger {

	private OrderEditView view;

	private final OrderService orderService;
	private final UserService userService;

	private final LocationService locationService;

	private final NavigationManager navigationManager;

	private final ViewEventBus viewEventBus;

	private static final List<OrderState> happyPath = Arrays.asList(OrderState.NEW,OrderState.CONFIRMED, OrderState.READY_TO_BOOK, OrderState.BOOKED,
			OrderState.READY, OrderState.DELIVERED);

	@Autowired
	public OrderEditPresenter(ViewEventBus viewEventBus, NavigationManager navigationManager, OrderService orderService,
							  UserService userService, LocationService locationService) {
		this.viewEventBus = viewEventBus;
		this.navigationManager = navigationManager;
		this.orderService = orderService;
		this.userService = userService;
		this.locationService = locationService;
		viewEventBus.subscribe(this);
	}

	@PreDestroy
	public void destroy() {
		viewEventBus.unsubscribe(this);
	}

	@EventBusListenerMethod
	private void onProductInfoChange(ProductInfoChangeEvent event) {
		updateTotalSum();
		updateFreightCost();
		view.onProductInfoChanged();
	}

	@EventBusListenerMethod
	private void onOrderItemDelete(LineDeletedEvent event) {
		removeLine(event.getOrderItem());
		view.onProductInfoChanged();
	}

	@EventBusListenerMethod
	private void onOrderItemUpdate(OrderUpdatedEvent event) {
		refresh(view.getOrder().getId());
	}

	@EventBusListenerMethod
	private void onEquimentDelete(EquipmentDeletedEvent event) {
		removeEquipment(event.getOrderItem());
		view.onProductInfoChanged();
	}

	@EventBusListenerMethod
	private void onFreightCostUpdate(FreightCostUpdatedEvent event) {
		updateFreightCost();
		refresh(view.getOrder().getId());
	}

	void init(OrderEditView view) {
		this.view = view;
	}

	/**
	 * Called when the user enters the view.
	 */
	public void enterView(Long id) {
		Order order;
		if (id == null) {
			// New
			order = new Order();
			order.setState(OrderState.NEW);
			order.setItems(new ArrayList<>());
			order.setCustomer(new Customer());
			order.setCreateDate(LocalDate.now().plusDays(1));
			order.setCreateTime(LocalTime.now());
		} else {
			order = orderService.findOrder(id);
			if (order == null) {
				view.showNotFound();
				return;
			}
		}

		refreshView(order);
	}

	private void updateTotalSum() {
		double sum = view.getOrder().getItems().stream().filter(item -> item.getProduct() != null)
				.collect(Collectors.summingDouble(item -> item.getProduct().getValue() * item.getCount()));
		view.setSum(sum);
	}

	private void updateFreightCost() {
		double sumA = view.getOrder().getItems().stream().filter(item -> item.getProduct() != null)
				.collect(Collectors.summingDouble(item -> item.getProduct().getValue() * item.getCount()));
		double sumB = view.getOrder().getBooking().getFreightCost()*100;
		view.setFreightCost(sumA, sumB);

	}

	public void editBackCancelPressed() {
		if (view.getMode() == OrderEditView.Mode.REPORT) {
			// Edit order
			view.setMode(OrderEditView.Mode.EDIT);
		} else if (view.getMode() == OrderEditView.Mode.CONFIRMATION) {
			// Back to edit
			view.setMode(OrderEditView.Mode.EDIT);
		} else if (view.getMode() == OrderEditView.Mode.EDIT || view.getMode() == OrderEditView.Mode.BOOKING) {
			// Cancel edit
			Long id = view.getOrder().getId();
			if (id == null) {
				navigationManager.navigateTo(StorefrontView.class);
			} else {
				enterView(id);
			}
		}
	}

	public void okPressed() {
		System.out.println("okPressed:" +view.getMode());
		if (view.getMode() == OrderEditView.Mode.REPORT) {
			// Set next state
			Order order = view.getOrder();
			if(order.getState() ==OrderState.READY_TO_BOOK) {
				view.setMode(OrderEditView.Mode.BOOKING);
			} else {
				Optional<OrderState> nextState = getNextHappyPathState(order.getState());
				if (!nextState.isPresent()) {
					throw new IllegalStateException(
							"The next state button should never be enabled when the state does not follow the happy path");
				}
				orderService.changeState(order, nextState.get(), SecurityUtils.getCurrentUser(userService));
				refresh(order.getId());
			}
		} else if (view.getMode() == OrderEditView.Mode.CONFIRMATION) {
			Order order = saveOrder();
			if (order != null) {
				// Navigate to edit view so URL is updated correctly
				navigationManager.updateViewParameter("" + order.getId());
				enterView(order.getId());
			}
		} else if (view.getMode() == OrderEditView.Mode.EDIT) {
			Optional<HasValue<?>> firstErrorField = view.validate().findFirst();
			if (firstErrorField.isPresent()) {
				((Focusable) firstErrorField.get()).focus();
				return;
			}
			// New order should still show a confirmation page
			Order order = view.getOrder();
			if (order.getId() == null) {
				filterEmptyProducts();
				view.setMode(OrderEditView.Mode.CONFIRMATION);
			} else {
				order = saveOrder();
				if (order != null) {
					refresh(order.getId());
				}
			}
		} else  if(view.getMode() == OrderEditView.Mode.BOOKING) {
			Order order = view.getOrder();
			Optional<OrderState> nextState = getNextHappyPathState(order.getState());
			if (!nextState.isPresent()) {
				throw new IllegalStateException(
						"The next state button should never be enabled when the state does not follow the happy path");
			}
			orderService.changeState(order, nextState.get(), SecurityUtils.getCurrentUser(userService));
			view.setMode(OrderEditView.Mode.REPORT);
			refresh(order.getId());

		}
	}


	private void refresh(Long id) {
		Order order = orderService.findOrder(id);
		if (order == null) {
			view.showNotFound();
			return;
		}
		refreshView(order);
	}

	private void refreshView(Order order) {
		view.setOrder(order);
		updateTotalSum();
		updateFreightCost();
		if (order.getId() == null) {
			view.setMode(OrderEditView.Mode.EDIT);
		} else {
			view.setMode(OrderEditView.Mode.REPORT);
		}
	}

	private void filterEmptyProducts() {
		LinkedList<Line> emptyRows = new LinkedList<>();
		view.getOrder().getItems().forEach(line -> {
			if (line.getProduct() == null) {
				emptyRows.add(line);
			}
		});
		emptyRows.forEach(this::removeLine);
	}

	private Order saveOrder() {
		try {
			updateFreightCost();
			filterEmptyProducts();
			Order order = view.getOrder();
			return orderService.saveOrder(order, SecurityUtils.getCurrentUser(userService));
		} catch (ValidationException e) {
			// Should not get here if validation is setup properly
			Notification.show("Please check the contents of the fields: " + e.getMessage(), Type.ERROR_MESSAGE);
			getLogger().error("Validation error during order save", e);
			return null;
		} catch (OptimisticLockingFailureException e) {
			// Somebody else probably edited the data at the same time
			Notification.show("Somebody else might have updated the data. Please refresh and try again.",
					Type.ERROR_MESSAGE);
			getLogger().debug("Optimistic locking error while saving order", e);
			return null;
		} catch (Exception e) {
			// Something went wrong, no idea what
			Notification.show("An unexpected error occurred while saving. Please refresh and try again.",
					Type.ERROR_MESSAGE);
			getLogger().error("Unable to save order", e);
			return null;
		}
	}

	public Optional<OrderState> getNextHappyPathState(OrderState current) {
		final int currentIndex = happyPath.indexOf(current);
		if (currentIndex == -1 || currentIndex == happyPath.size() - 1) {
			return Optional.empty();
		}
		return Optional.of(happyPath.get(currentIndex + 1));
	}

	private void removeLine(Line line) {
		view.removeLine(line);
		updateTotalSum();
	}
	private void removeEquipment(EquipmentLine line) {
		view.removeEquipment(line);
	}
}
