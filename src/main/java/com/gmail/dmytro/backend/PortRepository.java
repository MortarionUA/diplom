package com.gmail.dmytro.backend;

import com.gmail.dmytro.backend.data.entity.Port;
import com.gmail.dmytro.backend.data.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortRepository extends JpaRepository<Port, Long> {
	Page<Port> findByCodeLikeIgnoreCaseOrNameLikeIgnoreCase(String code, String name, Pageable page);

	int countByCodeLikeIgnoreCaseOrNameLikeIgnoreCase(String code, String name);
}
