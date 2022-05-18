package com.example.todoapi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private UserEntity userEntity;

}
