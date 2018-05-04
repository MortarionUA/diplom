package com.gmail.dmytro.backend;

import com.gmail.dmytro.backend.data.entity.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

	Page<Equipment> findByCodeLikeIgnoreCaseOrNameLikeIgnoreCase(String code, String name, Pageable page);

	int countByCodeLikeIgnoreCaseOrNameLikeIgnoreCase(String code, String name);

}
