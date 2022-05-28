package com.extensivelyscrum.backend.service;


import com.extensivelyscrum.backend.dto.CreateTicketDto;
import com.extensivelyscrum.backend.dto.JwtLoginDto;
import com.extensivelyscrum.backend.dto.JwtTokenDto;
import com.extensivelyscrum.backend.dto.ListTicketsDto;
import com.extensivelyscrum.backend.dtoMappers.TicketDtoMapper;
import com.extensivelyscrum.backend.model.BacklogItem;
import com.extensivelyscrum.backend.model.Project;
import com.extensivelyscrum.backend.model.Ticket;
import com.extensivelyscrum.backend.model.User;
import com.extensivelyscrum.backend.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TicketService {

    private TicketRepository ticketRepository;
    private ProjectService projectService;
    private UserService userService;
    private BacklogItemService backlogItemService;

    public ListTicketsDto createTicket(CreateTicketDto dto, JwtTokenDto tokenDto) {
        Ticket ticket = TicketDtoMapper.createTicketDtoMapper(dto, new Ticket());
        Project project = projectService.getProjectWithId(dto.projectId());
        String email = JwtLoginDto.getEmailFromJwtToken(tokenDto.token());
        User user = userService.getUserWithEmail(email);
        BacklogItem parent = null;
        if (dto.parentId() != null) parent = backlogItemService.getWithId(dto.projectId());
        ticket.setBacklogItem(parent);
        ticket.setProject(project);
        ticket.setReporter(user);
        ticket.setTag(projectService.getNextTag(ticket.getProject().getName(), ticket.getProject().getTagCounter()));
        ticket = ticketRepository.save(ticket);
        return new ListTicketsDto(
                ticket.getId(),
                ticket.getName(),
                ticket.getDescription(),
                ticket.getTag(),
                (dto.parentId() != null) ? ticket.getBacklogItem().getName() : null
        );
    }

    public void deleteWithId(String id) {
        ticketRepository.deleteById(id);
    }


}
