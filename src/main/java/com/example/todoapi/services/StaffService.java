package com.example.todoapi.services;

import com.example.todoapi.dtos.StaffDTO;

import java.util.List;

public interface StaffService {
    public StaffDTO findById(Long id);
}
