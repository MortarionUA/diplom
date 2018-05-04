package com.gmail.dmytro.backend;

import com.gmail.dmytro.backend.data.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

	Page<Location> findByNameLikeIgnoreCase(String nameFilter, Pageable pageable);

	int countByNameLikeIgnoreCase(String nameFilter);
}
