package com.example.todoapi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "timekeeping")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Timekeeping {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "time_start")
    private Timestamp timeStart;

    @Column(name = "end_start")
    private Timestamp endStart;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private StaffEntity staff;

}
