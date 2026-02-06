package com.mlcdev.employeeapi.controller;

import com.mlcdev.employeeapi.dto.EmployeeDTO;
import com.mlcdev.employeeapi.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping(value = "/app/employee")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EmployeeDTO> findById(@PathVariable Long id){
        log.debug("Request received to find employee with ID: {}", id);
        EmployeeDTO savedDto = service.findById(id);
        return ResponseEntity.ok(savedDto);
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeDTO>> findAll(@PageableDefault(page = 0, size = 10) Pageable pageable){
        log.debug("Request received to find all, with a size of {}, on page {}",pageable.getPageSize(),pageable.getPageNumber());
        Page<EmployeeDTO> page = service.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> create(@Valid @RequestBody EmployeeDTO dto) {
        log.debug("Request received create employee");
        EmployeeDTO savedDto = service.add(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedDto.getId()).toUri();
        return ResponseEntity.created(uri).body(savedDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EmployeeDTO> update(@PathVariable Long id,@Valid @RequestBody EmployeeDTO dto){
        log.debug("Request received to update the employee with ID: {}",id);
        dto.setId(id);
        EmployeeDTO updatedDTO = service.update(dto);
        return ResponseEntity.ok(updatedDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.debug("Request received to delete the employee with ID: {}",id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
