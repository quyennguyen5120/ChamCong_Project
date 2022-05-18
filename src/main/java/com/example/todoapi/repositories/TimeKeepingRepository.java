package com.example.todoapi.repositories;

import com.example.todoapi.dtos.TimekeepingDTO;
import com.example.todoapi.entities.Timekeeping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TimeKeepingRepository extends JpaRepository<Timekeeping, Long> {
    @Query("select t from Timekeeping  t where t.timeStart BETWEEN ?1 and ?2 and t.staff.id = ?3")
    public Timekeeping findTimeKeepingByDay(String startDate, String endDate, Long staffId);
}
