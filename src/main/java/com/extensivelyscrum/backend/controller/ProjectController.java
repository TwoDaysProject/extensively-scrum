package com.extensivelyscrum.backend.controller;

import com.extensivelyscrum.backend.dto.JwtTokenDto;
import com.extensivelyscrum.backend.dto.ListProjectDto;
import com.extensivelyscrum.backend.dto.NewProjectDto;
import com.extensivelyscrum.backend.model.Project;
import com.extensivelyscrum.backend.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
@AllArgsConstructor
public class ProjectController {

    ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<ListProjectDto> createProject(@RequestBody NewProjectDto newProject,
                                              @RequestHeader("Authorization") JwtTokenDto jwtToken){
        return new ResponseEntity<>(
                projectService.createProject(newProject, jwtToken),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/deleteProject/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable String id,
                                              @RequestHeader("Authorization") JwtTokenDto jwtTokens) {
        projectService.deleteById(id, jwtTokens);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @GetMapping("/getUserProjects")
    public ResponseEntity<List<ListProjectDto>> listUserProjects(@RequestHeader("Authorization") JwtTokenDto jwtToken) {
        return new ResponseEntity<>(
                projectService.listUserProjects(jwtToken),
                HttpStatus.OK
        );
    }


}
