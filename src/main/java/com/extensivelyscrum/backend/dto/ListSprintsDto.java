package com.extensivelyscrum.backend.dto;

public record ListSprintsDto(
        String id,
        String name,
        String description,
        boolean active
) {
}
