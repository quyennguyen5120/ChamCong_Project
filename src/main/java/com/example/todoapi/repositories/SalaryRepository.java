package com.example.todoapi.repositories;

import com.example.todoapi.entities.SalaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryRepository extends JpaRepository<SalaryEntity, Long> {

}
