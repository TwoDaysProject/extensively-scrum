package com.extensivelyscrum.backend.controller;

import com.extensivelyscrum.backend.dto.CreateSprintDto;
import com.extensivelyscrum.backend.dto.JwtTokenDto;
import com.extensivelyscrum.backend.dto.ListSprintsDto;
import com.extensivelyscrum.backend.service.SprintService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/sprint")
@AllArgsConstructor
public class SprintController {

    private SprintService sprintService;

    @PostMapping(path = "/create",
    consumes = "application/json",
    produces = "application/json")
    public ResponseEntity<ListSprintsDto> createSprint(@RequestBody @Valid CreateSprintDto dto,
                                                       @RequestHeader("Authorization") JwtTokenDto token) {
        return new ResponseEntity<>(
            sprintService.createSprint(dto),
            HttpStatus.CREATED
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSprint(@PathVariable String id) {
        sprintService.deleteWithId(id);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @GetMapping("/listSprints/{projectId}")
    public ResponseEntity<Void> listSprints(@PathVariable String projectId) {
        sprintService.listSprints(projectId);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }
}
