package com.example.todoapi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "timekeeping")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Timekeeping extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "time_start")
    private Date timeStart;

    @Column(name = "end_start")
    private Date endStart;

    @Column(name = "lamthem")
    private Long lamthem;

    @Column(name = "dimuon")
    private Long dimuon;

    @Column(name = "vesom")
    private Long vesom;

    @Column(name = "lamviec")
    private Long lamviec;

    @Column(name = "xin_ve_som")
    private Boolean xinVeSom;

    @Column(name = "xin_lam_them")
    private Boolean xinLamThem;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private StaffEntity staff;

}
