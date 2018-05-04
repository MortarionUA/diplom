package com.gmail.dmytro.ui.view.orderedit;

import com.gmail.dmytro.backend.data.entity.Carrier;
import com.gmail.dmytro.backend.service.CarrierService;
import com.vaadin.data.provider.Query;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.artur.spring.dataprovider.PageableDataProvider;

import java.util.ArrayList;
import java.util.List;

@SpringComponent
public class CarrierComboBoxDataProvider extends PageableDataProvider<Carrier, String> {

	private final CarrierService carrierService;

	@Autowired
	public CarrierComboBoxDataProvider(CarrierService carrierService) {
		this.carrierService = carrierService;
	}

	@Override
	protected Page<Carrier> fetchFromBackEnd(Query<Carrier, String> query, Pageable pageable) {
		return carrierService.findAnyMatching(query.getFilter(), pageable);
	}

	@Override
	protected int sizeInBackEnd(Query<Carrier, String> query) {
		return (int) carrierService.countAnyMatching(query.getFilter());
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("name", SortDirection.ASCENDING));
		return sortOrders;
	}

}
