package com.revature.controller;

import javax.servlet.http.HttpServletRequest;

import com.revature.ajax.ClientMessage;
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
			return "update.html";
		} 
		if(request.getParameter("firstName") == "") {
			return new ClientMessage("FAILED");
		}
		if(request.getParameter("lastName") == "" ) {
			return new ClientMessage("FAILED");

		}
		if(request.getParameter("email") == "") {
			return new ClientMessage("FAILED");

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

		return loggedEmployee = EmployeeServiceAlpha.getInstance().getEmployeeInformation(loggedEmployee);
		
	}

	@Override
	public Object viewAllEmployees(HttpServletRequest request) {
		Employee loggedEmployee = (Employee) request.getSession().getAttribute("loggedEmployee");
		
		if(loggedEmployee == null) {
			return "login.html";
		}
		if(loggedEmployee.getEmployeeRole().getId() != 2) {
			System.out.println(loggedEmployee.getEmployeeRole().getId());
			return "oops.html";
		}
		if (request.getParameter("fetch") == null) {
			return "all-employees.html";
		} 
		return EmployeeServiceAlpha.getInstance().getAllEmployeesInformation();
		
	}

	@Override
	public Object usernameExists(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
