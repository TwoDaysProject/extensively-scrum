package com.extensivelyscrum.backend.dto;

public record SendMailDto(
        String email,
        String body,
        String subject
) {
}
