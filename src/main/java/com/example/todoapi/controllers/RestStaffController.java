package com.example.todoapi.controllers;

import com.example.todoapi.dtos.ResponseDto;
import com.example.todoapi.dtos.StaffDTO;
import com.example.todoapi.services.ServiceImpl.StaffServiceImpl;
import com.example.todoapi.services.StaffService;
import com.example.todoapi.services.UserService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "Lấy tất cả staff", notes = "Trả về 1 list staff")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully retrieved"),
//            @ApiResponse(code = 404, message = "Not found - Don't have any book")
//    })
    @GetMapping
    public ResponseEntity<?> getAllStaff() {
        return ResponseEntity.ok(staffService.getAll());
    }

    @ApiOperation(value = "Thêm 1 staff", notes = "Gửi về 1 staff")
    @PostMapping
    public ResponseEntity<?> saveStaff(@RequestBody StaffDTO staffDTO){
        return ResponseEntity.ok(staffService.saveOrUpdate(staffDTO));
    }

    @ApiOperation(value = "Sửa 1 staff", notes = "Gửi về 1 staff có id")
    @PostMapping(value = "/update")
    public ResponseEntity<?> updateStaff(@RequestBody StaffDTO staffDTO){
        return ResponseEntity.ok(staffService.saveOrUpdate(staffDTO));
    }

    @ApiOperation(value = "Xoá 1 staff", notes = "Gửi về id staff")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteByIdStaff(@PathVariable("id") Long id){
        return ResponseEntity.ok(staffService.deleteById(id));
    }

    @ApiOperation(value = "Tìm staff theo id", notes = "Trả về 1 staff")
    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdStaff(@PathVariable("id") Long id){
        return ResponseEntity.ok(staffService.findById(id));
    }

    @ApiOperation(value = "Lấy tất cả user theo id của role", notes = "Gửi về list user")
    @GetMapping("/getAllUser/{id}")
    public ResponseEntity<?> getAllUserByRoles(@PathVariable("id") int id){
        return ResponseEntity.ok(staffService.findAllByIdRole(id));
    }


}
