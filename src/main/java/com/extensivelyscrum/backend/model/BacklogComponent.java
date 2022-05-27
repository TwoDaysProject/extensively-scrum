package com.extensivelyscrum.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
public abstract class BacklogComponent {

    @NotNull(message = "reporter must not be null")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "REPORTER_ID")
    private User reporter;

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

    private String tag;

    public BacklogComponent() {}
}
