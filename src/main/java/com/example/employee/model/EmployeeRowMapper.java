package com.example.employee.model;

import com.example.employee.model.Employee;
import org.springframework.jdbc.core.RowMapper;
import java.sql.SQLException;
import java.sql.ResultSet;

public class EmployeeRowMapper implements RowMapper<Employee> {
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException{
        return new Employee(rs.getInt("employeeId"), rs.getString("employeeName"), rs.getString("email"), rs.getString("department"));
    }
}