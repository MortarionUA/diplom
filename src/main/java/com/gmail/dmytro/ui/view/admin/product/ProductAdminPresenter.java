package com.gmail.dmytro.ui.view.admin.product;

import com.gmail.dmytro.backend.data.entity.Product;
import com.gmail.dmytro.backend.service.ProductService;
import com.gmail.dmytro.ui.navigation.NavigationManager;
import com.gmail.dmytro.ui.view.admin.AbstractCrudPresenter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class ProductAdminPresenter extends AbstractCrudPresenter<Product, ProductService, ProductAdminView> {

	@Autowired
	public ProductAdminPresenter(ProductAdminDataProvider productAdminDataProvider, NavigationManager navigationManager,
                                 ProductService service, BeanFactory beanFactory) {
		super(navigationManager, service, Product.class, productAdminDataProvider, beanFactory);
	}
}
