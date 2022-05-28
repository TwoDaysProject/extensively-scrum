package com.extensivelyscrum.backend.model;


import com.extensivelyscrum.backend.enums.BackLogType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Entity
public abstract class BacklogItem extends BacklogComponent {

    private Date startDate;

    private Date endDate;

    @OneToMany(mappedBy="backlogItem", cascade={CascadeType.ALL})
    private List<Ticket> ticketList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EPIC_ID")
    private Epic parentEpic;

    private int storyPoints;

    private BackLogType type;

    public BacklogItem() {}
}
