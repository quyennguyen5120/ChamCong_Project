package com.example.todoapi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "staff")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StaffEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullname;
    private String email;
    private Integer age;
    private String address;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private SalaryEntity salaryEntity;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private UserEntity userEntity;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sub_user")
    private UserEntity subUserEntity;

    @OneToMany(mappedBy = "staff", cascade=CascadeType.REMOVE)
    private Set<Timekeeping> timekeeping;
}
