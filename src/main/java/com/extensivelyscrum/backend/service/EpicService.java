package com.extensivelyscrum.backend.service;

import com.extensivelyscrum.backend.model.Epic;
import com.extensivelyscrum.backend.repository.EpicRepository;
import org.springframework.stereotype.Service;


@Service
public class EpicService {
    EpicRepository epicRepository;

    public EpicService(EpicRepository epicRepository) {
        this.epicRepository = epicRepository;
    }
    public Epic getEpicWithId(String id) {
        return epicRepository.findById(id).orElseThrow(() -> new RuntimeException());
    }
}
