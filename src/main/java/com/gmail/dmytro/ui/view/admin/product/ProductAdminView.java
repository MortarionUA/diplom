package com.gmail.dmytro.ui.view.admin.product;

import javax.annotation.PostConstruct;

import com.vaadin.data.converter.StringToDoubleConverter;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.ValueContext;
import com.vaadin.spring.annotation.SpringView;
import com.gmail.dmytro.backend.data.entity.Product;
import com.gmail.dmytro.ui.util.DollarPriceConverter;
import com.gmail.dmytro.ui.view.admin.AbstractCrudView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Focusable;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

@SpringView
public class ProductAdminView extends AbstractCrudView<Product> {

	private static final String ERROR_MSG = "Invalid prices, please re-check the value";
	private final ProductAdminPresenter presenter;

	private final ProductAdminViewDesign userAdminViewDesign;

	private final DollarPriceConverter priceToStringConverter;
	private final StringToDoubleConverter weightToStringConverter = new StringToDoubleConverter(ERROR_MSG);

	@Autowired
	public ProductAdminView(ProductAdminPresenter presenter, DollarPriceConverter priceToStringConverter) {
		this.presenter = presenter;
		this.priceToStringConverter = priceToStringConverter;
		userAdminViewDesign = new ProductAdminViewDesign();
	}

	@PostConstruct
	private void init() {
		presenter.init(this);
		// Show two columns: "name" and "price".
		getGrid().setColumns("name", "value", "weight", "volume", "hazmatCode", "hazmatGroup", "hazmatClass");
		// The price column is configured automatically based on the bean. As
		// we want a custom converter, we remove the column and configure it
		// manually.
//		getGrid().removeColumn("value");
//		getGrid().addColumn(product -> priceToStringConverter.convertToPresentation(product.getValue() != 0 ? product.getValue() : 0.0,
//				new ValueContext(getGrid()))).setSortProper	ty("value");
//		getGrid().removeColumn("weight");
//		getGrid().addColumn(product -> priceToStringConverter.convertToPresentation(product.getValue() != 0 ? product.getValue() : 0.0,
//				new ValueContext(getGrid()))).setSortProperty("weight");
	}

	@Override
	public void bindFormFields(BeanValidationBinder<Product> binder) {
		binder.forField(getViewComponent().price).withConverter(priceToStringConverter).bind("value");
		binder.forField(getViewComponent().weight).withConverter(weightToStringConverter).bind("weight");
		binder.forField(getViewComponent().volume).withConverter(weightToStringConverter).bind("volume");
		binder.forField(getViewComponent().hazmatCode).bind("hazmatCode");
		binder.forField(getViewComponent().hazmatGroup).bind("hazmatGroup");
		binder.forField(getViewComponent().hazmatClass).bind("hazmatClass");
		binder.bindInstanceFields(getViewComponent());
	}

	@Override
	public ProductAdminViewDesign getViewComponent() {
		return userAdminViewDesign;
	}

	@Override
	protected ProductAdminPresenter getPresenter() {
		return presenter;
	}

	@Override
	protected Grid<Product> getGrid() {
		return getViewComponent().list;
	}

	@Override
	protected void setGrid(Grid<Product> grid) {
		getViewComponent().list = grid;
	}

	@Override
	protected Component getForm() {
		return getViewComponent().form;
	}

	@Override
	protected Button getAdd() {
		return getViewComponent().add;
	}

	@Override
	protected Button getCancel() {
		return getViewComponent().cancel;
	}

	@Override
	protected Button getDelete() {
		return getViewComponent().delete;
	}

	@Override
	protected Button getUpdate() {
		return getViewComponent().update;
	}

	@Override
	protected TextField getSearch() {
		return getViewComponent().search;
	}

	@Override
	protected Focusable getFirstFormField() {
		return getViewComponent().name;
	}

}