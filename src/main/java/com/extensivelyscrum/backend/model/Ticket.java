package com.extensivelyscrum.backend.model;

import com.extensivelyscrum.backend.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@AllArgsConstructor
@Getter
@Setter
@Entity
public class Ticket extends BacklogComponent{

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "ASSIGNEE_ID")
    private User assignee;
    private Status status;
    @ManyToOne(cascade= {CascadeType.ALL})
    @JoinColumn(name = "SPRINT_ID")
    private Sprint sprint;
    @ManyToOne(cascade= {CascadeType.ALL})
    @JoinColumn(name = "BACKLOG_ITEM_ID")
    private BacklogItem backlogItem;
    public Ticket() {}
}
