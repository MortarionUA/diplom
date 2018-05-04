package com.gmail.dmytro.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmail.dmytro.backend.LocationRepository;
import com.gmail.dmytro.backend.data.entity.Location;

@Service
public class LocationService {

	private final LocationRepository locationRepository;

	@Autowired
	public LocationService(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	public Page<Location> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return locationRepository.findByNameLikeIgnoreCase(repositoryFilter, pageable);
		} else {
			return locationRepository.findByNameLikeIgnoreCase("%", pageable);
		}
	}

	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return locationRepository.countByNameLikeIgnoreCase(repositoryFilter);
		} else {
			return locationRepository.countByNameLikeIgnoreCase("%");
		}
	}

	public Location getDefault() {
		return findAnyMatching(Optional.empty(), new PageRequest(0, 1)).iterator().next();
	}

}
