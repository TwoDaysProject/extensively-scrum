package com.extensivelyscrum.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Epic extends BacklogItem{

    private List<BacklogItem> items;
}
