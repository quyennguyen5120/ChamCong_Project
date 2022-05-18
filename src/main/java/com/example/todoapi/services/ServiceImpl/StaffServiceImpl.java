package com.example.todoapi.services.ServiceImpl;

import com.example.todoapi.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {
    @Autowired
    StaffService staffService;

//    public List<Staff> getAll()
}
