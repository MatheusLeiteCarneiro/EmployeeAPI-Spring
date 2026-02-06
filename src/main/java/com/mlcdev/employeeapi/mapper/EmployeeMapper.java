package com.mlcdev.employeeapi.mapper;

import com.mlcdev.employeeapi.dto.EmployeeDTO;
import com.mlcdev.employeeapi.model.Employee;

public class EmployeeMapper {

    private EmployeeMapper() {
    }

    public static EmployeeDTO toDTO(Employee employee){
        if(employee == null){
            return null;
        }
        return EmployeeDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .salary(employee.getSalary())
                .hiringDate(employee.getHiringDate())
                .role(employee.getRole())
                .build();
    }

    public static Employee toEntity(EmployeeDTO dto){
        if(dto == null){
            return null;
        }
        return Employee.builder()
                .name(dto.getName())
                .salary(dto.getSalary())
                .hiringDate(dto.getHiringDate())
                .role(dto.getRole())
                .build();
    }

    public static Employee updateEntity(EmployeeDTO dto, Employee entity){
        Employee updatedEntity = entity;
        updatedEntity.setName(dto.getName());
        updatedEntity.setSalary(dto.getSalary());
        updatedEntity.setHiringDate(dto.getHiringDate());
        updatedEntity.setRole(dto.getRole());
        return updatedEntity;
    }

    public static void updateEntityFromDTO(EmployeeDTO dto, Employee entity){
        if(dto == null || entity == null){
            return;
        }
        entity.setName(dto.getName());
        entity.setSalary(dto.getSalary());
        entity.setHiringDate(dto.getHiringDate());
        entity.setRole(dto.getRole());
    }

}
