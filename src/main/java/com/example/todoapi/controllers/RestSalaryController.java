package com.example.todoapi.controllers;

import com.example.todoapi.services.SalaryService;
import com.example.todoapi.services.ServiceImpl.SalaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/salary")
public class RestSalaryController {
    @Autowired
    SalaryService salaryService;

    @GetMapping("/{staffId}")
    public ResponseEntity<?> calculateSalary(@PathVariable Long staffId, @RequestParam("month") int month){
        return ResponseEntity.ok().body(salaryService.calculateSalary(staffId, month));
    }
}
