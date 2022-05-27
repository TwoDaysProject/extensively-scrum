package com.extensivelyscrum.backend.repository;

import com.extensivelyscrum.backend.model.BacklogItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BacklogItemRepository extends JpaRepository<BacklogItem, String> {
}
