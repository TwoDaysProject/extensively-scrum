package com.extensivelyscrum.backend.controller;
import java.util.List;
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

    @GetMapping("/getRoles")
    public ResponseEntity<List<Role>> getCurrentUserRolesInProject(@RequestBody GetRoleDto dto,
                 @RequestHeader("Authorization") String jwtToken){
        String email = JwtLoginDto.getEmailFromJwtToken(jwtToken);
        return new ResponseEntity<>(
                roleService.getCurrentUserRolesInProject(email, dto.idProject()),
                HttpStatus.OK
        );
    }

    @PostMapping("/addProjectMember")
    public ResponseEntity<Role> addProjectMembers(@RequestBody AddProjectMemberDto dto) {
        return new ResponseEntity<>(
                roleService.addProjectMember(dto),
                HttpStatus.OK
        );
    }







}
