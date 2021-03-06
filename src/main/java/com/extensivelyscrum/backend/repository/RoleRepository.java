package com.extensivelyscrum.backend.repository;

import com.extensivelyscrum.backend.model.Project;
import com.extensivelyscrum.backend.model.Role;
import com.extensivelyscrum.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    List<Role> findAllByUserAndProject(User user, Project project);

    Optional<Role> findByUserAndProject(User user, Project project);
    void deleteByUserAndProject(User user, Project project);

    List<Role> findAllByUser(User user);
}
