package com.example.todoapi.services;

import com.example.todoapi.dtos.InputDto.TimeKeepingInputDto;
import com.example.todoapi.dtos.TimekeepingDTO;

import java.util.List;

public interface TimeKeepingService {
    TimekeepingDTO requestTimeKeeping(Long staffId);
    TimekeepingDTO enableTimeKeeping(Long timeKeepingId);

    List<TimekeepingDTO> findByEnabled(TimeKeepingInputDto timeKeepingInputDto);

}
