package com.example.todoapi.services.ServiceImpl;

import com.example.todoapi.dtos.SalaryDto;
import com.example.todoapi.dtos.StaffDTO;
import com.example.todoapi.dtos.StaffSalaryDTO;
import com.example.todoapi.dtos.TimekeepingDTO;
import com.example.todoapi.entities.SalaryEntity;
import com.example.todoapi.entities.StaffEntity;
import com.example.todoapi.entities.Timekeeping;
import com.example.todoapi.repositories.SalaryRepository;
import com.example.todoapi.repositories.StaffRepository;
import com.example.todoapi.repositories.TimeKeepingRepository;
import com.example.todoapi.services.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SalaryServiceImpl implements SalaryService {
    @Autowired
    SalaryRepository salaryRepository;
    @Autowired
    TimeKeepingRepository timeKeepingRepository;
    @Autowired
    StaffRepository staffRepository;

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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        YearMonth yearMonth = YearMonth.of(YearMonth.now().getYear(), month);
        LocalDate localDate1 = yearMonth.atDay(1);
        LocalDate localDate2 = yearMonth.atEndOfMonth();
        Date firstOfMonth = Date.from(localDate1.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date lastOfMonth = Date.from(localDate2.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Timekeeping> timekeepingList = timeKeepingRepository.findAllByTimeStartBetween(firstOfMonth, lastOfMonth);
        List<TimekeepingDTO> timekeepingDTOList = new ArrayList<>();
        timekeepingList.forEach(t -> {
            TimekeepingDTO timekeepingDTO = new TimekeepingDTO(t);
            timekeepingDTOList.add(timekeepingDTO);
        });
        double heSoLuong = 0;
        StaffEntity staffEntity = staffRepository.getById(staffId);
        for (TimekeepingDTO t: timekeepingDTOList){
            LocalDateTime startTime = LocalDateTime.ofInstant(t.getTimeStart().toInstant(), ZoneId.systemDefault());
            LocalDateTime endTime = LocalDateTime.ofInstant(t.getEndStart().toInstant(), ZoneId.systemDefault());
            double timesBetween = Duration.between(startTime, endTime).toMinutes();
            timesBetween = timesBetween / 60 - 1;

            if (timesBetween > 8)
                timesBetween = 8;
            if (t.getDimuon() != null)
                timesBetween -= t.getDimuon().doubleValue() / 60;
            if (t.getXinLamThem() != null){
                if (t.getXinLamThem())
                    timesBetween += t.getLamthem().doubleValue() / 60;
            }
            if (t.getXinVeSom() != null){
                if (!t.getXinVeSom())
                    timesBetween -= t.getVesom().doubleValue() / 60;
            } else {
                if (t.getVesom() != null)
                    timesBetween -= t.getVesom().doubleValue() / 60;
            }

            heSoLuong += timesBetween / 8;
        }
        double luong = heSoLuong * staffEntity.getSalaryEntity().getSalary();
        StaffDTO staffDTO = new StaffDTO(staffEntity);
        return new StaffSalaryDTO(staffDTO, heSoLuong, luong);
    }
}
