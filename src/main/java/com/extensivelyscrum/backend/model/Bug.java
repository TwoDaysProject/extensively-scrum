package com.extensivelyscrum.backend.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Bug extends BacklogItem{

    private BacklogItem referencedItem;

}
