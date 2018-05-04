package com.gmail.dmytro.ui.view.admin;

import com.gmail.dmytro.backend.data.Role;
import com.vaadin.ui.ComboBox;

public class RoleSelect extends ComboBox<String> {

	public RoleSelect() {
		setCaption("Role");
		setEmptySelectionAllowed(false);
		setItems(Role.getAllRoles());
		setTextInputAllowed(false);
	}
}
