package com.example.todoapi.repositories;

import com.example.todoapi.dtos.UserDTO;
import com.example.todoapi.entities.StaffEntity;
import com.example.todoapi.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    @Query("select new com.example.todoapi.dtos.UserDTO(u) from UserEntity u left join RoleEntity r where r.id = ?1")
    List<UserDTO> findUserEntitiesByRoleName(int id);
}
