package com.extensivelyscrum.backend.service;

import com.extensivelyscrum.backend.dto.*;
import com.extensivelyscrum.backend.dtoMappers.ProjectDtoMapper;
import com.extensivelyscrum.backend.enums.RoleEnum;
import com.extensivelyscrum.backend.model.Project;
import com.extensivelyscrum.backend.model.Role;
import com.extensivelyscrum.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.extensivelyscrum.backend.repository.ProjcetRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class ProjectService {
    private ProjcetRepository projectRepository;
    private UserService userService;
    private RoleService roleService;

    public ProjectService(ProjcetRepository projectRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(@Lazy RoleService roleService) {
        this.roleService = roleService;
    }

    public Project getProjectWithId(String id) {
        return projectRepository.findById(id).orElseThrow(
                () -> new RuntimeException()
        );
    }

    public ListProjectDto getProjectDtoWithId(String id) {
        Project project = getProjectWithId(id);
        return new ListProjectDto(
                project.getId(),
                project.getName(),
                project.getDescription()
        );
    }
    public ListProjectDto createProject(NewProjectDto newProjectDto, JwtTokenDto tokenDto){
        Project project = ProjectDtoMapper.createProjectDtoMapper(newProjectDto, new Project());
        String email = JwtLoginDto.getEmailFromJwtToken(tokenDto.token());
        User user = userService.getUserWithEmail(email);
        project =  projectRepository.save(project);
        roleService.saveProjectMember(project, user, RoleEnum.SCRUM_MASTER);
        return new ListProjectDto(
                project.getId(),
                project.getName(),
                project.getDescription()
        );
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

    public void deleteById(String id, JwtTokenDto dto) {
        Project project = projectRepository.getById(id);
        String email = JwtLoginDto.getEmailFromJwtToken(dto.token());
        User user = userService.getUserWithEmail(email);
        roleService.deleteByUserAndProject(user, project);
        projectRepository.delete(project);
        projectRepository.flush();
    }

    public Project updateProject(Project project) {
        return projectRepository.save(project);
    }

    public List<ListProjectDto> listUserProjects(JwtTokenDto jwtToken) {
        String email = JwtLoginDto.getEmailFromJwtToken(jwtToken.token());
        User user = userService.getUserWithEmail(email);
        List<Role> roleList = roleService.getUserRoles(user);
        List<Project> projectList = roleList.stream().map(
                (Role role) -> projectRepository.findById(role.getProject().getId()).get()
        ).toList();

        return projectList.stream().map(
                (Project project) -> new ListProjectDto(
                        project.getId(),
                        project.getName(),
                        project.getDescription()
                )
        ).toList();
    }

}
