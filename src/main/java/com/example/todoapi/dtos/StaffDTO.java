package com.example.todoapi.dtos;


import com.example.todoapi.entities.StaffEntity;
import com.example.todoapi.entities.Timekeeping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
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
    private Set<TimekeepingDTO> timekeepings;
    private Set<TimekeepingDTO> timekeepingDTOS;
    private TimekeepingDTO timekeepingConvert;


    public StaffDTO(StaffEntity staffEntity){
        if(staffEntity != null){
            this.id = staffEntity.getId();
            if(staffEntity.getFullname() != null)
                this.fullname = staffEntity.getFullname();
            if(staffEntity.getAddress() != null)
                this.address = staffEntity.getAddress();
            if(staffEntity.getEmail() != null)
                this.email = staffEntity.getEmail();
            if(staffEntity.getAge() != null)
                this.age = staffEntity.getAge();

            if (staffEntity.getSalaryEntity() != null){
                SalaryDto salaryDto = new SalaryDto(staffEntity.getSalaryEntity());
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
            if(staffEntity.getTimekeepings() != null){
                Set<TimekeepingDTO> timekeepingDTOS = new HashSet<>();
                for(Timekeeping t : staffEntity.getTimekeepings()){
                    timekeepingDTOS.add(new TimekeepingDTO(t));
                }
                this.timekeepings = timekeepingDTOS;
                this.timekeepingDTOS = timekeepingDTOS;
            }
        }

    }
}
