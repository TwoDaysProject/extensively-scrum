package com.extensivelyscrum.backend.dto;

import com.extensivelyscrum.backend.enums.BackLogType;
import com.extensivelyscrum.backend.model.BacklogItem;
import com.extensivelyscrum.backend.model.Epic;

import java.util.Date;

public record CreateBacklogItemDto(
        BackLogType type,
        String name,
        String description,
        String projectId,
        Date startDate,
        Date endDate,
        String parentEpicId, // this one is used just when the created backlog item has a parent epic
        BacklogItem referencedItem, // this one is used when type = Bug

        String reporterId
) {
}
