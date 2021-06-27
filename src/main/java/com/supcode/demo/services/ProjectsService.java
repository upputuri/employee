package com.supcode.demo.services;

import java.util.ArrayList;
import java.util.List;

import com.supcode.demo.bo.Employee;
import com.supcode.demo.services.exceptions.ErrorCodes;
import com.supcode.demo.services.exceptions.InvalidInputException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.micrometer.core.instrument.config.validate.Validated.Invalid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ProjectsService {

    @Autowired
    JdbcTemplate jdbcTemplateObject;

    @Autowired
    ValidationUtil validations;
  
    @GetMapping(value="/projects/{projId}/departments/{deptId}/employees", produces= "application/json")
    public List<Employee> getEmployeesOfProject(@PathVariable String projId, @PathVariable String deptId) throws InvalidInputException{
        log.info("Fetching employees of given project");

        List<Employee> employees = new ArrayList<Employee>(1);
        try{
            if(!validations.projExists(projId)){
                throw new InvalidInputException(ErrorCodes.PROJECT_NOT_FOUND, "Invalid Project Code", null);
            }
            else if (!validations.deptExists(deptId)){
                throw new InvalidInputException(ErrorCodes.DEPARTMENT_NOT_FOUND, "Invalid Dept Code", null);
            }
            else if(!validations.isProjInDept(projId, deptId)){
                throw new InvalidInputException(ErrorCodes.PROJECT_NOT_IN_DEPARTMENT, "Invalid Project", null);
            }

            String fetch_employees_sql = "select distinct e.id, e.first_name from   employee_projects ep inner join employees e on (ep.emp_id = e.id) "+
                                            "inner join departments d on (ep.dept_id = d.id) inner join projects p on (ep.proj_id = p.id) "+
                                        "where ep.proj_id = ? and ep.dept_id = ?";

            employees = jdbcTemplateObject.query(fetch_employees_sql, (rs, rowNum) -> {
                Employee e = new Employee();
                e.setId(rs.getString("id"));
                e.setFName(rs.getString("first_name"));
                return e;
            },  new Object[]{projId, deptId});
        }
        catch(DataAccessException e){
            log.error("An exception occurred while getting employees of given project ", e);
            // throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An internal error occurred!, pls retry after some time or pls call support");
            throw e;
        }
        log.info("Employees fetch complete. Returning result");
        return employees;
    }

    @DeleteMapping(value="/projects/{projId}/departments/{deptId}/employees", produces= "application/json")
    public void removeEmployeeFromProject(@PathVariable String projId,
                                            @PathVariable String deptId, 
                                            @RequestParam String empId) throws InvalidInputException{
        // Remove empId from the project identified by projId:deptId
        if (!validations.empExists(empId)){
            throw new InvalidInputException(ErrorCodes.EMPLOYEE_NOT_FOUND, "Invalid emp Id", null);
        }
        
        if (!validations.isProjInDept(projId, deptId)){
            throw new InvalidInputException(ErrorCodes.PROJECT_NOT_IN_DEPARTMENT, "Invalid Project", null);
        }

        log.info("Removing employee from given project");
        String delete_sql = "delete from employee_projects ep where ep.proj_id = ? and ep.dept_id = ? and ep.emp_id = ?";
        int count = jdbcTemplateObject.update(delete_sql, new Object[]{projId, deptId, empId});
        if (count == 0 ){
            throw new InvalidInputException(ErrorCodes.EMPLOYEE_NOT_IN_PROJECT, "Employee not in project", null);
        }
        log.info("Removed employee from given project");
    }
}
