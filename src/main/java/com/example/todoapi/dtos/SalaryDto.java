package com.example.todoapi.dtos;

import com.example.todoapi.entities.SalaryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDto {
    private Long id;
    private Double salary;
    public SalaryDto(SalaryEntity  salaryEntity){
        this.id = salaryEntity.getId();
        this.salary = salaryEntity.getSalary();
    }
}


