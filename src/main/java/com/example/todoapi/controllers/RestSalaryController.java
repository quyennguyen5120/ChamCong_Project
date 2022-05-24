package com.example.todoapi.controllers;

import com.example.todoapi.dtos.SalaryDto;
import com.example.todoapi.dtos.StaffDTO;
import com.example.todoapi.services.SalaryService;
import com.example.todoapi.services.ServiceImpl.SalaryServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/salary")
public class RestSalaryController {
    @Autowired
    SalaryService salaryService;

    @ApiOperation(value = "Tính lương trong 1 tháng theo staff id")
    @GetMapping("/staff/{staffId}")
    public ResponseEntity<?> calculateSalary(@PathVariable Long staffId, @RequestParam("month") int month){
        return ResponseEntity.ok().body(salaryService.calculateSalary(staffId, month));
    }

    @ApiOperation(value = "Thêm Số tiền lương", notes = "Tiền lương của 1 hay nhiều user")
    @PostMapping
    public ResponseEntity<?> saveStaff(@RequestBody SalaryDto salaryDto){
        return ResponseEntity.ok(salaryService.saveOrUpdate(salaryDto));
    }

    @ApiOperation(value = "Sửa Số tiền lương", notes = "Tiền lương của 1 hay nhiều user")
    @PatchMapping
    public ResponseEntity<?> updateStaff(@RequestBody SalaryDto salaryDto){
        return ResponseEntity.ok(salaryService.saveOrUpdate(salaryDto));
    }

    @ApiOperation(value = "Xoá tiền lương", notes = "Tiền lương của 1 hay nhiều user")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteByIdStaff(@PathVariable("id") Long id){
        return ResponseEntity.ok(salaryService.deleteById(id));
    }
    @ApiOperation(value = "Tìm tiền lương theo id", notes = "Tiền lương của 1 hay nhiều user")
    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdStaff(@PathVariable("id") Long id){
        return ResponseEntity.ok(salaryService.findById(id));
    }

    @ApiOperation(value = "Xem tất cả số tiền lương của những chức vụ khác nhau", notes = "Tiền lương của 1 hay nhiều user")
    @GetMapping()
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(salaryService.getAll());
    }

    @ApiOperation(value = "Xuất tất cả tiền lương ra file excel", notes = "Tiền lương của 1 hay nhiều user")
    @GetMapping("/export")
    public ResponseEntity<?> exportAllSalary(HttpServletResponse response){
        return ResponseEntity.ok(salaryService.exportBySearchDto(response));
    }
}
