package com.gmail.dmytro.ui.view.orderedit;

import com.gmail.dmytro.backend.data.entity.Location;
import com.gmail.dmytro.backend.data.entity.Port;
import com.gmail.dmytro.backend.service.LocationService;
import com.gmail.dmytro.backend.service.PortService;
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

/**
 * A singleton data provider which knows which products are available.
 */
@SpringComponent
public class PortComboBoxDataProvider extends PageableDataProvider<Port, String> {

	private final PortService portService;

	@Autowired
	public PortComboBoxDataProvider(PortService portService) {
		this.portService = portService;
	}

	@Override
	protected Page<Port> fetchFromBackEnd(Query<Port, String> query, Pageable pageable) {
		return portService.findAnyMatching(query.getFilter(), pageable);
	}

	@Override
	protected int sizeInBackEnd(Query<Port, String> query) {
		return (int) portService.countAnyMatching(query.getFilter());
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("name", SortDirection.ASCENDING));
		return sortOrders;
	}

}
