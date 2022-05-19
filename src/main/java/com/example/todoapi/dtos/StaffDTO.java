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
    private SalaryDto salaryDto;
    private UserDTO userDTO;
    private UserDTO userParentDTO;
    private SalaryDto salaryDto;
    private Set<TimekeepingDTO> timekeeping;
    private TimekeepingDTO timekeepingConvert;


    public StaffDTO(StaffEntity staffEntity){
        this.id = staffEntity.getId();
        this.fullname = staffEntity.getFullname();
        this.address = staffEntity.getAddress();
        this.email = staffEntity.getEmail();
        this.age = staffEntity.getAge();-

        if (staffEntity.getSalaryEntity() != null){
            SalaryDto salaryDto = new SalaryDto(staffEntity.getSalaryEntity());
        }

        if (staffEntity.getTimekeeping() != null){
            this.timekeeping = staffEntity.getTimekeeping().stream().map(x -> new TimekeepingDTO(x)).collect(Collectors.toSet());
        }

        if (staffEntity.getUserEntity() != null){
            UserDTO user= new UserDTO();
            user.setId(staffEntity.getUserEntity().getId());
            user.setUsername(staffEntity.getUserEntity().getUsername());
            user.setEmail(staffEntity.getUserEntity().getEmail());
            user.setPassword(staffEntity.getUserEntity().getPassword());
            this.userDTO = user;
        }
        if(staffEntity.getSubUserEntity() != null){
            UserDTO userParent= new UserDTO();
            userParent.setId(staffEntity.getUserEntity().getId());
            userParent.setUsername(staffEntity.getUserEntity().getUsername());
            userParent.setEmail(staffEntity.getUserEntity().getEmail());
            userParent.setPassword(staffEntity.getUserEntity().getPassword());
            this.userParentDTO = userParent;
        }
        if(staffEntity.getSalaryEntity() != null){
            SalaryDto salary = new SalaryDto();
            salary.setSalary(staffEntity.getSalaryEntity().getSalary());
            salary.setId(staffEntity.getSalaryEntity().getId());
            this.salaryDto = salary;
        }
    }


}
