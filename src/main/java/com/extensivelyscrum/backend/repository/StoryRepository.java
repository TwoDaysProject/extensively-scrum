package com.extensivelyscrum.backend.repository;

import com.extensivelyscrum.backend.model.BacklogItem;
import com.extensivelyscrum.backend.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, String> {
}
