package com.gmail.dmytro.ui.view.orderedit;

import com.gmail.dmytro.app.HasLogger;
import com.gmail.dmytro.backend.data.OrderState;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.ComboBox;

@SpringComponent
@ViewScope
public class OrderStateSelect extends ComboBox<OrderState> implements HasLogger {

	public OrderStateSelect() {
		setEmptySelectionAllowed(false);
		setTextInputAllowed(false);
		setItems(OrderState.values());
		setItemCaptionGenerator(OrderState::getDisplayName);
	}

}
