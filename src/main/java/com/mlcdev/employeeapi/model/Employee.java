package com.mlcdev.employeeapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "tb_employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "salary", nullable = false)
    private BigDecimal salary;
    @Column(name = "hiring_date", nullable = false)
    private LocalDate hiringDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

}
