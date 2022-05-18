package com.example.todoapi.services.ServiceImpl;

import com.example.todoapi.dtos.TimekeepingDTO;
import com.example.todoapi.entities.StaffEntity;
import com.example.todoapi.entities.Timekeeping;
import com.example.todoapi.repositories.StaffRepository;
import com.example.todoapi.repositories.TimeKeepingRepository;
import com.example.todoapi.services.TimeKeepingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;

@Service
@Transactional
public class TimeKeepingServiceImpl implements TimeKeepingService {

    @Autowired
    TimeKeepingRepository timeKeepingRepository;
    @Autowired
    StaffRepository staffRepository;

    @Override
    public TimekeepingDTO requestTimeKeeping(Long staffId) {
        StaffEntity staff = staffRepository.getById(staffId);
        Timekeeping timekeeping = new Timekeeping();
        timekeeping.setTimeStart((Timestamp) new Date());
        timekeeping.setStaff(staff);
        timekeeping = timeKeepingRepository.save(timekeeping);
        return new TimekeepingDTO(timekeeping);
    }

    @Override
    public TimekeepingDTO enableTimeKeeping(Long timeKeepingId) {
        return null;
    }
}
