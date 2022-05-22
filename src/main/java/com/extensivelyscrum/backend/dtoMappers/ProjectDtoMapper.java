package com.extensivelyscrum.backend.dtoMappers;

import com.extensivelyscrum.backend.dto.NewProjectDto;
import com.extensivelyscrum.backend.model.Project;

public class ProjectDtoMapper {

    public static Project  createProjectDtoMapper(NewProjectDto newProjectDto){
        Project project = new Project();
        project.setName(newProjectDto.getName());
        project.setDescription(newProjectDto.getDescription());

        return project;
    }
}
