package com.gmail.dmytro.ui.view.orderedit;

import com.gmail.dmytro.backend.data.entity.Location;
import com.gmail.dmytro.backend.data.entity.Port;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.ComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import javax.annotation.PostConstruct;

@SpringComponent
@PrototypeScope
public class PortComboBox extends ComboBox<Port> {

	private final PortComboBoxDataProvider dataProvider;

	@Autowired
	public PortComboBox(PortComboBoxDataProvider dataProvider) {
		this.dataProvider = dataProvider;
		setEmptySelectionAllowed(true);
		setPlaceholder("Port");
		setItemCaptionGenerator(port -> port.getCode() + " : " + port.getName());
	}

	@PostConstruct
	private void initDataProvider() {
		setDataProvider(dataProvider);
	}

}
