package com.extensivelyscrum.backend.service;


import com.extensivelyscrum.backend.dto.AddProjectMemberDto;
import com.extensivelyscrum.backend.dto.ListRoleDto;
import com.extensivelyscrum.backend.enums.RoleEnum;
import com.extensivelyscrum.backend.model.Project;
import com.extensivelyscrum.backend.model.Role;
import com.extensivelyscrum.backend.model.User;
import com.extensivelyscrum.backend.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final ProjectService projectService;

    private final UserService userService;


    public List<Role> getUserRolesInProject(User user, Project project) {
        return roleRepository.findAllByUserAndProject(user, project);
    }

    public void deleteByUserAndProject(User user, Project project) {
        Role role = roleRepository.findByUserAndProject(user, project).orElseThrow();
        roleRepository.delete(role);
        roleRepository.flush();
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

    public ListRoleDto addProjectMember(AddProjectMemberDto dto) {
        Project project = projectService.getProjectWithId(dto.projectId());
        User user = userService.getUserWithEmail(dto.email());
        return saveProjectMember(project, user, dto.role());
    }

    public ListRoleDto saveProjectMember(Project project, User user, RoleEnum roleEnum) {
        Role role = new Role();
        role.setUser(user);
        role.setProject(project);
        role.setRole(roleEnum);
        Role result= roleRepository.save(role);
        return new ListRoleDto(
                result.getProject().getId(),
                result.getProject().getName(),
                result.getUser().getId(),
                result.getUser().getFullName(),
                result.getRole()
        );
    }

    public List<Role> getUserRoles(User user) {
        return roleRepository.findAllByUser(user);
    }

    public void deleteRole(String id) {
        roleRepository.deleteById(id);
    }
}
