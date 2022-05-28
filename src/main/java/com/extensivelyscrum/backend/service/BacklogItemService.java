package com.extensivelyscrum.backend.service;

import com.extensivelyscrum.backend.dto.ListBacklogItemsDto;
import com.extensivelyscrum.backend.dto.CreateBacklogItemDto;
import com.extensivelyscrum.backend.dto.JwtLoginDto;
import com.extensivelyscrum.backend.dto.JwtTokenDto;
import com.extensivelyscrum.backend.dtoMappers.BacklogItemDtoMapper;
import com.extensivelyscrum.backend.enums.BackLogType;
import com.extensivelyscrum.backend.factories.backlogItemFactory.BacklogItemFactory;
import com.extensivelyscrum.backend.factories.backlogItemFactory.BugFactory;
import com.extensivelyscrum.backend.factories.backlogItemFactory.EpicFactory;
import com.extensivelyscrum.backend.factories.backlogItemFactory.StoryFactory;
import com.extensivelyscrum.backend.model.BacklogItem;
import com.extensivelyscrum.backend.repository.BacklogItemRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class BacklogItemService {
    private final BacklogItemRepository backlogItemRepository;
    private final UserService userService;
    private final ProjectService projectService;
    private final EpicService bugService;

    public BacklogItemService(BacklogItemRepository backlogItemRepository,
                              UserService userService,
                              ProjectService projectService,
                              EpicService bugService) {
        this.backlogItemRepository = backlogItemRepository;
        this.userService = userService;
        this.projectService = projectService;
        this.bugService = bugService;
    }

    public ListBacklogItemsDto createBacklogItem(@NotNull CreateBacklogItemDto dto, JwtTokenDto tokenDto) {

        BacklogItemFactory backlogItemFactory;

        switch (dto.type()) {
            case BUG -> backlogItemFactory = new BugFactory();
            case EPIC -> backlogItemFactory = new EpicFactory();
            case STORY -> backlogItemFactory = new StoryFactory();
            default -> throw new RuntimeException();
        }
        BacklogItem backlogItem = backlogItemFactory.createBacklogItem();
        BacklogItemDtoMapper.createBacklogItemDtoMapper(dto, backlogItem);
        String emailReporter = JwtLoginDto.getEmailFromJwtToken(tokenDto.token());
        backlogItem.setReporter(userService.getUserWithEmail(emailReporter));
        backlogItem.setProject(projectService.getProjectWithId(dto.projectId()));
        backlogItem.setTag(projectService.getNextTag(backlogItem.getProject().getName(), backlogItem.getProject().getTagCounter()));
        backlogItem.getProject().setTagCounter(backlogItem.getProject().getTagCounter() + 1);
        backlogItem.setProject(projectService.updateProject(backlogItem.getProject()));

        if (dto.parentEpicId() != null && BackLogType.valueOf(dto.parentEpicId()) == BackLogType.EPIC)
            backlogItem.setParentEpic(bugService.getEpicWithId(dto.parentEpicId()));
        backlogItem = backlogItemRepository.save(backlogItem);
        return new ListBacklogItemsDto(
                backlogItem.getId(),
                backlogItem.getName(),
                backlogItem.getDescription(),
                dto.type(),
                backlogItem.getTag());
    }

    public BacklogItem getWithId(String id) {
        return backlogItemRepository.findById(id).orElseThrow(() -> new RuntimeException());
    }

    public void deleteWithId(String id) {
        backlogItemRepository.deleteById(id);
    }
}
