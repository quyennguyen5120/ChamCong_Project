package com.example.todoapi.controllers;

import com.example.todoapi.repositories.ManagerSessionRepository;
import com.example.todoapi.services.ManagerSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/managersession")
public class RestManagerSessionController {
    @Autowired
    ManagerSessionRepository managerSessionRepository;

    @Autowired
    ManagerSessionService managerSessionService;

    @GetMapping(value = "/request_vesom/{staffId}")
    public Boolean request_vesom(@PathVariable("staffId") Long staffId){
        return managerSessionService.request_vesom(staffId);
    }

    @GetMapping(value = "/request_lamthem/{staffId}/{sogio}")
    public Boolean request_lamthem(@PathVariable("staffId") Long staffId,@PathVariable("staffId") Long sogio){
        return managerSessionService.request_lamthem(staffId,sogio);
    }
}
