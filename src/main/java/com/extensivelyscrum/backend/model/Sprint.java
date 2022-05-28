package com.extensivelyscrum.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Entity
public class Sprint{

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",  strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotNull(message = "name is required!")
    private String name;

    @NotNull(message = "description is required")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId")
    private Project project;

    private Date startDate;

    private Date endDate;

    private boolean activated;

    @OneToMany(mappedBy="sprint", cascade={CascadeType.ALL})
    private List<Ticket> ticketList;

    public Sprint() {}
}
