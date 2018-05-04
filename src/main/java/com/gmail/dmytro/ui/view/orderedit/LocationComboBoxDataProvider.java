package com.gmail.dmytro.ui.view.orderedit;

import java.util.ArrayList;
import java.util.List;

import com.gmail.dmytro.backend.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.artur.spring.dataprovider.PageableDataProvider;

import com.vaadin.data.provider.Query;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.SpringComponent;
import com.gmail.dmytro.backend.data.entity.Location;

/**
 * A singleton data provider which knows which products are available.
 */
@SpringComponent
public class LocationComboBoxDataProvider extends PageableDataProvider<Location, String> {

	private final LocationService locationService;

	@Autowired
	public LocationComboBoxDataProvider(LocationService locationService) {
		this.locationService = locationService;
	}

	@Override
	protected Page<Location> fetchFromBackEnd(Query<Location, String> query, Pageable pageable) {
		return locationService.findAnyMatching(query.getFilter(), pageable);
	}

	@Override
	protected int sizeInBackEnd(Query<Location, String> query) {
		return (int) locationService.countAnyMatching(query.getFilter());
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("name", SortDirection.ASCENDING));
		return sortOrders;
	}

}
