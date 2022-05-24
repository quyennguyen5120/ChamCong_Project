package com.example.todoapi.services;

import com.example.todoapi.dtos.SalaryDto;
import com.example.todoapi.dtos.StaffSalaryDTO;
import org.apache.poi.ss.usermodel.Workbook;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.jdbc.Work;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpRequest;
import java.util.List;

public interface SalaryService {
    List<SalaryDto> getAll();
    SalaryDto findById(Long id);
    boolean deleteById(Long id);
    SalaryDto saveOrUpdate(SalaryDto salaryDto);
    StaffSalaryDTO calculateSalary(Long staffId, Integer month, Integer year);
    Workbook exportBySearchDto(HttpServletResponse response,Integer month,Integer year);
}
