package com.extensivelyscrum.backend.service;

import com.extensivelyscrum.backend.model.Project;
import org.springframework.stereotype.Service;
import com.extensivelyscrum.backend.repository.ProjcetRepository;

@Service
public class ProjectService {
    private ProjcetRepository projectRepository;

    public ProjectService(ProjcetRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project getProjectWithId(String id) {
        return projectRepository.findById(id).orElseThrow(
                () -> new RuntimeException()
        );
    }
}
