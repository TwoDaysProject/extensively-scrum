package com.extensivelyscrum.backend.dtoMappers;

import com.extensivelyscrum.backend.dto.CreateSprintDto;
import com.extensivelyscrum.backend.dto.CreateUserDto;
import com.extensivelyscrum.backend.model.Sprint;

public class SprintDtoMapper {

    public static Sprint createSprintDtoMapper(CreateSprintDto dto, Sprint sprint) {

        sprint.setDescription(dto.description());
        sprint.setName(dto.name());
        sprint.setEndDate(dto.endDate());
        sprint.setActivated(false);
        sprint.setStartDate(dto.startDate());

        return sprint;
    }
}
