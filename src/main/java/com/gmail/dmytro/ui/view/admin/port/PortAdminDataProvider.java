package com.gmail.dmytro.ui.view.admin.port;

import com.gmail.dmytro.backend.data.entity.Port;
import com.gmail.dmytro.backend.service.PortService;
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
public class PortAdminDataProvider extends FilterablePageableDataProvider<Port, Object> {

	private final PortService portService;

	@Autowired
	public PortAdminDataProvider(PortService portService) {
		this.portService = portService;
	}

	@Override
	protected Page<Port> fetchFromBackEnd(Query<Port, Object> query, Pageable pageable) {
		return portService.findAnyMatching(getOptionalFilter(), pageable);
	}

	@Override
	protected int sizeInBackEnd(Query<Port, Object> query) {
		return (int) portService.countAnyMatching(getOptionalFilter());
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("name", SortDirection.ASCENDING));
		return sortOrders;
	}

}