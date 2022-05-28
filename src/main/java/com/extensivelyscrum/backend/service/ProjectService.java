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

    public String getNextTag(String name, int tagCounter) {
        StringBuilder tagBuilder = new StringBuilder();
        Arrays.stream(name.split(" |-|_")).
                map((String word) -> Character.toUpperCase(word.charAt(0))).
                forEach(c -> tagBuilder.append(c));
        if (tagBuilder.toString().length() == 1) {
            return name.toUpperCase() + '-' + (tagCounter + 1);
        }
        tagBuilder.append('-');
        tagBuilder.append(tagCounter + 1);
        String res = tagBuilder.toString();
        System.out.println(tagBuilder);
        return res;
    }

    public void deleteById(String id) {
        projectRepository.deleteById(id);
    }

    public Project updateProject(Project project) {
        return projectRepository.save(project);
    }

}
