package com.extensivelyscrum.backend.repository;

import com.extensivelyscrum.backend.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, String> {
}
