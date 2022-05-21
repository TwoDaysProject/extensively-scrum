package com.extensivelyscrum.backend.service;

import com.extensivelyscrum.backend.model.User;
import com.extensivelyscrum.backend.security.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserPrincipalDetailService implements UserDetailsService {
    private UserService userService;

    public UserPrincipalDetailService(UserService userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email){
        User user = null;
        try {
            user = userService.getUserWithEmail(email);
        } catch (Throwable e) {}
        if (user == null) throw new UsernameNotFoundException(email);
        return new UserPrincipal(user, userService);
    }
}
