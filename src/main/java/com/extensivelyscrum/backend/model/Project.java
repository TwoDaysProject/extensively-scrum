package com.extensivelyscrum.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",  strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotNull(message = "name is required!")
    private String name;

    @NotNull(message = "description is required")
    private String description;

    @Column(name = "tag_counter", nullable = true)
    private int tagCounter;

    @OneToMany(fetch = FetchType.LAZY)
    private List<BacklogItem> projectItems;

    public Project() {

    }

}
