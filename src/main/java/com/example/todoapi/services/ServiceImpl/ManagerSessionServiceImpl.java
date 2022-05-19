package com.example.todoapi.services.ServiceImpl;

import com.example.todoapi.dtos.ManagerSessionDto;
import com.example.todoapi.entities.ManagerSession;
import com.example.todoapi.entities.StaffEntity;
import com.example.todoapi.entities.Timekeeping;
import com.example.todoapi.repositories.ManagerSessionRepository;
import com.example.todoapi.repositories.StaffRepository;
import com.example.todoapi.repositories.TimeKeepingRepository;
import com.example.todoapi.services.ManagerSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class ManagerSessionServiceImpl implements ManagerSessionService {
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    ManagerSessionRepository managerSessionRepositoryl;
    @Autowired
    TimeKeepingRepository timeKeepingRepository;

    @Override
    public Boolean request_vesom(Long staffId) {
        try{
            String bacz= LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            Date dtz = new Date(bacz);
            Date dt = new Date(bacz);
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            c.add(Calendar.DATE, 1);
            dt = c.getTime();

            ManagerSession managerSession = null;
            managerSession = managerSessionRepositoryl.findSessionByDayVeSom(dtz, dt , staffId);
            if(managerSession != null){
                return null;
            }
            else {
                managerSession = new ManagerSession();
            }
            StaffEntity staff = staffRepository.getById(staffId);
            managerSession.setStaffEntity(staff);
            managerSession.setIsVeSom(false);

            Timekeeping timekeepingz = timeKeepingRepository.findTimeKeepingByDay(dtz, dt , staffId);
            managerSession.setTimekeeping(timekeepingz);
            managerSessionRepositoryl.save(managerSession);
            return true;
        }
        catch (Exception e){
            return  false;
        }
    }

    @Override
    public Boolean request_lamthem(Long staffId, Long sogio) {
        try{
            String bacz= LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            Date dtz = new Date(bacz);
            Date dt = new Date(bacz);
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            c.add(Calendar.DATE, 1);
            dt = c.getTime();

            ManagerSession managerSession = null;
            managerSession = managerSessionRepositoryl.findSessionByDayLamThem(dtz, dt , staffId);
            if(managerSession != null){
                return null;
            }
            else {
                managerSession = new ManagerSession();
            }
            StaffEntity staff = staffRepository.getById(staffId);
            managerSession.setStaffEntity(staff);
            managerSession.setIsLamThem(false);
            managerSession.setTimeLamThem(sogio);

            Timekeeping timekeepingz = timeKeepingRepository.findTimeKeepingByDay(dtz, dt , staffId);
            managerSession.setTimekeeping(timekeepingz);

            managerSessionRepositoryl.save(managerSession);
            return true;
        }
        catch (Exception e){
            return  false;
        }
    }

    @Override
    public List<ManagerSessionDto> getAllVeSom() {
        return managerSessionRepositoryl.getAllXinVeSom();
    }

    @Override
    public List<ManagerSessionDto> getAllLamThem() {
        return managerSessionRepositoryl.getAllLamThem();
    }

    @Override
    public Boolean accept_vesom(Long staffId, Long time) {
       try{
           ManagerSession managerSession = managerSessionRepositoryl.acceptVeSom(staffId,time);
           managerSession.setIsVeSom(true);
           managerSession = managerSessionRepositoryl.save(managerSession);
           Timekeeping timekeeping = managerSession.getTimekeeping();
           timekeeping.setXinVeSom(true);
           timeKeepingRepository.save(timekeeping);
           return true;
       }
       catch (Exception e){
           return false;
       }
    }

    @Override
    public Boolean accept_lamthem(Long staffId, Long time) {
        try{
            ManagerSession managerSession = managerSessionRepositoryl.acceptLamThem(staffId,time);
            managerSession.setIsLamThem(true);
            managerSession =managerSessionRepositoryl.save(managerSession);
            Timekeeping timekeeping = managerSession.getTimekeeping();
            timekeeping.setXinLamThem(true);
            timekeeping.setLamthem(time);
            timeKeepingRepository.save(timekeeping);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

}
