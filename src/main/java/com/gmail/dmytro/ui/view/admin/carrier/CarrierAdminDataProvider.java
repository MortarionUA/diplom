package com.gmail.dmytro.ui.view.admin.carrier;

import com.gmail.dmytro.backend.data.entity.Carrier;
import com.gmail.dmytro.backend.service.CarrierService;
import com.vaadin.data.provider.Query;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.artur.spring.dataprovider.FilterablePageableDataProvider;
import org.vaadin.spring.annotation.PrototypeScope;

import java.util.ArrayList;
import java.util.List;

@SpringComponent
@PrototypeScope
public class CarrierAdminDataProvider extends FilterablePageableDataProvider<Carrier, Object> {

	private final CarrierService carrierService;

	@Autowired
	public CarrierAdminDataProvider(CarrierService carrierService) {
		this.carrierService = carrierService;
	}

	@Override
	protected Page<Carrier> fetchFromBackEnd(Query<Carrier, Object> query, Pageable pageable) {
		return carrierService.findAnyMatching(getOptionalFilter(), pageable);
	}

	@Override
	protected int sizeInBackEnd(Query<Carrier, Object> query) {
		return (int) carrierService.countAnyMatching(getOptionalFilter());
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("name", SortDirection.ASCENDING));
		return sortOrders;
	}

}