package com.example.todoapi.controllers;

import com.example.todoapi.dtos.ResponseDto;
import com.example.todoapi.dtos.StaffDTO;
import com.example.todoapi.services.ServiceImpl.StaffServiceImpl;
import com.example.todoapi.services.StaffService;
import com.example.todoapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staff")
public class RestStaffController {
    @Autowired
    StaffService staffService;

    @Operation(summary = "Lấy tất cả staff", description = "Trả về 1 list staff")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully retrieved"),
//            @ApiResponse(code = 404, message = "Not found - Don't have any book")
//    })
    @GetMapping
    public ResponseEntity<?> getAllStaff() {
        return ResponseEntity.ok(staffService.getAll());
    }

    @Operation(summary = "Thêm 1 staff", description = "Gửi về 1 staff")
    @PostMapping
    public ResponseEntity<?> saveStaff(@RequestBody StaffDTO staffDTO){
        return ResponseEntity.ok(staffService.saveOrUpdate(staffDTO));
    }

    @Operation(summary = "Sửa 1 staff", description = "Gửi về 1 staff có id")
    @PostMapping(value = "/update")
    public ResponseEntity<?> updateStaff(@RequestBody StaffDTO staffDTO){
        return ResponseEntity.ok(staffService.saveOrUpdate(staffDTO));
    }

    @Operation(summary = "Xoá 1 staff", description = "Gửi về id staff")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteByIdStaff(@PathVariable("id") Long id){
        return ResponseEntity.ok(staffService.deleteById(id));
    }

    @Operation(summary = "Tìm staff theo id", description = "Trả về 1 staff")
    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdStaff(@PathVariable("id") Long id){
        return ResponseEntity.ok(staffService.findById(id));
    }

    @Operation(summary = "Lấy tất cả user theo id của role", description = "Gửi về list user")
    @GetMapping("/getAllUser/{id}")
    public ResponseEntity<?> getAllUserByRoles(@PathVariable("id") int id){
        return ResponseEntity.ok(staffService.findAllByIdRole(id));
    }


}
