package com.gmail.dmytro.ui.view.orderedit;

import com.gmail.dmytro.backend.data.entity.Carrier;
import com.gmail.dmytro.backend.data.entity.Equipment;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.ComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

@SpringComponent
@PrototypeScope
public class CarrierComboBox extends ComboBox<Carrier> {

	@Autowired
	public CarrierComboBox(CarrierComboBoxDataProvider dataProvider) {
		setWidth("100%");
		setEmptySelectionAllowed(false);
		setPlaceholder("Carrier");
		setItemCaptionGenerator(carrier -> carrier.getCode() + " : " + carrier.getName());
		setDataProvider(dataProvider);
	}

}
