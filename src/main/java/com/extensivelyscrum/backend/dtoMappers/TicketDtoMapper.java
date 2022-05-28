package com.extensivelyscrum.backend.dtoMappers;

import com.extensivelyscrum.backend.dto.CreateBacklogItemDto;
import com.extensivelyscrum.backend.dto.CreateTicketDto;
import com.extensivelyscrum.backend.enums.BackLogType;
import com.extensivelyscrum.backend.model.BacklogItem;
import com.extensivelyscrum.backend.model.Bug;
import com.extensivelyscrum.backend.model.Ticket;

public class TicketDtoMapper {

    public static Ticket createTicketDtoMapper(CreateTicketDto dto, Ticket ticket) {
        ticket.setDescription(dto.description());
        ticket.setName(dto.name());
        return ticket;
    }
}
