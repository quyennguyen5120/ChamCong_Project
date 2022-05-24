package com.example.todoapi.controllers;

import com.example.todoapi.dtos.SalaryDto;
import com.example.todoapi.dtos.StaffDTO;
import com.example.todoapi.dtos.StaffSalaryDTO;
import com.example.todoapi.entities.StaffEntity;
import com.example.todoapi.services.MailService;
import com.example.todoapi.services.SalaryService;
import com.example.todoapi.services.ServiceImpl.SalaryServiceImpl;
import com.example.todoapi.services.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Optional;

@RestController
@RequestMapping("/api/salary")
public class RestSalaryController {
    @Autowired
    SalaryService salaryService;
    @Autowired
    MailService mailService;
    @Autowired
    StaffService  staffService;

    @Operation(summary = "Tính lương trong 1 tháng theo staff id")
    @GetMapping("/staff/{staffId}")
    public ResponseEntity<?> calculateSalary(@PathVariable Long staffId,
                                             @RequestParam(value = "month", required = false) Optional<Integer> month,
                                             @RequestParam(value = "year", required = false) Optional<Integer> year){
        return ResponseEntity.ok().body(salaryService.calculateSalary(staffId, month.orElse(null), year.orElse(null)));
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
    public ResponseEntity<?> exportAllSalary(HttpServletResponse response,
                                            @RequestParam(value = "month", required = false) Optional<Integer> month,
                                             @RequestParam(value = "year", required = false) Optional<Integer> year){
        return ResponseEntity.ok(salaryService.exportBySearchDto(response, month.orElse(null), year.orElse(null)));
    }
    @Operation(summary = "Gửi mail lương tháng này cho tất cả nhân viên", description = "gửi từng 1 user 1")
    @GetMapping("/export1")
    public ResponseEntity<?> sendMailSalaryAllStaff(
                                                    @RequestParam(value = "month", required = false) Optional<Integer> month,
                                                    @RequestParam(value = "year", required = false) Optional<Integer> year){
        List<StaffDTO> listStaffDTOs= staffService.getAll();
        List<StaffSalaryDTO> listStaffSalaryDTO = new ArrayList<>();
        for (StaffDTO s : listStaffDTOs){
            listStaffSalaryDTO.add(salaryService.calculateSalary(s.getId(),month.orElse(null), year.orElse(null)));
        };
        return ResponseEntity.ok(mailService.sendMail(listStaffSalaryDTO));
    };


    @Operation(summary = "Xuất tất cả tiền lương ra file excel", description = "Tiền lương của 1 hay nhiều user")
    @GetMapping("/export_new")
    public ResponseEntity<?> exportAllSalary_new(HttpServletResponse response,
                                             @RequestParam(value = "month", required = false) Optional<Integer> month,
                                             @RequestParam(value = "year", required = false) Optional<Integer> year) throws IOException {
        File file = new File("poi-generated-file.xlsx");
        Files.copy(file.toPath(), response.getOutputStream());
        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        String contentDisposition = String.format("attachment; filename=%s", file.getName());
        int fileSize = Long.valueOf(file.length()).intValue();

        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", contentDisposition);
        response.setContentLength(fileSize);
        return new ResponseEntity<>(HttpStatus.OK);
//        return ResponseEntity.ok(salaryService.exportBySearchDto(response, month.orElse(null), year.orElse(null)));
    }
}
