package com.extensivelyscrum.backend.dtoMappers;

import com.extensivelyscrum.backend.dto.CreateBacklogItemDto;
import com.extensivelyscrum.backend.enums.BackLogType;
import com.extensivelyscrum.backend.model.BacklogItem;
import com.extensivelyscrum.backend.model.Bug;

public class BacklogItemDtoMapper {

    public static void createBacklogItemDtoMapper(CreateBacklogItemDto dto, BacklogItem item) {
        if(dto.type() == BackLogType.BUG) ((Bug)item).setReferencedItem(dto.referencedItem());
        item.setDescription(dto.description());
        item.setEndDate(dto.endDate());
        item.setName(dto.name());
        item.setStartDate(dto.startDate());
    }
}
