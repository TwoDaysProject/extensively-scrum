package com.extensivelyscrum.backend.dto;

public record CreateTicketDto(
        String name,
        String description,
        String projectId,
        String parentId
) {
}
