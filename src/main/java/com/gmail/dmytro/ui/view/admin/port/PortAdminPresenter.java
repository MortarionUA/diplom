package com.gmail.dmytro.ui.view.admin.port;

import com.gmail.dmytro.backend.data.entity.Port;
import com.gmail.dmytro.backend.service.PortService;
import com.gmail.dmytro.ui.navigation.NavigationManager;
import com.gmail.dmytro.ui.view.admin.AbstractCrudPresenter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@ViewScope
public class PortAdminPresenter extends AbstractCrudPresenter<Port, PortService, PortAdminView> {

	@Autowired
	public PortAdminPresenter(PortAdminDataProvider portAdminDataProvider, NavigationManager navigationManager,
                              PortService service, BeanFactory beanFactory) {
		super(navigationManager, service, Port.class, portAdminDataProvider, beanFactory);
	}
}
