package com.extensivelyscrum.backend.service;

import com.extensivelyscrum.backend.dto.NewProjectDto;
import com.extensivelyscrum.backend.dtoMappers.ProjectDtoMapper;
import com.extensivelyscrum.backend.dtoMappers.UserDtoMapper;
import com.extensivelyscrum.backend.model.Project;
import com.extensivelyscrum.backend.model.User;
import org.springframework.stereotype.Service;
import com.extensivelyscrum.backend.repository.ProjcetRepository;

import java.util.Arrays;

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
    public Project createProject(NewProjectDto newProjectDto){
        Project project = ProjectDtoMapper.createProjectDtoMapper(newProjectDto, new Project());
        return projectRepository.save(project);
    }

    public String getNextTag(Project project) {
        StringBuilder tagBuilder = new StringBuilder();
        tagBuilder.append(
                Arrays.stream(project.getName().split(" |-|_")).
                        map((String word) -> word.charAt(0))
        );
        tagBuilder.append(project.getTagCounter() + 1);
        project.setTagCounter(project.getTagCounter() + 1);
        return tagBuilder.toString();
    }

}
