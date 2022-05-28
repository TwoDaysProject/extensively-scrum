package com.extensivelyscrum.backend.controller;

import com.extensivelyscrum.backend.dto.ListBacklogItemsDto;
import com.extensivelyscrum.backend.dto.CreateBacklogItemDto;
import com.extensivelyscrum.backend.dto.JwtTokenDto;
import com.extensivelyscrum.backend.model.BacklogItem;
import com.extensivelyscrum.backend.service.BacklogItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/backlog")
@AllArgsConstructor
public class BacklogController {

    BacklogItemService backlogItemService;

    @PostMapping("/create")
    public ResponseEntity<ListBacklogItemsDto> createBacklogItem(@RequestBody CreateBacklogItemDto dto,
                                                                 @RequestHeader("Authorization") JwtTokenDto tokenDto) {
        ListBacklogItemsDto dto1 = backlogItemService.createBacklogItem(dto, tokenDto);
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
                HttpStatus.OK
        );
    }

    @GetMapping("/list/{projectId}")
    public ResponseEntity<List<ListBacklogItemsDto>> listBacklogItems(@PathVariable String projectId) {
        return new ResponseEntity<>(
                backlogItemService.listBacklogItems(projectId),
                HttpStatus.OK
        );
    }

}
