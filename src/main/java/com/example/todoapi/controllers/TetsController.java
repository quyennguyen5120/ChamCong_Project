package com.example.todoapi.controllers;

import com.example.todoapi.dtos.StaffDTO;
import com.example.todoapi.dtos.StaffSalaryDTO;
import com.example.todoapi.services.MailService;
import com.example.todoapi.services.SalaryService;
import com.example.todoapi.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class TetsController {
    @Autowired
    StaffService staffService;
    @Autowired
    SalaryService salaryService;
    @Autowired
    MailService mailService;

    @GetMapping(value = "/test")
    public String index(){
        return "index";
    }
    @GetMapping ("/sendmail")
    public String testmail(){
        List<StaffDTO> listStaffDTOs= staffService.getAll();
        List<StaffSalaryDTO> listStaffSalaryDTO = new ArrayList<>();
        for (StaffDTO s : listStaffDTOs){
            listStaffSalaryDTO.add(salaryService.calculateSalary(s.getId(),1, 2022));
        };
        mailService.sendMail(listStaffSalaryDTO);
        return "redirect:/test";
    }
}
