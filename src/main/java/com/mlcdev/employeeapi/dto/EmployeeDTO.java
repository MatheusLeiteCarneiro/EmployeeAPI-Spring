package com.mlcdev.employeeapi.dto;

import com.mlcdev.employeeapi.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
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

    @NotNull(message = "The name can't be null")
    @NotBlank(message = "Name can't be blank")
    private String name;

    @NotNull(message = "The salary can't be null")
    @Positive(message = "The salary must be positive")
    private BigDecimal salary;

    @NotNull(message = "The hiring date can't be null")
    private LocalDate hiringDate;

    @NotNull(message = "You must specify the employee role")
    private Role role;

}
