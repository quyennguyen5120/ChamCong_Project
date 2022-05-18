package com.example.todoapi.controllers;

import com.example.todoapi.dtos.ResponseDto;
import com.example.todoapi.dtos.StaffDTO;
import com.example.todoapi.services.ServiceImpl.StaffServiceImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staff")
public class RestStaffController {
    @Autowired
    StaffServiceImpl staffService;
    @GetMapping
    public ResponseEntity<?> getAllStaff() {
        return ResponseEntity.ok(staffService.getAll());
    }
    @PostMapping
    public ResponseEntity<?> saveStaff(@RequestBody StaffDTO staffDTO){
        return ResponseEntity.ok(staffService.saveOrUpdate(staffDTO));
    }
    @PatchMapping
    public ResponseEntity<?> updateStaff(@RequestBody StaffDTO staffDTO){
        return ResponseEntity.ok(staffService.saveOrUpdate(staffDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteByIdStaff(@PathVariable("id") Long id){
        return ResponseEntity.ok(staffService.deleteById(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> findByIdStaff(@PathVariable("id") Long id){
        return ResponseEntity.ok(staffService.findById(id));
    }

}
