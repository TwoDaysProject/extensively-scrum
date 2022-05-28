package com.extensivelyscrum.backend.dto;

import com.extensivelyscrum.backend.enums.BackLogType;

public record ListBacklogItemsDto(
        String id,
        String name,
        String description,
        BackLogType type,
        String tag
){
}
