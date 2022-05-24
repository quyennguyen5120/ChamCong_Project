package com.example.todoapi.controllers;

import com.example.todoapi.dtos.ManagerSessionDto;
import com.example.todoapi.dtos.TimekeepingDTO;
import com.example.todoapi.repositories.ManagerSessionRepository;
import com.example.todoapi.services.ManagerSessionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/managersession")
public class RestManagerSessionController {
    @Autowired
    ManagerSessionRepository managerSessionRepository;

    @Autowired
    ManagerSessionService managerSessionService;

    @ApiOperation(value = "", notes = "")
    @GetMapping(value = "/request_vesom/{staffId}")
    public Boolean request_vesom(@PathVariable("staffId") Long staffId){
        return managerSessionService.request_vesom(staffId);
    }

    @ApiOperation(value = "", notes = "")
    @GetMapping(value = "/request_lamthem/{staffId}/{sogio}")
    public Boolean request_lamthem(@PathVariable("staffId") Long staffId,@PathVariable("staffId") Long sogio){
        return managerSessionService.request_lamthem(staffId,sogio);
    }

    @ApiOperation(value = "", notes = "")
    @PostMapping(value = "/accept_vesom/{staffId}")
    public Boolean accept_vesom(@PathVariable("staffId") Long staffId, @RequestBody TimekeepingDTO timekeepingDTO){
        return managerSessionService.accept_vesom(staffId,timekeepingDTO.getId());
    }

    @ApiOperation(value = "", notes = "")
    @PostMapping(value = "/accept_lamthem/{staffId}")
    public Boolean accept_lamthem(@PathVariable("staffId") Long staffId, @RequestBody TimekeepingDTO timekeepingDTO){
        return managerSessionService.accept_lamthem(staffId,timekeepingDTO.getId());
    }

    @ApiOperation(value = "", notes = "")
    @GetMapping(value = "/getvesom")
    public List<ManagerSessionDto> getvesom(){
        return managerSessionService.getAllVeSom();
    }

    @ApiOperation(value = "", notes = "")
    @GetMapping(value = "/getlamthem")
    public List<ManagerSessionDto> getlamthem(){
        return managerSessionService.getAllLamThem();
    }
}
