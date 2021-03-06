package com.extensivelyscrum.backend.dto;

import com.extensivelyscrum.backend.enums.RoleEnum;

public record AddProjectMemberDto(
        String projectId,
        String email,
        RoleEnum role
) {
}
