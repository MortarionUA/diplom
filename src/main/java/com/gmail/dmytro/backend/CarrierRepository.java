package com.gmail.dmytro.backend;

import com.gmail.dmytro.backend.data.entity.Carrier;
import com.gmail.dmytro.backend.data.entity.Port;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrierRepository extends JpaRepository<Carrier, Long> {
	Page<Carrier> findByCodeLikeIgnoreCaseOrNameLikeIgnoreCase(String code, String name, Pageable page);

	int countByCodeLikeIgnoreCaseOrNameLikeIgnoreCase(String code, String name);
}
