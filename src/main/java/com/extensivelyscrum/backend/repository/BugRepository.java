package com.extensivelyscrum.backend.repository;

import com.extensivelyscrum.backend.model.Bug;
import com.extensivelyscrum.backend.model.Epic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BugRepository extends JpaRepository<Bug, String> {
}
