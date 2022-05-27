package com.extensivelyscrum.backend.dto;

import com.extensivelyscrum.backend.enums.BackLogType;

public record BacklogItemMiniDto (
        String id,
        String name,
        String description,
        BackLogType type
){
}
