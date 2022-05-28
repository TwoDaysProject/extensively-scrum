package com.extensivelyscrum.backend.repository;

import com.extensivelyscrum.backend.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepository extends JpaRepository<Sprint, String> {
}
