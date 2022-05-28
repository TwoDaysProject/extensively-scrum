package com.extensivelyscrum.backend.repository;

import com.extensivelyscrum.backend.model.BacklogItem;
import com.extensivelyscrum.backend.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BacklogItemRepository extends JpaRepository<BacklogItem, String> {
    List<BacklogItem> findAllByProject(Project project);
}
