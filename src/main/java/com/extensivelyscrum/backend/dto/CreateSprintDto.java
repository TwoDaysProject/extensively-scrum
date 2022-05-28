package com.extensivelyscrum.backend.dto;

import java.util.Date;

public record CreateSprintDto(
        String name,
        String description,
        String projectId,
        Date startDate,
        Date endDate
) {
}
