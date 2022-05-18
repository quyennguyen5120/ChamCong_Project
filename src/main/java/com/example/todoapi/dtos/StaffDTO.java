package com.example.todoapi.dtos;


import com.example.todoapi.entities.StaffEntity;
import com.example.todoapi.entities.Timekeeping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffDTO {
    private Long id;
    private String fullname;
    private String email;
    private Integer age;
    private String address;
    private UserDTO userDTO;
    private Set<TimekeepingDTO> timekeeping;


    public StaffDTO(StaffEntity staffEntity){
        this.id = staffEntity.getId();
        this.fullname = staffEntity.getFullname();
        this.address = staffEntity.getAddress();
        this.email = staffEntity.getEmail();
        this.age = staffEntity.getAge();
        this.timekeeping = staffEntity.getTimekeeping().stream().map(x -> new TimekeepingDTO(x)).collect(Collectors.toSet());
        UserDTO user= new UserDTO();
        user.setId(staffEntity.getUserEntity().getId());
        user.setUsername(staffEntity.getUserEntity().getUsername());
        user.setEmail(staffEntity.getUserEntity().getEmail());
        user.setPassword(staffEntity.getUserEntity().getPassword());
        this.userDTO = user;
    }

}
