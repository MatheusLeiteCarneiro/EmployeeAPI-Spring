package com.mlcdev.employeeapi;

import com.mlcdev.employeeapi.dto.EmployeeDTO;
import com.mlcdev.employeeapi.mapper.EmployeeMapper;
import com.mlcdev.employeeapi.model.Employee;
import com.mlcdev.employeeapi.model.Role;
import com.mlcdev.employeeapi.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EmployeeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private EmployeeDTO.EmployeeDTOBuilder getBaseDTOBuilder(){
        return EmployeeDTO.builder().name("Name").salary(new BigDecimal("1.00")).role(Role.INTERN).hiringDate(LocalDate.of(2000, 1, 1));
    }

    private EmployeeDTO addBaseEmployeeToDataBase(){
        Employee entity = EmployeeMapper.toEntity(getBaseDTOBuilder().build());
        Employee updatedEntity = repository.save(entity);
        return EmployeeMapper.toDTO(updatedEntity);
    }

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("TRUNCATE TABLE tb_employee RESTART IDENTITY");
    }

    @Test
    void shouldCreateEmployee() throws Exception{
        EmployeeDTO inputDto = getBaseDTOBuilder().build();
        mockMvc.perform(post("/app/employee").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(inputDto))).andExpect(status().isCreated());
        Assertions.assertEquals(1, repository.count());
    }


}
