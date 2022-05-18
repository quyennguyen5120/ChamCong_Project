package com.example.todoapi.services;

import com.example.todoapi.dtos.StaffDTO;

import java.util.List;

public interface StaffService {
    List<StaffDTO> getAll();
    StaffDTO findById(Long id);
    boolean deleteById(Long id);
    StaffDTO saveOrUpdate(StaffDTO staffDTO);

}
