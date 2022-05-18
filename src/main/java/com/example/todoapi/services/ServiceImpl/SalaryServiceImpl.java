package com.example.todoapi.services.ServiceImpl;

import com.example.todoapi.dtos.SalaryDto;
import com.example.todoapi.dtos.StaffDTO;
import com.example.todoapi.entities.SalaryEntity;
import com.example.todoapi.entities.StaffEntity;
import com.example.todoapi.entities.UserEntity;
import com.example.todoapi.repositories.SalaryRepository;
import com.example.todoapi.services.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class SalaryServiceImpl implements SalaryService {
    @Autowired
    SalaryRepository salaryRepository;
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
}
