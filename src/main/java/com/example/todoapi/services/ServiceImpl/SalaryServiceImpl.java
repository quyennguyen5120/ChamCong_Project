package com.example.todoapi.services.ServiceImpl;

import com.example.todoapi.dtos.SalaryDto;
import com.example.todoapi.dtos.StaffDTO;
import com.example.todoapi.dtos.StaffSalaryDTO;
import com.example.todoapi.dtos.TimekeepingDTO;
import com.example.todoapi.entities.SalaryEntity;
import com.example.todoapi.entities.Timekeeping;
import com.example.todoapi.repositories.SalaryRepository;
import com.example.todoapi.repositories.TimeKeepingRepository;
import com.example.todoapi.services.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SalaryServiceImpl implements SalaryService {
    @Autowired
    SalaryRepository salaryRepository;
    @Autowired
    TimeKeepingRepository timeKeepingRepository;

    public List<SalaryDto> getAll(){
        return  salaryRepository.getAll();
    }

    public SalaryDto findById(Long id){
        return new SalaryDto(salaryRepository.findById(id).get());
    }

    public boolean deleteById(Long id){
        try {
            salaryRepository.deleteById(id);
            return true;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public SalaryDto saveOrUpdate(SalaryDto salaryDto){
        SalaryEntity salaryEntity = null;
        if (salaryDto.getId() != null){
            salaryEntity = salaryRepository.findById(salaryDto.getId()).get();
        }else {
            salaryEntity = new SalaryEntity();
        }
        salaryEntity.setSalary(salaryDto.getSalary());
        salaryRepository.save(salaryEntity);
        return new SalaryDto(salaryEntity);
    }

    public StaffSalaryDTO calculateSalary(Long staffId, int month){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        YearMonth yearMonth = YearMonth.of(YearMonth.now().getYear(), month);
        String firstOfMonth = yearMonth.atDay(1).format(dtf);
        String lastOfMonth = yearMonth.atEndOfMonth().format(dtf);
        List<Timekeeping> timekeepingList = timeKeepingRepository.findAllByTimeStartBetween(firstOfMonth, lastOfMonth);
        List<TimekeepingDTO> timekeepingDTOList = new ArrayList<>();
        timekeepingList.forEach(t -> {
            TimekeepingDTO timekeepingDTO = new TimekeepingDTO(t);
            timekeepingDTOList.add(timekeepingDTO);
        });
        double heSoLuong = 0;
        StaffDTO staffDTO = new StaffDTO();
        for (TimekeepingDTO t: timekeepingDTOList){
            LocalDateTime startTime = LocalDateTime.ofInstant(t.getTimeStart().toInstant(), ZoneId.systemDefault());
            LocalDateTime endTime = LocalDateTime.ofInstant(t.getEndStart().toInstant(), ZoneId.systemDefault());
            double daysBetween = Duration.between(startTime, endTime).toMinutes();
            daysBetween = daysBetween / 60 - 1;

            if (daysBetween > 8)
                daysBetween = 8;
            if (t.getDimuon() != null)
                daysBetween += t.getDimuon() / 60;
            if (t.getLamthem() != null)
                daysBetween += t.getLamthem() / 60;
            if (!t.getXinVeSom())
                daysBetween -= t.getVesom() / 60;

            heSoLuong += daysBetween / 8;
            staffDTO = t.getStaffDTO();
        }
        double luong = heSoLuong * staffDTO.getSalaryDto().getSalary();
        return new StaffSalaryDTO(staffDTO, heSoLuong, luong);
    }
}
