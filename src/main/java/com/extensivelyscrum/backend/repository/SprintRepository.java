package com.extensivelyscrum.backend.repository;

import com.extensivelyscrum.backend.model.Project;
import com.extensivelyscrum.backend.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SprintRepository extends JpaRepository<Sprint, String> {
    List<Sprint> findAllByProject(Project project);
}
