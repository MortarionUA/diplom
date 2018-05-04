package com.gmail.dmytro.ui.view.orderedit;

import com.gmail.dmytro.backend.data.OrderState;
import com.gmail.dmytro.backend.data.entity.*;
import com.gmail.dmytro.ui.components.ConfirmPopup;
import com.gmail.dmytro.ui.util.DollarPriceConverter;
import com.vaadin.annotations.PropertyId;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValueContext;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewBeforeLeaveEvent;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg.ERROR_MSG;

@SpringView(name = "order")
public class OrderEditView extends OrderEditViewDesign implements View {

	public enum Mode {
		EDIT, REPORT, CONFIRMATION, BOOKING;
	}

	private final OrderEditPresenter presenter;

	private final DollarPriceConverter priceConverter;

	private BeanValidationBinder<Order> binder;

	private final StringToDoubleConverter stringToDoubleConverter = new StringToDoubleConverter(ERROR_MSG);
	private Mode mode;

	private boolean hasChanges;

	private final BeanFactory beanFactory;

	@Autowired
	public OrderEditView(OrderEditPresenter presenter, BeanFactory beanFactory, DollarPriceConverter priceConverter) {
		this.presenter = presenter;
		this.beanFactory = beanFactory;
		this.priceConverter = priceConverter;
	}

	@PostConstruct
	public void init() {
		presenter.init(this);

		createDate.setDefaultValue(ZonedDateTime.now().toLocalDate());

		createTime.setItems(IntStream.range(0, 23).mapToObj(h -> LocalTime.of(h, 0)));
		bookedDeliveryTime.setItems(IntStream.range(0, 23).mapToObj(h -> LocalTime.of(h, 0)));
		bookedPickupTime.setItems(IntStream.range(0, 23).mapToObj(h -> LocalTime.of(h, 0)));
		cutOffTime.setItems(IntStream.range(0, 23).mapToObj(h -> LocalTime.of(h, 0)));

		// Binder takes care of binding Vaadin fields defined as Java member
		// fields in this class to properties in the Order bean
		binder = new BeanValidationBinder<>(Order.class);

		// Almost all fields are required, so we don't want to display
		// indicators
		binder.setRequiredConfigurator(null);

		// Bindings are done in the order the fields appear on the screen as we
		// report validation errors for the first invalid field and it is most
		// intuitive for the user that we start from the top if there are
		// multiple errors.
		binder.bindInstanceFields(this);

		// Must bind sub properties manually until
		// https://github.com/vaadin/framework/issues/9210 is fixed
		binder.bind(deliveryName, "delivery.name");
		binder.bind(deliveryRegion, "delivery.region");
		binder.bind(deliveryPostal, "delivery.postal");
		binder.bind(deliveryCity, "delivery.city");
		binder.bind(deliveryAddress, "delivery.address");
		binder.bind(deliveryCountry, "delivery.country");

		binder.bind(pickupName, "pickup.name");
		binder.bind(pickupRegion, "pickup.region");
		binder.bind(pickupPostal, "pickup.postal");
		binder.bind(pickupCity, "pickup.city");
		binder.bind(pickupAddress, "pickup.address");
		binder.bind(pickupCountry, "pickup.country");

		binder.bind(fullName, "customer.fullName");
		binder.bind(phone, "customer.phoneNumber");
		binder.bind(details, "customer.details");

		binder.bind(bookingNumber, "booking.bookingNumber");
		binder.bind(bookedLoadPort, "booking.loadPort");
		binder.bind(bookedUnloadPort, "booking.unloadPort");
		binder.bind(bookedCarrier, "booking.carrier");
		binder.bind(bookedPickupDate, "booking.pickupDate");
		binder.bind(bookedPickupTime, "booking.pickupTime");
		binder.bind(bookedDeliveryDate, "booking.deliveryDate");
		binder.bind(bookedDeliveryTime, "booking.deliveryTime");
		binder.bind(cutOffDate, "booking.cutOffDate");
		binder.bind(cutOffTime, "booking.cutOffTime");

		binder.forField(freightCost).withConverter(stringToDoubleConverter).bind("booking.freightCost");

		binder.bind(voyageNumber, "booking.voyageNumber");
		binder.bind(vesselName, "booking.vesselName");
		binder.bind(vesselFlag, "booking.vesselFlag");

		// Track changes manually as we use setBean and nested binders
		binder.addValueChangeListener(e -> hasChanges = true);

		addItems.addClickListener(e -> addEmptyLine());
		cancel.addClickListener(e -> presenter.editBackCancelPressed());
		ok.addClickListener(e -> presenter.okPressed());

		addEqipmentInfos.addClickListener(e -> addEmptyEquipment());
	}

	@Override
	public void enter(ViewChangeEvent event) {
		String orderId = event.getParameters();
		if ("".equals(orderId)) {
			presenter.enterView(null);
		} else {
			presenter.enterView(Long.valueOf(orderId));
		}
	}

	public void setOrder(Order order) {
		stateLabel.setValue(order.getState().getDisplayName());
		if(order.getDelivery() == null) order.setDelivery(new Location());
		if(order.getPickup() == null) order.setPickup(new Location());
		if(order.getBooking() == null) order.setBooking(new Booking());
		if(order.getBooking().getEquipmentLineList() == null) order.getBooking().setEquipmentLineList(new ArrayList<>());
		binder.setBean(order);

		productInfoContainer.removeAllComponents();

		reportHeader.setVisible(order.getId() != null);
		if (order.getId() == null) {
			addEmptyLine();
			createDate.focus();
		} else {
			orderId.setValue("#" + order.getId());
			for (Line item : order.getItems()) {
				ProductInfo productInfo = createProductInfo(item);
				productInfo.setReportMode(mode != Mode.EDIT);
				productInfoContainer.addComponent(productInfo);
			}
			history.setOrder(order);
		}
		hasChanges = false;

		equipmentInfoContainer.removeAllComponents();
		Booking booking = order.getBooking();
		for(EquipmentLine equipmentLine : booking.getEquipmentLineList()) {
			EquipmentInfo equipmentInfo = createEquipmentInfo(equipmentLine);
			equipmentInfo.setReportMode(mode != Mode.EDIT);
			equipmentInfoContainer.addComponent(equipmentInfo);
		}
	}

	private void addEmptyLine() {
		Line line = new Line();
		ProductInfo productInfo = createProductInfo(line);
		productInfoContainer.addComponent(productInfo);
		productInfo.focus();
		getOrder().getItems().add(line);
	}

	protected void removeLine(Line line) {
		getOrder().getItems().remove(line);

		for (Component c : productInfoContainer) {
			if (c instanceof ProductInfo && ((ProductInfo) c).getItem() == line) {
				productInfoContainer.removeComponent(c);
				break;
			}
		}
	}

	private void addEmptyEquipment() {
		EquipmentLine line = new EquipmentLine();
		EquipmentInfo equipmentInfo = createEquipmentInfo(line);
		equipmentInfoContainer.addComponent(equipmentInfo);
		equipmentInfo.focus();
		getOrder().getBooking().getEquipmentLineList().add(line);
	}

	protected void removeEquipment(EquipmentLine line) {
		getOrder().getBooking().getEquipmentLineList().remove(line);

		for (Component c : equipmentInfoContainer) {
			if (c instanceof EquipmentInfo && ((EquipmentInfo) c).getItem() == line) {
				equipmentInfoContainer.removeComponent(c);
				break;
			}
		}
	}

	/**
	 * Create a ProductInfo instance using Spring so that it is injected and can
	 * in turn inject a ProductComboBox and its data provider.
	 *
	 * @param line
	 *            the item to edit
	 *
	 * @return a new product info instance
	 */
	private ProductInfo createProductInfo(Line line) {
		ProductInfo productInfo = beanFactory.getBean(ProductInfo.class);
		productInfo.setItem(line);
		return productInfo;
	}

	private EquipmentInfo createEquipmentInfo(EquipmentLine line) {
		EquipmentInfo equipmentInfo = beanFactory.getBean(EquipmentInfo.class);
		equipmentInfo.setItem(line);
		return equipmentInfo;
	}

	protected Order getOrder() {
		return binder.getBean();
	}

	protected void setSum(double sum) {
		total.setValue(priceConverter.convertToPresentation(sum, new ValueContext(Locale.US)));
	}

	protected void setFreightCost(double sumA, double sumB) {
		double sum = sumA + sumB;
		freight.setValue(priceConverter.convertToPresentation(sum, new ValueContext(Locale.US)));
	}

	public void showNotFound() {
		removeAllComponents();
		addComponent(new Label("Order not found"));
	}

	public void setMode(Mode mode) {
		// Allow to style different modes separately
		if (this.mode != null) {
			removeStyleName(this.mode.name().toLowerCase());
		}
		addStyleName(mode.name().toLowerCase());
		createTime.setEnabled(mode != Mode.EDIT);
		createDate.setEnabled(mode != Mode.EDIT);


		this.mode = mode;
		binder.setReadOnly(!(mode == Mode.EDIT || mode == Mode.BOOKING));
		for (Component c : productInfoContainer) {
			if (c instanceof ProductInfo) {
				((ProductInfo) c).setReportMode(mode != Mode.EDIT);
			}
		}
		for (Component c : equipmentInfoContainer) {
			if (c instanceof EquipmentInfo) {
				((EquipmentInfo) c).setReportMode(mode != Mode.BOOKING);
			}
		}
		addItems.setVisible(mode == Mode.EDIT);
		history.setVisible(mode == Mode.REPORT);
		state.setVisible(mode == Mode.EDIT);

		detail.setEnabled(mode != Mode.BOOKING);
		bookingItems.setEnabled(mode != Mode.EDIT);
		bookingItems.setVisible(mode != Mode.EDIT);

		addEqipmentInfos.setVisible(mode == Mode.BOOKING);

		if (mode == Mode.REPORT) {
			cancel.setCaption("Edit");
			cancel.setIcon(VaadinIcons.EDIT);
			Optional<OrderState> nextState = presenter.getNextHappyPathState(getOrder().getState());
			ok.setCaption("Mark as " + nextState.map(OrderState::getDisplayName).orElse("?"));
			ok.setVisible(nextState.isPresent());
			if(getOrder().getState() == OrderState.READY_TO_BOOK) {
				ok.setCaption("Book order");
			}
		} else if (mode == Mode.CONFIRMATION) {
			cancel.setCaption("Back");
			cancel.setIcon(VaadinIcons.ANGLE_LEFT);
			ok.setCaption("Place order");
			ok.setVisible(true);
		} else if (mode == Mode.BOOKING) {
			cancel.setCaption("Cancel");
			cancel.setIcon(VaadinIcons.ANGLE_LEFT);
			ok.setCaption("Book order");
			ok.setVisible(true);
		} else if (mode == Mode.EDIT) {
			bookingItems.setVisible(false);
			cancel.setCaption("Cancel");
			cancel.setIcon(VaadinIcons.CLOSE);
			if (getOrder() != null && !getOrder().isNew()) {
				ok.setCaption("Save");
			} else {
				ok.setCaption("Review order");
			}
			ok.setVisible(true);
		} else {
			throw new IllegalArgumentException("Unknown mode " + mode);
		}
	}

	public Mode getMode() {
		return mode;
	}

	public Stream<HasValue<?>> validate() {
		Stream<HasValue<?>> errorFields = binder.validate().getFieldValidationErrors().stream()
				.map(BindingValidationStatus::getField);

		for (Component c : productInfoContainer) {
			if (c instanceof ProductInfo) {
				ProductInfo productInfo = (ProductInfo) c;
				if (!productInfo.isEmpty()) {
					errorFields = Stream.concat(errorFields, productInfo.validate());
				}
			}
		}
		return errorFields;
	}

	@Override
	public void beforeLeave(ViewBeforeLeaveEvent event) {
		if (!containsUnsavedChanges()) {
			event.navigate();
		} else {
			ConfirmPopup confirmPopup = beanFactory.getBean(ConfirmPopup.class);
			confirmPopup.showLeaveViewConfirmDialog(this, event::navigate);
		}
	}

	public void onProductInfoChanged() {
		hasChanges = true;
	}

	public boolean containsUnsavedChanges() {
		return hasChanges;
	}
}
