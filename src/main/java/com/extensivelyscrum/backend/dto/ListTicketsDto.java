package com.extensivelyscrum.backend.dto;

public record ListTicketsDto (
        String id,
        String name,
        String description,
        String tag,
        String parentName
){
}
