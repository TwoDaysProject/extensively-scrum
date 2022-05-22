package com.example.demo.controller;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import com.extensivelyscrum.backend.model.Project;
import com.extensivelyscrum.backend.model.Role;
import com.extensivelyscrum.backend.model.User;
import com.extensivelyscrum.backend.repository.ProjcetRepository;
import com.extensivelyscrum.backend.repository.RoleRepository;
import com.extensivelyscrum.backend.repository.UserRepository;
import com.extensivelyscrum.backend.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RoleController {

    private RoleService roleService;
    private UserRepository userRepository;
    private ProjcetRepository projcetRepository;
    private RoleRepository roleRepository;








    @GetMapping("/role/{iduser}/{idproject}")
    List<Role> getRole(@PathVariable String iduser,@PathVariable String idproject){
        Optional<User> user =userRepository.findById(iduser);
        Optional<Project> project =projcetRepository.findById(idproject);


        List<Role> role = roleRepository.findAllByUserAndProject(user.get(),project.get());
        return role;

    }



    @PostMapping("/role")
    ResponseEntity<Role> createOrder(@Validated @RequestBody Role role) throws URISyntaxException{

        Role result= roleRepository.save(role);
        return ResponseEntity.created(new URI("/api/role" + result.getId())).body(result);

    }

    @PutMapping("/role/{projectid}/user/{userid}")
    Role assignprojectTouser(
            @PathVariable String userid,
            @PathVariable String projectid
    ) {
        Role role = roleRepository.findById(projectid).get();
        User user = userRepository.findById(userid).get();
        role.setUser(user);
        Role result= roleRepository.save(role);
        return result;
    }







}
