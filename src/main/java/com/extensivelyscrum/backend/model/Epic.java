package com.extensivelyscrum.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Entity
public class Epic extends BacklogItem{

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentEpic")
    private List<BacklogItem> items;
    public Epic() {}
}
