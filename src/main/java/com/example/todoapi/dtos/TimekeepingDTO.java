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
    private Long lamthem;
    private Long dimuon;
    private Long vesom;
    private Long lamviec;
    private Boolean xinVeSom;
    private Boolean xinLamThem;
    private Boolean is_Active;
    private StaffDTO staffDTO;
    private String description;
    private Boolean isComplete;
    private List<Integer> days;
    private StaffSalaryDTO staffSalaryDTO;

    public TimekeepingDTO(Timekeeping timekeeping){
        if(timekeeping != null){
            this.id = timekeeping.getId();
            this.timeStart = timekeeping.getTimeStart();
            this.endStart = timekeeping.getEndStart();
            this.lamthem = timekeeping.getLamthem();
            this.dimuon = timekeeping.getDimuon();
            this.vesom = timekeeping.getVesom();
            this.lamthem = timekeeping.getLamthem();
            this.xinVeSom = timekeeping.getXinVeSom();
            this.is_Active = timekeeping.getIsActive();
            if(timekeeping.getStaff() != null){
                StaffDTO s = new StaffDTO();
                s.setAddress(timekeeping.getStaff().getAddress());
                s.setAge(timekeeping.getStaff().getAge());
                s.setFullname(timekeeping.getStaff().getFullname());
                s.setEmail(timekeeping.getStaff().getEmail());
                s.setId(timekeeping.getId());
                this.staffDTO = s;
            }
        }
    }
}
