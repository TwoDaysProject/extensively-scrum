package com.extensivelyscrum.backend.security;

import com.extensivelyscrum.backend.model.Project;
import com.extensivelyscrum.backend.model.Role;
import com.extensivelyscrum.backend.model.User;
import com.extensivelyscrum.backend.service.RoleService;
import com.extensivelyscrum.backend.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private User user;
    private Project project;
    private UserService userService;
    private RoleService roleService;

    public UserPrincipal(User user, Project project, UserService userService, RoleService roleService) {
        this.user = user;
        this.project = project;
        this.userService = userService;
        this.roleService = roleService;
    }

    public UserPrincipal(User user, UserService userService) {
        this.user = user;
        this.userService = userService;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (project != null) {
            roleService.getUserRolesInProject(user, project)
                    .stream()
                    .forEach(
                            (Role role) -> {
                                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole().name().toUpperCase()));
                            });
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
