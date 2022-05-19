package com.example.todoapi.repositories;

import com.example.todoapi.dtos.ManagerSessionDto;
import com.example.todoapi.entities.ManagerSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerSessionRepository extends JpaRepository<ManagerSession, Long> {

    @Query("select new com.example.todoapi.dtos.ManagerSessionDto(m) from ManagerSession m where m.staffEntity.id =?1")
    public List<ManagerSessionDto> getByStaffId(Long staffId);
}
