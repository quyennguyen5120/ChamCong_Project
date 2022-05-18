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
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimekeepingDTO {
    private Long id;
    private Date timeStart;
    private Date endStart;
    private Boolean is_Active;
    private StaffDTO staffDTO;
    private String description;
    private Boolean isComplete;
    private List<Integer> days;

    public TimekeepingDTO(Timekeeping timekeeping){
        this.id = timekeeping.getId();
        this.timeStart = timekeeping.getTimeStart();
        this.endStart = timekeeping.getEndStart();
        this.is_Active = timekeeping.getIsActive();
        StaffDTO s = new StaffDTO();
        s.setAddress(timekeeping.getStaff().getAddress());
        s.setAge(timekeeping.getStaff().getAge());
        s.setFullname(timekeeping.getStaff().getFullname());
        s.setEmail(timekeeping.getStaff().getEmail());
        s.setId(timekeeping.getId());
        this.staffDTO = s;
    }
}
