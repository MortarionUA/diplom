package com.gmail.dmytro.backend.service;

import com.gmail.dmytro.backend.EquipmentRepository;
import com.gmail.dmytro.backend.ProductRepository;
import com.gmail.dmytro.backend.data.entity.Equipment;
import com.gmail.dmytro.backend.data.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EquipmentService extends CrudService<Equipment> {

	private final EquipmentRepository equipmentRepository;

	@Autowired
	public EquipmentService(EquipmentRepository equipmentRepository) {
		this.equipmentRepository = equipmentRepository;
	}

	@Override
	public Page<Equipment> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().findByCodeLikeIgnoreCaseOrNameLikeIgnoreCase(repositoryFilter,repositoryFilter, pageable);
		} else {
			return getRepository().findAll(pageable);
		}
	}

	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().countByCodeLikeIgnoreCaseOrNameLikeIgnoreCase(repositoryFilter,repositoryFilter);
		} else {
			return getRepository().count();
		}
	}

	@Override
	protected EquipmentRepository getRepository() {
		return equipmentRepository;
	}

}
