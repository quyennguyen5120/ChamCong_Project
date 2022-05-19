package com.example.todoapi.controllers;

import com.example.todoapi.dtos.SalaryDto;
import com.example.todoapi.dtos.StaffDTO;
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
    public ResponseEntity<?> calculateSalary(@PathVariable Long staffId){
        return ResponseEntity.ok().body(salaryService);
    }
    @PostMapping
    public ResponseEntity<?> saveStaff(@RequestBody SalaryDto salaryDto){
        return ResponseEntity.ok(salaryService.saveOrUpdate(salaryDto));
    }
    @PatchMapping
    public ResponseEntity<?> updateStaff(@RequestBody SalaryDto salaryDto){
        return ResponseEntity.ok(salaryService.saveOrUpdate(salaryDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteByIdStaff(@PathVariable("id") Long id){
        return ResponseEntity.ok(salaryService.deleteById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdStaff(@PathVariable("id") Long id){
        return ResponseEntity.ok(salaryService.findById(id));
    }
    @GetMapping()
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(salaryService.getAll());
    }
}
