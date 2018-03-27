package com.revature.controller;

import javax.servlet.http.HttpServletRequest;

import com.revature.model.Employee;
import com.revature.repository.EmployeeRepositoryjdbc;
import com.revature.service.EmployeeServiceAlpha;

public class EmployeeInformationControllerAlpha implements EmployeeInformationController {
	private static EmployeeInformationController informationController = new EmployeeInformationControllerAlpha();

	private EmployeeInformationControllerAlpha() {}

	public static EmployeeInformationController getInstance() {
		return informationController;
	}

	@Override
	public Object registerEmployee(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object updateEmployee(HttpServletRequest request) {
		Employee loggedEmployee = (Employee) request.getSession().getAttribute("loggedEmployee");

		if(loggedEmployee == null) {
			System.out.println("no this is the one that is printing.");
			return "login.html";
		}
		if (request.getMethod().equals("GET")) {
			System.out.println("actually this is printing.");
			return "update.html";
		} 
		loggedEmployee.setFirstName(request.getParameter("firstName"));
		loggedEmployee.setLastName(request.getParameter("lastName"));
		loggedEmployee.setEmail(request.getParameter("email"));
		EmployeeServiceAlpha.getInstance().updateEmployeeInformation(loggedEmployee);
			return loggedEmployee;
		
	}

	@Override
	public Object viewEmployeeInformation(HttpServletRequest request) {
		Employee loggedEmployee = (Employee) request.getSession().getAttribute("loggedEmployee");
		if(loggedEmployee == null) {
			return "login.html";
		}
		if (request.getParameter("fetch") == null) {
			return "my-account.html";
		} 
			/* Client is requesting the list of customers */

			//return loggedEmployee= EmployeeRepositoryjdbc.getInstance().select(loggedEmployee.getUsername());
		return loggedEmployee = EmployeeServiceAlpha.getInstance().getEmployeeInformation(loggedEmployee);
		
	}

	@Override
	public Object viewAllEmployees(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object usernameExists(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
