package com.example.employee.service;

import com.example.employee.model.*;
import com.example.employee.repository.EmployeeRepository;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeH2Service implements EmployeeRepository {

	@Autowired
	private JdbcTemplate db;

	@Override
	public ArrayList<Employee> getEmployees() {
		List<Employee> employeeList = db.query("select * from employeelist", new EmployeeRowMapper());
		return new ArrayList<>(employeeList);
	}

	@Override
	public Employee getEmployeeById(int employeeId) {
		try {
			return db.queryForObject("select * from employeelist where employeeid = ?", new EmployeeRowMapper(),
					employeeId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Employee addEmployee(Employee employee) {
		db.update("insert into employeelist(employeename,email,department) values(?,?,?)", employee.getEmployeeName(),
				employee.getEmail(), employee.getDepartment());
		Employee savedEmployee = db.queryForObject(
				"select * from employeelist where employeename = ? and email = ? and department  = ?",
				new EmployeeRowMapper(), employee.getEmployeeName(), employee.getEmail(), employee.getDepartment());
		return savedEmployee;
	}

	@Override
	public Employee updateEmployee(Employee employee, int employeeId) {
		if (employee.getEmployeeName() != null) {
			db.update("update employeelist set employeename = ? where employeeid = ? ", employee.getEmployeeName(),
					employeeId);
		}
		if (employee.getEmail() != null)
			db.update("update employeelist set email = ? where employeeid = ? ", employee.getEmail(), employeeId);
		if (employee.getDepartment() != null)
			db.update("update employeelist set department = ? where employeeid = ? ", employee.getDepartment(),
					employeeId);
		return getEmployeeById(employeeId);
	}

	@Override
	public void deleteEmployeeId(int employeeId) {
		db.update("delete from employeelist where employeeid = ? ", employeeId);

	}

}