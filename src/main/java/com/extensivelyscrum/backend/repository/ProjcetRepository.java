package com.extensivelyscrum.backend.repository;

import com.extensivelyscrum.backend.model.Project;
import com.extensivelyscrum.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjcetRepository extends JpaRepository<Project, String> {
}
