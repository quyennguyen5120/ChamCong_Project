package com.example.todoapi.services;

import com.example.todoapi.dtos.StaffDTO;
import com.example.todoapi.dtos.StaffSalaryDTO;

import java.util.List;

public interface MailService {
    public boolean sendMail(List<StaffSalaryDTO> staffSalaryDTOS);

    public boolean sendMailByStaff(StaffSalaryDTO staffSalaryDTO);

    public boolean SendMailByEmailAndContent(String email, String content , String title);
}
