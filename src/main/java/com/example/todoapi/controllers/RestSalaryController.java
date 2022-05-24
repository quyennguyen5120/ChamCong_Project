package com.example.todoapi.controllers;

import com.example.todoapi.dtos.SalaryDto;
import com.example.todoapi.dtos.StaffDTO;
import com.example.todoapi.services.SalaryService;
import com.example.todoapi.services.ServiceImpl.SalaryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/salary")
public class RestSalaryController {
    @Autowired
    SalaryService salaryService;

    @Operation(summary = "Tính lương trong 1 tháng theo staff id")
    @GetMapping("/staff/{staffId}")
    public ResponseEntity<?> calculateSalary(@PathVariable Long staffId, @RequestParam("month") Integer month, @RequestParam("year") Integer year){
        return ResponseEntity.ok().body(salaryService.calculateSalary(staffId, month, year));
    }

    @Operation(summary = "Thêm Số tiền lương", description = "Tiền lương của 1 hay nhiều user")
    @PostMapping
    public ResponseEntity<?> saveStaff(@RequestBody SalaryDto salaryDto){
        return ResponseEntity.ok(salaryService.saveOrUpdate(salaryDto));
    }

    @Operation(summary = "Sửa Số tiền lương", description = "Tiền lương của 1 hay nhiều user")
    @PatchMapping
    public ResponseEntity<?> updateStaff(@RequestBody SalaryDto salaryDto){
        return ResponseEntity.ok(salaryService.saveOrUpdate(salaryDto));
    }

    @Operation(summary = "Xoá tiền lương", description = "Tiền lương của 1 hay nhiều user")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteByIdStaff(@PathVariable("id") Long id){
        return ResponseEntity.ok(salaryService.deleteById(id));
    }
    @Operation(summary = "Tìm tiền lương theo id", description = "Tiền lương của 1 hay nhiều user")
    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdStaff(@PathVariable("id") Long id){
        return ResponseEntity.ok(salaryService.findById(id));
    }

    @Operation(summary = "Xem tất cả số tiền lương của những chức vụ khác nhau", description = "Tiền lương của 1 hay nhiều user")
    @GetMapping()
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(salaryService.getAll());
    }

    @Operation(summary = "Xuất tất cả tiền lương ra file excel", description = "Tiền lương của 1 hay nhiều user")
    @GetMapping("/export")
    public ResponseEntity<?> exportAllSalary(HttpServletResponse response){
        return ResponseEntity.ok(salaryService.exportBySearchDto(response));
    }
}
