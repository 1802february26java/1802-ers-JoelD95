package com.revature.service;

import java.util.Set;

import com.revature.model.Employee;
import com.revature.model.EmployeeToken;
import com.revature.repository.EmployeeRepository;
import com.revature.repository.EmployeeRepositoryjdbc;

public class EmployeeServiceAlpha implements EmployeeService {
	private static EmployeeService employeeService = new EmployeeServiceAlpha();
	
	private EmployeeServiceAlpha() {}
	
	public static EmployeeService getInstance(){
		return employeeService;
	}

	@Override
	public Employee authenticate(Employee employee) {
		
		Employee loggedEmployee = EmployeeRepositoryjdbc.getInstance().select(employee.getUsername());
		if(loggedEmployee.getPassword().equals(EmployeeRepositoryjdbc.getInstance().getPasswordHash(employee))){
			return loggedEmployee;
		}
		return null;
	}

	@Override
	public Employee getEmployeeInformation(Employee employee) {
		Employee loggedEmployee = EmployeeRepositoryjdbc.getInstance().select(employee.getId());

		return loggedEmployee;
	}

	@Override
	public Set<Employee> getAllEmployeesInformation() {
		Set<Employee> employees = EmployeeRepositoryjdbc.getInstance().selectAll();
		return employees;
	}

	@Override
	public boolean createEmployee(Employee employee) {
		return EmployeeRepositoryjdbc.getInstance().insert(employee);
	}

	@Override
	public boolean updateEmployeeInformation(Employee employee) {

		return EmployeeRepositoryjdbc.getInstance().update(employee);
	}

	@Override
	public boolean updatePassword(Employee employee) {
		Employee loggedEmployee = EmployeeRepositoryjdbc.getInstance().select(employee.getId());
		if(loggedEmployee.getPassword().equals(EmployeeRepositoryjdbc.getInstance().getPasswordHash(employee))){
			loggedEmployee.setPassword("passholder");
			return EmployeeRepositoryjdbc.getInstance().updatePassword(loggedEmployee);
		}
		return false;
	}

	@Override
	public boolean isUsernameTaken(Employee employee) {
		return EmployeeRepositoryjdbc.getInstance().checkUsername(employee);
	}

	@Override
	public boolean createPasswordToken(Employee employee) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deletePasswordToken(EmployeeToken employeeToken) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTokenExpired(EmployeeToken employeeToken) {
		// TODO Auto-generated method stub
		return false;
	}

}
