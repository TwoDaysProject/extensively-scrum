package com.extensivelyscrum.backend.service;


import com.extensivelyscrum.backend.dto.CreateSprintDto;
import com.extensivelyscrum.backend.dto.ListSprintsDto;
import com.extensivelyscrum.backend.dtoMappers.SprintDtoMapper;
import com.extensivelyscrum.backend.model.Project;
import com.extensivelyscrum.backend.model.Sprint;
import com.extensivelyscrum.backend.repository.SprintRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Getter
@Setter
public class SprintService {

    private SprintRepository sprintRepository;

    private ProjectService projectService;

    public ListSprintsDto createSprint(CreateSprintDto dto) {
        Sprint sprint = SprintDtoMapper.createSprintDtoMapper(dto, new Sprint());
        Project project = projectService.getProjectWithId(dto.projectId());
        sprint.setProject(project);
        sprint = sprintRepository.save(sprint);
        return new ListSprintsDto(
            sprint.getId(),
            sprint.getName(),
            sprint.getDescription(),
            sprint.isActivated()
        );
    }

    public void deleteWithId(String id) {
        sprintRepository.deleteById(id);
    }

}
