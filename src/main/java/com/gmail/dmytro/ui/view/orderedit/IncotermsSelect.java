package com.gmail.dmytro.ui.view.orderedit;

import com.gmail.dmytro.app.HasLogger;
import com.gmail.dmytro.backend.data.Incoterms;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.ComboBox;

@SpringComponent
@ViewScope
public class IncotermsSelect extends ComboBox<Incoterms> implements HasLogger {

	public IncotermsSelect() {
		setEmptySelectionAllowed(false);
		setTextInputAllowed(false);

		setItems(Incoterms.values());
		setItemCaptionGenerator(Incoterms::name);

	}

}
