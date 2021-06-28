package com.supcode.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtil {
    public static final String VALIDATE_PROJECT_ID_SQL = "select count(*) from projects where id=?";
    public static final String VALIDATE_DEPT_ID_SQL = "select count(*) from departments where id=?";
    public static final String VALIDATE_EMP_ID_SQL = "select count(*) from employees where id=?";
    public static final String VALIDATE_PROJECT_SQL = "select count(*) from employee_projects where proj_id = ? and dept_id = ?";

    @Autowired
    JdbcTemplate jdbcTemplateObject;

    public boolean projExists(String projId) throws DataAccessException{
        int count = jdbcTemplateObject.queryForObject(VALIDATE_PROJECT_ID_SQL,  Integer.TYPE, new Object[]{projId});
        return count > 0 ? true: false;
    }

    public boolean deptExists(String deptId) throws DataAccessException{
        int count = jdbcTemplateObject.queryForObject(VALIDATE_DEPT_ID_SQL, Integer.TYPE, new Object[]{deptId});
        return count > 0 ? true: false;
    }

    public boolean empExists(String empId) throws DataAccessException{
        int count = jdbcTemplateObject.queryForObject(VALIDATE_EMP_ID_SQL, Integer.TYPE, new Object[]{empId});
        return count > 0 ? true: false;
    }

    public boolean isProjInDept(String projId, String deptId) throws DataAccessException{
        int count = jdbcTemplateObject.queryForObject(VALIDATE_PROJECT_SQL, Integer.TYPE, new Object[]{projId, deptId});
        return count > 0 ? true: false;
    }
}
