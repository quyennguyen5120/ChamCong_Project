package com.example.todoapi.controllers;

import com.example.todoapi.dtos.ResponseDto;
import com.example.todoapi.dtos.TimekeepingDTO;
import com.example.todoapi.repositories.TimeKeepingRepository;
import com.example.todoapi.services.TimeKeepingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/timekeeping")
public class RestTimeKeepingController {
    @Autowired
    TimeKeepingRepository timeKeepingRepository;
    @Autowired
    TimeKeepingService timeKeepingService;

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping(value = "/{staffId}", method = RequestMethod.GET)
    public ResponseEntity<?> requestTimeKeeping(@PathVariable("staffId") Long staffId){
        return ResponseEntity.ok(timeKeepingService.requestTimeKeeping(staffId));
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping(value = "/enable/{timekeepingId}", method = RequestMethod.GET)
    public ResponseEntity<?> enableTimeKeeping(@PathVariable("timekeepingId") Long timekeepingId){

        return null;
    }
}
