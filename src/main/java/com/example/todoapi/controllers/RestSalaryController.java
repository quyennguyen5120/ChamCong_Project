package com.example.todoapi.controllers;

import com.example.todoapi.services.SalaryService;
import com.example.todoapi.services.ServiceImpl.SalaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/salary")
public class RestSalaryController {
    @Autowired
    SalaryService salaryService;

    @GetMapping("/{staffId}")
    public ResponseEntity<?> calculateSalary(@PathVariable Long staffId){
        return ResponseEntity.ok().body(salaryService);
    }
}
