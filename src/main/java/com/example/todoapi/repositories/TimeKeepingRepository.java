package com.example.todoapi.repositories;

import com.example.todoapi.entities.Timekeeping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeKeepingRepository extends JpaRepository<Timekeeping, Long> {
}
