package com.example.todoapi.services;

import com.example.todoapi.dtos.SalaryDto;
import com.example.todoapi.dtos.StaffSalaryDTO;
import org.apache.poi.ss.usermodel.Workbook;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.jdbc.Work;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpRequest;
import java.util.List;

public interface SalaryService {
    List<SalaryDto> getAll();
    SalaryDto findById(Long id);
    boolean deleteById(Long id);
    SalaryDto saveOrUpdate(SalaryDto salaryDto);
    StaffSalaryDTO calculateSalary(Long staffId, int month);
    Workbook exportBySearchDto(HttpServletResponse response);
}
