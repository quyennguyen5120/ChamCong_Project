package com.example.todoapi.dtos;

import com.example.todoapi.entities.StaffEntity;
import com.example.todoapi.entities.Timekeeping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimekeepingDTO {
    private Long id;

    private Timestamp timeStart;

    private Timestamp endStart;
    private StaffDTO staffDTO;


    public TimekeepingDTO(Timekeeping timekeeping){
        this.id = timekeeping.getId();
        this.timeStart = timekeeping.getTimeStart();
        this.endStart = timekeeping.getEndStart();
        StaffDTO s = new StaffDTO();
        s.setAddress(timekeeping.getStaff().getAddress());
        s.setAge(timekeeping.getStaff().getAge());
        s.setFullname(timekeeping.getStaff().getFullname());
        s.setEmail(timekeeping.getStaff().getEmail());
        s.setId(timekeeping.getId());
        this.staffDTO = s;
    }
}
