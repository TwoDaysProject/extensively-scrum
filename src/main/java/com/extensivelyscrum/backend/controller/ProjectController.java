package com.extensivelyscrum.backend.controller;

import com.extensivelyscrum.backend.dto.CreateUserDto;
import com.extensivelyscrum.backend.dto.NewProjectDto;
import com.extensivelyscrum.backend.model.Project;
import com.extensivelyscrum.backend.model.User;
import com.extensivelyscrum.backend.repository.ProjcetRepository;
import com.extensivelyscrum.backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
public class ProjectController {


    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Autowired
    ProjectService projectService;

    @PostMapping("/newProject")
    public ResponseEntity<Project> newProject(@RequestBody NewProjectDto newproject){

        return new ResponseEntity<>(
                projectService.createProject(newproject),
                HttpStatus.CREATED
        );

    }
}
