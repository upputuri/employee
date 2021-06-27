package com.supcode.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtil {

    @Autowired
    JdbcTemplate jdbcTemplateObject;

    public boolean projExists(String projId) throws DataAccessException{
        int count = jdbcTemplateObject.queryForObject("select count(*) from projects where id=?",  Integer.TYPE, new Object[]{projId});
        return count > 0 ? true: false;
    }

    public boolean deptExists(String deptId) throws DataAccessException{
        int count = jdbcTemplateObject.queryForObject("select count(*) from departments where id=?", Integer.TYPE, new Object[]{deptId});
        return count > 0 ? true: false;
    }

    public boolean empExists(String empId) throws DataAccessException{
        int count = jdbcTemplateObject.queryForObject("select count(*) from employees where id=?", Integer.TYPE, new Object[]{empId});
        return count > 0 ? true: false;
    }

    public boolean isProjInDept(String projId, String deptId) throws DataAccessException{
        int count = jdbcTemplateObject.queryForObject("select count(*) from employee_projects where proj_id = ? and dept_id = ?", Integer.TYPE, new Object[]{projId, deptId});
        return count > 0 ? true: false;
    }
}
