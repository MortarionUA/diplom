package com.gmail.dmytro.ui.view.storefront;

import com.gmail.dmytro.ui.view.orderedit.OrderStateSelect;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.gmail.dmytro.ui.components.OrdersGrid;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;
import com.gmail.dmytro.ui.components.OrdersGrid;

/** 
 * !! DO NOT EDIT THIS FILE !!
 * 
 * This class is generated by Vaadin Designer and will be overwritten.
 * 
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class StorefrontViewDesign extends VerticalLayout {
    protected Panel searchPanel;
    protected TextField orderNoSearchField;
    protected TextField customerSearchField;
    protected Button searchButton;
    protected CheckBox includePast;
    protected Button newOrder;
    protected OrdersGrid list;

    public StorefrontViewDesign() {
		Design.read(this);
	}
}
