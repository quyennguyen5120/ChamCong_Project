package com.example.todoapi.services;

import com.example.todoapi.dtos.TimekeepingDTO;

public interface TimeKeepingService {
    TimekeepingDTO requestTimeKeeping(Long staffId);
    TimekeepingDTO enableTimeKeeping(Long timeKeepingId);

}
