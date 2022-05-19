package com.example.todoapi.repositories;

import com.example.todoapi.dtos.ManagerSessionDto;
import com.example.todoapi.entities.ManagerSession;
import com.example.todoapi.entities.Timekeeping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ManagerSessionRepository extends JpaRepository<ManagerSession, Long> {

    @Query("select new com.example.todoapi.dtos.ManagerSessionDto(m) from ManagerSession m where m.staffEntity.id =?1")
    public List<ManagerSessionDto> getByStaffId(Long staffId);

    @Query("select new com.example.todoapi.dtos.ManagerSessionDto(m) from ManagerSession m where m.isLamThem is not null")
    public List<ManagerSessionDto> getAllLamThem();
    @Query("select m from ManagerSession m where m.staffEntity.id = ?1 and m.timekeeping.id= ?2 and m.isVeSom is not null")
    public ManagerSession acceptVeSom(Long staffId, Long timeId);

    @Query("select m from ManagerSession m where m.staffEntity.id = ?1 and m.timekeeping.id= ?2 and m.isLamThem is not null")
    public ManagerSession acceptLamThem(Long staffId, Long timeId);

    @Query("select new com.example.todoapi.dtos.ManagerSessionDto(m) from ManagerSession m where m.isVeSom is not null")
    public List<ManagerSessionDto> getAllXinVeSom();

    @Query("select t from ManagerSession  t where t.CreateDate BETWEEN ?1 and ?2 and t.staffEntity.id = ?3 and t.isLamThem is null")
    public ManagerSession findSessionByDayVeSom(Date startDate, Date endDate, Long staffId);

    @Query("select t from ManagerSession  t where t.CreateDate BETWEEN ?1 and ?2 and t.staffEntity.id = ?3 and t.isVeSom is null")
    public ManagerSession findSessionByDayLamThem(Date startDate, Date endDate, Long staffId);
}
