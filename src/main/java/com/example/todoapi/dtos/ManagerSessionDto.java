package com.example.todoapi.dtos;

import com.example.todoapi.entities.ManagerSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerSessionDto {
    private Long id;
    private Boolean isVeSom;
    private Boolean isLamThem;
    private StaffDTO staffEntity;
    private TimekeepingDTO timekeeping;
    private Long time_lamthem;

    public ManagerSessionDto(ManagerSession managerSession){
        this.id = managerSession.getId();
        this.isVeSom = managerSession.getIsVeSom();
        this.isLamThem = managerSession.getIsLamThem();
        this.staffEntity = new StaffDTO(managerSession.getStaffEntity());
        this.time_lamthem = managerSession.getTimeLamThem();
        this.timekeeping = new TimekeepingDTO(managerSession.getTimekeeping());
    }
}
