package com.example.todoapi.services.ServiceImpl;


import com.example.todoapi.dtos.StaffDTO;
import com.example.todoapi.dtos.StaffSalaryDTO;
import com.example.todoapi.entities.StaffEntity;
import com.example.todoapi.repositories.StaffRepository;
import com.example.todoapi.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${mail.smtp.username}")
    String mailFrom;

    @Override
    public boolean sendMail(List<StaffSalaryDTO> staffSalaryDTOS) {
        try{
            for (StaffSalaryDTO s : staffSalaryDTOS){
                final Context ctx = new Context(LocaleContextHolder.getLocale());
                ctx.setVariable("StaffSalaryDTO", s);
                final MimeMessage message = this.javaMailSender.createMimeMessage();
                final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, "UTF-8");
//                mimeMessageHelper.setTo(s.getStaffDTO().getEmail());
                mimeMessageHelper.setTo("quyenproxxxxx@gmail.com");
                mimeMessageHelper.setFrom(mailFrom);
                mimeMessageHelper.setSubject("Lương Thưởng ");
                String templateHtml = templateEngine.process("index", ctx);
                mimeMessageHelper.setText(templateHtml, true);
                this.javaMailSender.send(message);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        return false;
    }
}
