package com.extensivelyscrum.backend.controller;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import com.extensivelyscrum.backend.dto.AddProjectMemberDto;
import com.extensivelyscrum.backend.dto.GetRoleDto;
import com.extensivelyscrum.backend.dto.JwtLoginDto;
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
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/role")
@AllArgsConstructor
public class RoleController {

    private RoleService roleService;
    private RoleRepository roleRepository;

    @GetMapping("/getRoles")
    public ResponseEntity<List<Role>> getCurrentUserRolesInProject(@RequestBody GetRoleDto dto,
                 @RequestHeader("Authorization") String jwtToken){
        String email = JwtLoginDto.getEmailFromJwtToken(jwtToken);
        return new ResponseEntity<>(
                roleService.getCurrentUserRolesInProject(email, dto.idProject()),
                HttpStatus.OK
        );
    }

//    @PostMapping("/role")
//    ResponseEntity<Role> createOrder(@Validated @RequestBody Role role) throws URISyntaxException{
//        Role result= roleRepository.save(role);
//        return ResponseEntity.created(new URI("/api/role" + result.getId())).body(result);
//    }

    @PutMapping("/addProjectMember")
    public ResponseEntity<Role> addProjectMembers(@RequestBody AddProjectMemberDto addProjectMemberDto) {
        return new ResponseEntity<>(
                roleService.addProjectMember(addProjectMemberDto.UserEmail(),
                        addProjectMemberDto.idProject(),
                        addProjectMemberDto.role()),
                HttpStatus.OK
        );
    }







}
