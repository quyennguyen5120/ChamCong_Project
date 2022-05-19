package com.example.todoapi.services;


import com.example.todoapi.dtos.ManagerSessionDto;

import java.util.List;

public interface ManagerSessionService {
    Boolean request_vesom(Long staffId);
    Boolean request_lamthem(Long staffId, Long sogio);
    List<ManagerSessionDto> getAllVeSom();
    List<ManagerSessionDto> getAllLamThem();
    Boolean accept_vesom(Long staffId, Long time);
    Boolean accept_lamthem(Long staffId, Long time);
}
