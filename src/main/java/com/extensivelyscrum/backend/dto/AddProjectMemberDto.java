package com.extensivelyscrum.backend.dto;

import com.extensivelyscrum.backend.enums.RoleEnum;

public record AddProjectMemberDto(
        String idProject,
        String UserEmail,
        RoleEnum role
) {
}
