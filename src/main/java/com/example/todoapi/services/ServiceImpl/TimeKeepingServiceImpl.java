package com.example.todoapi.services.ServiceImpl;

import com.example.todoapi.dtos.InputDto.TimeKeepingInputDto;
import com.example.todoapi.dtos.TimekeepingDTO;
import com.example.todoapi.entities.StaffEntity;
import com.example.todoapi.entities.Timekeeping;
import com.example.todoapi.repositories.StaffRepository;
import com.example.todoapi.repositories.TimeKeepingRepository;
import com.example.todoapi.services.TimeKeepingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TimeKeepingServiceImpl implements TimeKeepingService {

    @Autowired
    TimeKeepingRepository timeKeepingRepository;
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    EntityManager entityManager;

    @Override
    public TimekeepingDTO requestTimeKeeping(Long staffId) {
        StaffEntity staff = staffRepository.getById(staffId);
        Timekeeping timekeeping = new Timekeeping();
        timekeeping.setTimeStart((Timestamp) new Date());
        timekeeping.setStaff(staff);
        timekeeping.setIsActive(false);
        timekeeping = timeKeepingRepository.save(timekeeping);
        return new TimekeepingDTO(timekeeping);
    }

    @Override
    public TimekeepingDTO enableTimeKeeping(Long timeKeepingId) {
        Timekeeping timekeeping = timeKeepingRepository.getById(timeKeepingId);
        timekeeping.setIsActive(true);
        timekeeping = timeKeepingRepository.save(timekeeping);
        return new TimekeepingDTO(timekeeping);
    }

    @Override
    public List<TimekeepingDTO> findByEnabled(TimeKeepingInputDto timeKeepingInputDto) {
        String sql = "select new com.example.todoapi.dtos.TimekeepingDTO(t) from Timekeeping t where 1=1";
        String whereClause = "";
        if (timeKeepingInputDto.getIs_Active() != null) {
            whereClause += " and (t.isActive = :isActive)";
        }
        if(timeKeepingInputDto.getStartDate() != null){
            whereClause += " and (t.timeStart = :timeStart)";
        }
        if(timeKeepingInputDto.getEndDate() != null){
            whereClause += " and (t.endStart = :endStart)";
        }

        sql += whereClause;

        sql += " order by  sp.code  asc";

        Query q = entityManager.createQuery(sql, TimekeepingDTO.class);

        if (timeKeepingInputDto.getIs_Active() != null) {
            q.setParameter("isActive",timeKeepingInputDto.getIs_Active());
        }
        if(timeKeepingInputDto.getStartDate() != null){
            Date date = new Date(timeKeepingInputDto.getStartDate());
            q.setParameter("timeStart",date);
        }
        if(timeKeepingInputDto.getEndDate() != null){
            Date date = new Date(timeKeepingInputDto.getEndDate());
            q.setParameter("endStart",date);
        }
        return q.getResultList();
    }
}
