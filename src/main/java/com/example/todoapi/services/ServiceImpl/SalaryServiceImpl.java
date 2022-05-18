package com.example.todoapi.services.ServiceImpl;

import com.example.todoapi.repositories.SalaryRepository;
import com.example.todoapi.services.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    SalaryRepository salaryRepository;


}
