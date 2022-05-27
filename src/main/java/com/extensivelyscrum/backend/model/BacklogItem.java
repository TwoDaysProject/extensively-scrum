package com.extensivelyscrum.backend.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
public abstract class BacklogItem extends BacklogComponent {

    private Date startDate;

    private Date endDate;

    private List<Ticket> ticketList;
    public BacklogItem() {}

}
