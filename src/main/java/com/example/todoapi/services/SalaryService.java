package com.example.todoapi.services;

import com.example.todoapi.dtos.SalaryDto;
import com.example.todoapi.dtos.StaffSalaryDTO;

import java.util.List;

public interface SalaryService {
    List<SalaryDto> getAll();
    SalaryDto findById(Long id);
    boolean deleteById(Long id);
    SalaryDto saveOrUpdate(SalaryDto salaryDto);
    StaffSalaryDTO calculateSalary(Long staffId, int month);
}
