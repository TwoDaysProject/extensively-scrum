package com.extensivelyscrum.backend.dto;

public record SendMailDto(
        String toEmail,
        String body,
        String subject
) {
}
