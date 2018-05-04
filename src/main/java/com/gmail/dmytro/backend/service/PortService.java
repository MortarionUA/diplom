package com.gmail.dmytro.backend.service;

import com.gmail.dmytro.backend.PortRepository;
import com.gmail.dmytro.backend.ProductRepository;
import com.gmail.dmytro.backend.data.entity.Port;
import com.gmail.dmytro.backend.data.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PortService extends CrudService<Port> {

	private final PortRepository portRepository;

	@Autowired
	public PortService(PortRepository portRepository) {
		this.portRepository = portRepository;
	}

	@Override
	public Page<Port> findAnyMatching(Optional<String> filter, Pageable pageable) {
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
	protected PortRepository getRepository() {
		return portRepository;
	}

}
