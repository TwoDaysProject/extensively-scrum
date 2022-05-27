package com.extensivelyscrum.backend.controller;

import com.extensivelyscrum.backend.dto.BacklogItemMiniDto;
import com.extensivelyscrum.backend.dto.CreateBacklogItemDto;
import com.extensivelyscrum.backend.dto.JwtTokenDto;
import com.extensivelyscrum.backend.model.BacklogItem;
import com.extensivelyscrum.backend.service.BacklogItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/backlog")
public class BackLogController {

    BacklogItemService backlogItemService;

    public BackLogController(BacklogItemService backlogItemService) {
        this.backlogItemService = backlogItemService;
    }

    @PostMapping("/create")
    public ResponseEntity<BacklogItemMiniDto> createBacklogItem(@RequestBody CreateBacklogItemDto dto,
                                                                @RequestHeader("Authorization") JwtTokenDto tokenDto) {
        BacklogItemMiniDto dto1 = backlogItemService.createBacklogItem(dto, tokenDto);
        return new ResponseEntity<>(
                dto1,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/getWithId/{id}")
    public ResponseEntity<BacklogItem> get(@PathVariable String id,
                                    @RequestHeader("Authorization") JwtTokenDto tokenDto) {
        BacklogItem item = backlogItemService.getWithId(id);
        return new ResponseEntity<>(
                item,
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBacklogItem(@PathVariable String id) {
        backlogItemService.deleteWithId(id);
        return new ResponseEntity<>(
                HttpStatus.CREATED
        );
    }

}
