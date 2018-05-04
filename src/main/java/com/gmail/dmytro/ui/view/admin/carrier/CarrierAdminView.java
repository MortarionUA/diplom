package com.gmail.dmytro.ui.view.admin.carrier;

import com.gmail.dmytro.backend.data.entity.Carrier;
import com.gmail.dmytro.ui.view.admin.AbstractCrudView;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Focusable;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg.ERROR_MSG;

@SpringView
public class CarrierAdminView extends AbstractCrudView<Carrier> {

	private final CarrierAdminPresenter presenter;

	private final CarrierAdminViewDesign userAdminViewDesign;
	private final StringToDoubleConverter weightToStringConverter = new StringToDoubleConverter(ERROR_MSG);

	@Autowired
	public CarrierAdminView(CarrierAdminPresenter presenter) {
		this.presenter = presenter;
		userAdminViewDesign = new CarrierAdminViewDesign();
	}

	@PostConstruct
	private void init() {
		presenter.init(this);
		// Show two columns: "code" and "name".
		getGrid().setColumns("code","name","type","massCap","volumeCap","speed");
	}

	@Override
	public void bindFormFields(BeanValidationBinder<Carrier> binder) {
		binder.forField(getViewComponent().code).bind("code");
		binder.forField(getViewComponent().name).bind("name");
		binder.forField(getViewComponent().type).bind("type");
		binder.forField(getViewComponent().massCap).withConverter(weightToStringConverter).bind("massCap");
		binder.forField(getViewComponent().volumeCap).withConverter(weightToStringConverter).bind("volumeCap");
		binder.forField(getViewComponent().speed).withConverter(weightToStringConverter).bind("speed");
		binder.bindInstanceFields(getViewComponent());
	}

	@Override
	public CarrierAdminViewDesign getViewComponent() {
		return userAdminViewDesign;
	}

	@Override
	protected CarrierAdminPresenter getPresenter() {
		return presenter;
	}

	@Override
	protected Grid<Carrier> getGrid() {
		return getViewComponent().list;
	}

	@Override
	protected void setGrid(Grid<Carrier> grid) {
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