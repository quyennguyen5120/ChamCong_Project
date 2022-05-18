package com.example.todoapi.repositories;

import com.example.todoapi.dtos.SalaryDto;
import com.example.todoapi.dtos.StaffDTO;
import com.example.todoapi.entities.SalaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<SalaryEntity, Long> {
    @Query("select new com.example.todoapi.dtos.SalaryDto(s) from SalaryEntity s")
    List<SalaryDto> getAll();
}
