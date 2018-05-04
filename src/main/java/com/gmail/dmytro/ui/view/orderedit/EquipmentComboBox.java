package com.gmail.dmytro.ui.view.orderedit;

import com.gmail.dmytro.backend.data.entity.Equipment;
import com.gmail.dmytro.backend.data.entity.Product;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.ComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

@SpringComponent
@PrototypeScope
public class EquipmentComboBox extends ComboBox<Equipment> {

	@Autowired
	public EquipmentComboBox(EquipmentComboBoxDataProvider dataProvider) {
		setWidth("100%");
		setEmptySelectionAllowed(false);
		setPlaceholder("Equipment");
		setItemCaptionGenerator(equipment -> equipment.getCode() + " : " + equipment.getName());
		setDataProvider(dataProvider);
	}

}
