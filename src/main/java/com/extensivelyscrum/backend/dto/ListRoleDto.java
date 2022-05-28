package com.extensivelyscrum.backend.dto;

import com.extensivelyscrum.backend.enums.RoleEnum;

public record ListRoleDto(
        String projectId,
        String projectName,
        String userId,
        String userName,
        RoleEnum role
) {
}
