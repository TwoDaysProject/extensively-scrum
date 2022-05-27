package com.extensivelyscrum.backend.dtoMappers;

import com.extensivelyscrum.backend.dto.NewProjectDto;
import com.extensivelyscrum.backend.model.Project;

public class ProjectDtoMapper {

    public static Project createProjectDtoMapper(NewProjectDto newProjectDto, Project project){
        project.setName(newProjectDto.name());
        project.setDescription(newProjectDto.description());
        return project;
    }
}
