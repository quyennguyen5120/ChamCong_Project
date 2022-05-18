package com.example.todoapi.repositories;

import com.example.todoapi.dtos.StaffDTO;
import com.example.todoapi.entities.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<StaffEntity, Long> {

    @Query("select new com.example.todoapi.dtos.StaffDTO(s) from StaffEntity s ")
    List<StaffDTO> getAll();

    @Query("select s from StaffEntity s where s.userEntity.id = ?1")
    StaffEntity getStaffByUserId(Long id);
}
