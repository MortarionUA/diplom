package com.gmail.dmytro.ui.view.storefront;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.testbench.elements.AbsoluteLayoutElement;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.GridElement;
import com.vaadin.testbench.elements.TextFieldElement;
import com.vaadin.testbench.elementsbase.ServerClass;

@ServerClass("StorefrontViewDesign")
@AutoGenerated
public class StorefrontViewDesignElement extends AbsoluteLayoutElement {

	public ButtonElement getNewOrder() {
		return $(com.vaadin.testbench.elements.ButtonElement.class).id("newOrder");
	}

	public GridElement getList() {
		return $(com.vaadin.testbench.elements.GridElement.class).id("list");
	}

	public TextFieldElement getSearchField() {
		return $(com.vaadin.testbench.elements.TextFieldElement.class).id("searchField");
	}

	public ButtonElement getSearchButton() {
		return $(com.vaadin.testbench.elements.ButtonElement.class).id("searchButton");
	}

}