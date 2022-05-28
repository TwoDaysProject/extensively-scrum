package com.extensivelyscrum.backend.dto;

import com.extensivelyscrum.backend.enums.Status;

public record ChangeTicketStatusDto(
        String ticketId,
        Status status
) {
}
