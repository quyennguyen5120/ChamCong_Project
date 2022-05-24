package com.example.todoapi.controllers;

import com.example.todoapi.dtos.InputDto.TimeKeepingInputDto;
import com.example.todoapi.dtos.ResponseDto;
import com.example.todoapi.dtos.TimekeepingDTO;
import com.example.todoapi.repositories.TimeKeepingRepository;
import com.example.todoapi.services.TimeKeepingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/timekeeping")
public class RestTimeKeepingController {
    @Autowired
    TimeKeepingRepository timeKeepingRepository;
    @Autowired
    TimeKeepingService timeKeepingService;

//    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @Operation(summary = "Bắt đầu chấm công")
    @RequestMapping(value = "/{staffId}", method = RequestMethod.GET)
    public ResponseEntity<?> requestTimeKeeping(@PathVariable("staffId") Long staffId){
        return ResponseEntity.ok(timeKeepingService.requestTimeKeeping(staffId));
    }

//    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @Operation(summary = "Chấm công lúc về")
    @RequestMapping(value = "/logout/{staffId}", method = RequestMethod.GET)
    public ResponseEntity<?> logoutTimeKeeping(@PathVariable("staffId") Long staffId){
        return ResponseEntity.ok(timeKeepingService.logoutTimeKeeping(staffId));
    }

//    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping(value = "/enable/{timekeepingId}", method = RequestMethod.GET)
    public ResponseEntity<?> enableTimeKeeping(@PathVariable("timekeepingId") Long timekeepingId){
        return ResponseEntity.ok(timeKeepingService.enableTimeKeeping(timekeepingId));
    }

//    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping(value = "/searchByDto", method = RequestMethod.POST)
    public ResponseEntity<?> enableTimeKeeping(@RequestBody TimeKeepingInputDto timeKeepingInputDto){
        return ResponseEntity.ok(timeKeepingService.findByEnabled(timeKeepingInputDto));
    }

    //    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @Operation(summary = "Tìm chấm công theo staff id")
    @RequestMapping(value = "/getByStaff/{staffId}/{month}/{year}", method = RequestMethod.GET)
    public ResponseEntity<?> getByStaff(@PathVariable("staffId")Long staffId
            , @PathVariable("month") Integer month
            , @PathVariable("year") Integer year){
        return ResponseEntity.ok(timeKeepingService.getByStaff(staffId, month , year));
    }

    @Operation(summary = "Lấy tất cả chấm công của staff")
    @RequestMapping(value = "/getAllByStaff/{month}/{year}", method = RequestMethod.GET)
    public ResponseEntity<?> getByStaff(@PathVariable("month") Integer month
            , @PathVariable("year") Integer year){
        return ResponseEntity.ok(timeKeepingService.getByAllStaff(month , year));
    }

    @Operation(summary = "Thêm số giờ làm thêm staff")
    @RequestMapping(value = "/lamthem/{staffId}/{sogio}", method = RequestMethod.GET)
    public ResponseEntity<?> getByStaff(@PathVariable("staffId")Long staffId, @PathVariable("sogio")Long sogio){
        return ResponseEntity.ok(timeKeepingService.lamthem(staffId,sogio));
    }

    @Operation(summary = "Thêm số giờ staff về sớm")
    @RequestMapping(value = "/xinvesom/{staffId}", method = RequestMethod.GET)
    public ResponseEntity<?> xinvesom(@PathVariable("staffId")Long staffId){
        return ResponseEntity.ok(timeKeepingService.xinvesom(staffId));
    }
}
