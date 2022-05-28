package com.extensivelyscrum.backend.service;


import com.extensivelyscrum.backend.dto.AddProjectMemberDto;
import com.extensivelyscrum.backend.enums.RoleEnum;
import com.extensivelyscrum.backend.model.Project;
import com.extensivelyscrum.backend.model.Role;
import com.extensivelyscrum.backend.model.User;
import com.extensivelyscrum.backend.repository.RoleRepository;
import com.extensivelyscrum.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    RoleRepository roleRepository;

    ProjectService projectService;

    UserService userService;

    public RoleService(RoleRepository roleRepository,
                       ProjectService projectService,
                       UserService userService) {
        this.roleRepository = roleRepository;
        this.projectService = projectService;
        this.userService = userService;
    }
    public List<Role> getUserRolesInProject(User user, Project project) {
        return roleRepository.findAllByUserAndProject(user, project);
    }

    public List<Role> getCurrentUserRolesInProject(String userEmail, String idProject) {
        Project project = projectService.getProjectWithId(idProject);
        User user = userService.getUserWithEmail(userEmail);
        return roleRepository.findAllByUserAndProject(user, project);
    }

    public Role getRoleWithId(String roleId) {
        return roleRepository.findById(roleId).orElseThrow(
                () -> new RuntimeException()
        );
    }

    public Role addProjectMember(AddProjectMemberDto dto) {
        Project project = projectService.getProjectWithId(dto.idProject());
        User user = userService.getUserWithEmail(dto.UserEmail());
        Role role = new Role();
        role.setUser(user);
        role.setProject(project);
        role.setRole(dto.role());
        Role result= roleRepository.save(role);
        return result;
    }

    public List<Role> getUserRoles(User user) {
        return roleRepository.findAllByUser(user);
    }
}
