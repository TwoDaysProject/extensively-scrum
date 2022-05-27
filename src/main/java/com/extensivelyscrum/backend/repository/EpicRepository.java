package com.extensivelyscrum.backend.repository;

import com.extensivelyscrum.backend.model.BacklogItem;
import com.extensivelyscrum.backend.model.Epic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpicRepository extends JpaRepository<Epic, String> {
}
