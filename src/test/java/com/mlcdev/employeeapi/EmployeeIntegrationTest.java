package com.mlcdev.employeeapi;

import com.mlcdev.employeeapi.dto.EmployeeDTO;
import com.mlcdev.employeeapi.mapper.EmployeeMapper;
import com.mlcdev.employeeapi.model.Employee;
import com.mlcdev.employeeapi.model.Role;
import com.mlcdev.employeeapi.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class EmployeeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private EmployeeDTO.EmployeeDTOBuilder getBaseDTOBuilder() {
        return EmployeeDTO.builder().name("Name").salary(new BigDecimal("1.00")).role(Role.INTERN).hiringDate(LocalDate.of(2000, 1, 1));
    }

    private EmployeeDTO addBaseEmployeeToDataBase() {
        Employee entity = EmployeeMapper.toEntity(getBaseDTOBuilder().build());
        Employee updatedEntity = repository.save(entity);
        return EmployeeMapper.toDTO(updatedEntity);
    }

    private void assertEmployeeBody(ResultActions resultActions, String prefix, EmployeeDTO dto) throws Exception {
        resultActions.andExpect(jsonPath(prefix + ".id").value(dto.getId()))
                .andExpect(jsonPath(prefix + ".name").value(dto.getName()))
                .andExpect(jsonPath(prefix + ".hiringDate").value(dto.getHiringDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                .andExpect(jsonPath(prefix + ".role").value(dto.getRole().name()))
                .andExpect(jsonPath(prefix + ".salary").value(dto.getSalary().doubleValue()));
    }

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("TRUNCATE TABLE tb_employee RESTART IDENTITY");
    }

    @Nested
    class HappyPath {

        @Test
        void shouldCreateEmployee() throws Exception {
            EmployeeDTO inputDto = getBaseDTOBuilder().build();
            ResultActions resultActions = mockMvc.perform(post("/app/employee").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(inputDto))).andExpect(status().isCreated());
            inputDto.setId(1L);
            assertEmployeeBody(resultActions, "$", inputDto);
            Assertions.assertEquals(1, repository.count());
        }

        @Test
        void shouldGetEmployeeByID() throws Exception {
            EmployeeDTO dto = addBaseEmployeeToDataBase();
            ResultActions resultActions = mockMvc.perform(get("/app/employee/{id}", dto.getId()).contentType(MediaType.APPLICATION_JSON));
            assertEmployeeBody(resultActions, "$", dto);
        }

        @Test
        void shouldGetAListOfEmployee() throws Exception {
            EmployeeDTO dto0 = addBaseEmployeeToDataBase();
            EmployeeDTO dto1 = addBaseEmployeeToDataBase();

            ResultActions resultActions = mockMvc.perform(get("/app/employee").contentType(MediaType.APPLICATION_JSON));
            resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.content", hasSize(2)));
            assertEmployeeBody(resultActions, "$.content[0]", dto0);
            assertEmployeeBody(resultActions, "$.content[1]", dto1);
        }

        @Test
        void shouldUpdateEmployee() throws Exception {
            EmployeeDTO dto = addBaseEmployeeToDataBase();
            dto.setName("Name2");
            dto.setRole(Role.JUNIOR);
            dto.setSalary(new BigDecimal("1.00"));
            dto.setHiringDate(LocalDate.of(2001, 2, 2));
            ResultActions resultActions = mockMvc.perform(put("/app/employee/{id}", dto.getId())
                    .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)));
            resultActions.andExpect(status().isOk());
            assertEmployeeBody(resultActions, "$", dto);
        }

        @Test
        void shouldDeleteEmployee() throws Exception {
            EmployeeDTO dto = addBaseEmployeeToDataBase();
            mockMvc.perform(delete("/app/employee/{id}", dto.getId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
            Assertions.assertEquals(0, repository.count());
        }

    }

    @Nested
    class Validation{
        @Test
        void shouldReturnBadRequestWhenNameIsNull() throws Exception {
            EmployeeDTO inputDto = getBaseDTOBuilder().name(null).build();
            mockMvc.perform(post("/app/employee").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDto))).andExpect(status().isUnprocessableContent());
            Assertions.assertEquals(0, repository.count());
        }

        @Test
        void shouldReturnBadRequestWhenNameIsBlank() throws Exception {
            EmployeeDTO inputDto = getBaseDTOBuilder().name(" ").build();
            mockMvc.perform(post("/app/employee").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDto))).andExpect(status().isUnprocessableContent());
             Assertions.assertEquals(0, repository.count());
        }

        @Test
        void shouldReturnBadRequestWhenSalaryIsNull() throws Exception {
            EmployeeDTO inputDto = getBaseDTOBuilder().salary(null).build();
            mockMvc.perform(post("/app/employee").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDto))).andExpect(status().isUnprocessableContent());
            Assertions.assertEquals(0, repository.count());
        }

        @Test
        void shouldReturnBadRequestWhenSalaryIsNegative() throws Exception {
            EmployeeDTO inputDto = getBaseDTOBuilder().salary(new BigDecimal("-1.00")).build();
            mockMvc.perform(post("/app/employee").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDto))).andExpect(status().isUnprocessableContent());
            Assertions.assertEquals(0, repository.count());
        }

        @Test
        void shouldReturnBadRequestWhenSalaryIsZero() throws Exception {
            EmployeeDTO inputDto = getBaseDTOBuilder().salary(new BigDecimal("0.00")).build();
            mockMvc.perform(post("/app/employee").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDto))).andExpect(status().isUnprocessableContent());
            Assertions.assertEquals(0, repository.count());
        }

        @Test
        void shouldReturnBadRequestWhenHiringDateIsNull() throws Exception {
            EmployeeDTO inputDto = getBaseDTOBuilder().hiringDate(null).build();
            mockMvc.perform(post("/app/employee").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDto))).andExpect(status().isUnprocessableContent());
            Assertions.assertEquals(0, repository.count());
        }

        @Test
        void shouldReturnBadRequestWhenRoleIsNull() throws Exception {
            EmployeeDTO inputDto = getBaseDTOBuilder().role(null).build();
            mockMvc.perform(post("/app/employee").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(inputDto))).andExpect(status().isUnprocessableContent());
            Assertions.assertEquals(0, repository.count());
        }

        @Test
        void shouldReturnNotFoundWhenIdDontExist() throws Exception{
          mockMvc.perform(get("/app/employee/{id}",999L).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
        }
    }
}