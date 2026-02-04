package com.mlcdev.employeeapi.dto;

import com.mlcdev.employeeapi.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String name;
    private BigDecimal salary;
    private LocalDate hiringDate;
    private Role role;

}
