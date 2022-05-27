package com.extensivelyscrum.backend.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@AllArgsConstructor
@Getter
@Setter
@Entity
public class Bug extends BacklogItem{

    @OneToOne
    @JoinColumn(name = "REFERENCED_ITEM_ID")
    private BacklogItem referencedItem;

    public Bug() {}
}
