package com.example.todoapi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "manager_session")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ManagerSession extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_ve_som")
    private Boolean isVeSom;

    @Column(name = "is_lam_them")
    private Boolean isLamThem;

    @Column(name = "time_lamthem")
    private Long timeLamThem;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private StaffEntity staffEntity;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private Timekeeping timekeeping;

}
