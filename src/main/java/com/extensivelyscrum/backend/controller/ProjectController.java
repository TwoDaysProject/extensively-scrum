package com.extensivelyscrum.backend.controller;

import com.extensivelyscrum.backend.dto.NewProjectDto;
import com.extensivelyscrum.backend.model.Project;
import com.extensivelyscrum.backend.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
@AllArgsConstructor
public class ProjectController {

    ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<Project> createProject(@RequestBody NewProjectDto newProject,
                                              @RequestHeader("Authorization") String jwtToken){
        return new ResponseEntity<>(
                projectService.createProject(newProject),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/deleteProject/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {
        projectService.deleteById(id);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }


}
