package com.gmail.dmytro.backend.service;

import com.gmail.dmytro.backend.CarrierRepository;
import com.gmail.dmytro.backend.EquipmentRepository;
import com.gmail.dmytro.backend.data.entity.Carrier;
import com.gmail.dmytro.backend.data.entity.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarrierService extends CrudService<Carrier> {

	private final CarrierRepository carrierRepository;

	@Autowired
	public CarrierService(CarrierRepository carrierRepository) {
		this.carrierRepository = carrierRepository;
	}

	@Override
	public Page<Carrier> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().findByCodeLikeIgnoreCaseOrNameLikeIgnoreCase(repositoryFilter, repositoryFilter, pageable);
		} else {
			return getRepository().findAll(pageable);
		}
	}

	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().countByCodeLikeIgnoreCaseOrNameLikeIgnoreCase(repositoryFilter, repositoryFilter);
		} else {
			return getRepository().count();
		}
	}

	@Override
	protected CarrierRepository getRepository() {
		return carrierRepository;
	}

}
