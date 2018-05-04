package com.gmail.dmytro.ui.view.admin.carrier;

import com.gmail.dmytro.backend.data.entity.Carrier;
import com.gmail.dmytro.backend.data.entity.Product;
import com.gmail.dmytro.backend.service.CarrierService;
import com.gmail.dmytro.backend.service.ProductService;
import com.gmail.dmytro.ui.navigation.NavigationManager;
import com.gmail.dmytro.ui.view.admin.AbstractCrudPresenter;
import com.gmail.dmytro.ui.view.admin.product.ProductAdminDataProvider;
import com.gmail.dmytro.ui.view.admin.product.ProductAdminView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@ViewScope
public class CarrierAdminPresenter extends AbstractCrudPresenter<Carrier, CarrierService, CarrierAdminView> {

	@Autowired
	public CarrierAdminPresenter(CarrierAdminDataProvider carrierAdminDataProvider, NavigationManager navigationManager,
                                 CarrierService service, BeanFactory beanFactory) {
		super(navigationManager, service, Carrier.class, carrierAdminDataProvider, beanFactory);
	}
}
