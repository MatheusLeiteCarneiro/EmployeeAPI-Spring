package com.mlcdev.employeeapi.service;

import com.mlcdev.employeeapi.dto.EmployeeDTO;
import com.mlcdev.employeeapi.exception.NotFoundException;
import com.mlcdev.employeeapi.mapper.EmployeeMapper;
import com.mlcdev.employeeapi.model.Employee;
import com.mlcdev.employeeapi.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<EmployeeDTO> findAll(Pageable pageable){
        Page<Employee> result = repository.findAll(pageable);
        log.info("Page found with {} elements out of a total of: {}",result.getNumberOfElements(),result.getTotalElements());
        return result.map(EmployeeMapper::toDTO);
    }

    @Transactional
    public EmployeeDTO add(EmployeeDTO dto){
        log.debug("Request to create new employee {}",dto.getName());
        Employee entity =EmployeeMapper.toEntity(dto);
        entity = repository.save(entity);
        EmployeeDTO savedDto = EmployeeMapper.toDTO(entity);
        log.info("Employee {} saved with ID: {}",savedDto.getName(),savedDto.getId());
       return savedDto;
    }
}
