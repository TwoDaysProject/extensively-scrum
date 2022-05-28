package com.extensivelyscrum.backend.controller;


import com.extensivelyscrum.backend.dto.*;
import com.extensivelyscrum.backend.dtoMappers.TicketDtoMapper;
import com.extensivelyscrum.backend.model.BacklogItem;
import com.extensivelyscrum.backend.model.Ticket;
import com.extensivelyscrum.backend.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/ticket")
public class TicketController {

    private TicketService ticketService;

    @PostMapping("/create")
    public ResponseEntity<ListTicketsDto> createTicket(@RequestBody CreateTicketDto dto,
                                                       @RequestHeader("Authorization") JwtTokenDto tokenDto) {
        ListTicketsDto dto1 = ticketService.createTicket(dto, tokenDto);
        return new ResponseEntity<>(
                dto1,
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable String id) {
        ticketService.deleteWithId(id);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<ChangeTicketStatusDto> changeTicketStatus(@RequestBody ChangeTicketStatusDto dto) {
        return new ResponseEntity<>(
                ticketService.changeTicketStatus(dto),
                HttpStatus.OK
        );
    }

}
