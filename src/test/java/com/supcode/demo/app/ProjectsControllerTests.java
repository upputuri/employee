package com.supcode.demo.app;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.LinkedList;
import java.util.List;

import com.supcode.demo.bo.Employee;
import com.supcode.demo.services.ValidationUtil;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc

public class ProjectsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JdbcTemplate jdbcTemplateObject;

    @Autowired
    private ValidationUtil validations;

    @Test
    public void healthCheckURLShouldWork() throws Exception {
        this.mockMvc.perform(get("/health")).andExpect(status().isOk()).andExpect(content().string(containsString("good")));
    }

    @Test
    public void shouldReturnEmployeesOfProject() throws Exception {
        Employee e1 = new Employee();
        e1.setFName("Anand");
        e1.setId("E10001");
        Employee e2 = new Employee();
        e2.setFName("Bhargav");
        e2.setId("E10002");
        List<Employee> employees = new LinkedList<Employee>();
        employees.add(e1);
        employees.add(e2);
        when(jdbcTemplateObject.query(Mockito.anyString(), Mockito.any(RowMapper.class), Mockito.any(Object[].class))).thenReturn(employees);
        when(jdbcTemplateObject.queryForObject(Mockito.anyString(), Mockito.any(java.lang.Class.class), Mockito.any())).thenReturn(1);
        String expectedJson = "[{'id':'E10001','fname':'Anand'},{'id':'E10002','fname':'Bhargav'}]";
        this.mockMvc.perform(get("/projects/P1001/departments/D101/employees")).andExpect(status().isOk());
    }
    
    @Test
    public void shouldFailWithInvalidProject() throws Exception {
        // when(jdbcTemplateObject.query(Mockito.anyString(), Mockito.any(RowMapper.class), Mockito.any(Object[].class))).thenReturn(employees);
        when(jdbcTemplateObject.queryForObject(Mockito.contains(validations.VALIDATE_PROJECT_SQL), Mockito.any(java.lang.Class.class), Mockito.any())).thenReturn(0);
        when(jdbcTemplateObject.queryForObject(Mockito.contains(validations.VALIDATE_DEPT_ID_SQL), Mockito.any(java.lang.Class.class), Mockito.any())).thenReturn(1);
        when(jdbcTemplateObject.queryForObject(Mockito.contains(validations.VALIDATE_PROJECT_ID_SQL), Mockito.any(java.lang.Class.class), Mockito.any())).thenReturn(1);
        when(jdbcTemplateObject.queryForObject(Mockito.contains(validations.VALIDATE_EMP_ID_SQL), Mockito.any(java.lang.Class.class), Mockito.any())).thenReturn(1);
        this.mockMvc.perform(get("/projects/P1001/departments/D108/employees")).andExpect(status().isBadRequest());
    }
}
