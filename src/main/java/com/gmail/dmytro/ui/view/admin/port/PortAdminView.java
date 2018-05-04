package com.gmail.dmytro.ui.view.admin.port;

import com.gmail.dmytro.backend.data.entity.Port;
import com.gmail.dmytro.ui.view.admin.AbstractCrudView;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Focusable;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView
public class PortAdminView extends AbstractCrudView<Port> {

	private final PortAdminPresenter presenter;

	private final PortAdminViewDesign portAdminViewDesign;

	@Autowired
	public PortAdminView(PortAdminPresenter presenter) {
		this.presenter = presenter;
		portAdminViewDesign = new PortAdminViewDesign();
	}

	@PostConstruct
	private void init() {
		presenter.init(this);
		// Show two columns: "code" and "name".
		getGrid().setColumns("code","name");
	}

	@Override
	public void bindFormFields(BeanValidationBinder<Port> binder) {
		binder.bindInstanceFields(getViewComponent());
	}

	@Override
	public PortAdminViewDesign getViewComponent() {
		return portAdminViewDesign;
	}

	@Override
	protected PortAdminPresenter getPresenter() {
		return presenter;
	}

	@Override
	protected Grid<Port> getGrid() {
		return getViewComponent().list;
	}

	@Override
	protected void setGrid(Grid<Port> grid) {
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