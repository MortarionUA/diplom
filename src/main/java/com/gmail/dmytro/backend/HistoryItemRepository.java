package com.gmail.dmytro.backend;

import com.gmail.dmytro.backend.data.entity.HistoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryItemRepository extends JpaRepository<HistoryItem, Long> {
}
