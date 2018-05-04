package com.gmail.dmytro.ui.view.orderedit;

import com.gmail.dmytro.backend.data.entity.Equipment;
import com.gmail.dmytro.backend.data.entity.Product;
import com.gmail.dmytro.backend.service.EquipmentService;
import com.gmail.dmytro.backend.service.ProductService;
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
public class EquipmentComboBoxDataProvider extends PageableDataProvider<Equipment, String> {

	private final EquipmentService equipmentService;

	@Autowired
	public EquipmentComboBoxDataProvider(EquipmentService equipmentService) {
		this.equipmentService = equipmentService;
	}

	@Override
	protected Page<Equipment> fetchFromBackEnd(Query<Equipment, String> query, Pageable pageable) {
		return equipmentService.findAnyMatching(query.getFilter(), pageable);
	}

	@Override
	protected int sizeInBackEnd(Query<Equipment, String> query) {
		return (int) equipmentService.countAnyMatching(query.getFilter());
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("name", SortDirection.ASCENDING));
		return sortOrders;
	}

}
