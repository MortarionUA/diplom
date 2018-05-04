package com.gmail.dmytro.ui.view.orderedit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import com.vaadin.spring.annotation.SpringComponent;
import com.gmail.dmytro.backend.data.entity.Location;
import com.vaadin.ui.ComboBox;

@SpringComponent
@PrototypeScope
public class LocationComboBox extends ComboBox<Location> {

	private final LocationComboBoxDataProvider dataProvider;

	@Autowired
	public LocationComboBox(LocationComboBoxDataProvider dataProvider) {
		this.dataProvider = dataProvider;
		setEmptySelectionAllowed(false);
		setTextInputAllowed(false);
		setPlaceholder("Pickup location");
		setItemCaptionGenerator(Location::getName);
	}

	@PostConstruct
	private void initDataProvider() {
		setDataProvider(dataProvider);
	}

}
