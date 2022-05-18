package com.example.todoapi.dtos.InputDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeKeepingInputDto {
    private Long id;
    private String startDate;
    private String endDate;
    private Boolean is_Active;
}
