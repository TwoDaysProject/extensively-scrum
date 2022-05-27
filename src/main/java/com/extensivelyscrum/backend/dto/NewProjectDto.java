package com.extensivelyscrum.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record NewProjectDto (
        String name,
        String description
){
}


