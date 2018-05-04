package com.gmail.dmytro.ui.components;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import com.gmail.dmytro.backend.data.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.HtmlUtils;
import org.vaadin.spring.annotation.PrototypeScope;

import com.vaadin.spring.annotation.SpringComponent;
import com.gmail.dmytro.backend.data.entity.Order;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.HtmlRenderer;

@SpringComponent
@PrototypeScope
public class OrdersGrid extends Grid<Order> {

	@Autowired
	private OrdersDataProvider dataProvider;

	public OrdersGrid() {
		addStyleName("orders-grid");
		setSizeFull();
		removeHeaderRow(0);

		// Add stylenames to rows
		setStyleGenerator(OrdersGrid::getRowStyle);

		// Due column
		Column<Order, String> orderNoColumn = addColumn(
				Order::getOrderNo);
		orderNoColumn.setSortProperty("orderNo");
		orderNoColumn.setWidth(150);
		addColumn(
				Order::getState);
		Column<Order, String> deliveryDateColumn = addColumn(
				order -> order.getDeliveryDate().toString());
		deliveryDateColumn.setSortProperty("deliveryDate");
		deliveryDateColumn.setWidth(150);
		deliveryDateColumn.setStyleGenerator(order -> order.getDeliveryDate().toString());

		// Summary column
		Column<Order, String> summaryColumn = addColumn(order -> {
			Customer customer = order.getCustomer();
			return twoRowCell(customer.getFullName(), getOrderSummary(order));
		}, new HtmlRenderer()).setExpandRatio(1).setSortProperty("customer.fullName").setMinimumWidthFromContent(false);
		summaryColumn.setStyleGenerator(order -> "summary");
	}

	public void filterGrid(String searchTerm, boolean includePast) {
		dataProvider.setFilter(searchTerm);
		dataProvider.setIncludePast(includePast);
	}

	@PostConstruct
	protected void init() {
		setDataProvider(dataProvider);
	}

	/**
	 * Makes date into a more readable form; "Today", "Mon 7", "12 Jun"
	 * 
	 * @param createDate
	 *            The date to make into a string
	 * @return A formatted string depending on how far in the future the date
	 *         is.
	 */
	private static String getTimeHeader(LocalDate createDate) {
		LocalDate today = LocalDate.now();
		if (createDate.isEqual(today)) {
			return "Today";
		} else {
			// Show weekday for upcoming days
			LocalDate todayNextWeek = today.plusDays(7);
			if (createDate.isAfter(today) && createDate.isBefore(todayNextWeek)) {
				// "Mon 7"
				return createDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US) + " "
						+ createDate.getDayOfMonth();
			} else {
				// In the past or more than a week in the future
				return createDate.getDayOfMonth() + " " + createDate.getMonth().getDisplayName(TextStyle.SHORT, Locale.US);
			}
		}
	}

	private static String getRowStyle(Order order) {
		String style = order.getState().name().toLowerCase();

		long days = LocalDate.now().until(order.getCreateDate(), ChronoUnit.DAYS);
		if (days == 0) {
			style += " today";
		} else if (days == 1) {
			style += " tomorrow";
		}

		return style;
	}

	private static String getOrderSummary(Order order) {
		Stream<String> quantityAndName = order.getItems().stream()
				.map(item -> item.getCount() + "x " + item.getProduct().getName());
		return quantityAndName.collect(Collectors.joining(", "));
	}

	private static String twoRowCell(String header, String content) {
		return "<div class=\"header\">" + HtmlUtils.htmlEscape(header) + "</div><div class=\"content\">"
				+ HtmlUtils.htmlEscape(content) + "</div>";
	}

}
