package com.revature.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import com.revature.ajax.ClientMessage;
import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementStatus;
import com.revature.model.ReimbursementType;
import com.revature.repository.ReimbursementRepositoryjdbc;
import com.revature.service.EmployeeServiceAlpha;
import com.revature.service.ReimbursementServiceAlpha;

public class ReimbursementControllerAlpha implements ReimbursementController {
	private static ReimbursementController reimbursementController = new ReimbursementControllerAlpha() ;

	private ReimbursementControllerAlpha () {}

	public static ReimbursementController getInstance() {
		return reimbursementController;
	}

	@Override
	public Object submitRequest(HttpServletRequest request) {
		Employee loggedEmployee = (Employee) request.getSession().getAttribute("loggedEmployee");
		if(loggedEmployee == null) {
			return "login.html";
		}

		if (request.getMethod().equals("GET")) {
			return "submit-reimbursement.html";
		}
		try {
			Float.parseFloat(request.getParameter("reimbursementAmount"));
		}catch (NumberFormatException e){
			return new ClientMessage("SUBMIT FAILED");
		}
		if(Float.parseFloat(request.getParameter("reimbursementAmount"))>999999.99) {
			return new ClientMessage("SUBMIT FAILED");
		}else if(request.getParameter("reimbursementDescription") == "") {
			return new ClientMessage("SUBMIT FAILED"); 
		}else {


			Reimbursement reimbursement= new Reimbursement();
			ReimbursementType reimbursementType= new ReimbursementType();
			ReimbursementStatus reimbursementStatus = new ReimbursementStatus();

			reimbursementStatus.setId(1);
			reimbursementStatus.setStatus("PENDING");
			reimbursementType.setId(Integer.parseInt(request.getParameter("reimbursementTypeID").substring(0, 1)));
			reimbursementType.setType(request.getParameter("reimbursementTypeID").substring(2));
			///reimbursement.setId(Integer.parseInt(request.getParameter("reimbursementID")));
			reimbursement.setAmount(Float.parseFloat(request.getParameter("reimbursementAmount")));
			reimbursement.setDescription(request.getParameter("reimbursementDescription"));
			reimbursement.setRequester(loggedEmployee);
			reimbursement.setType(reimbursementType);
			reimbursement.setStatus(reimbursementStatus);								
			System.out.println("This is my reimbursement "+reimbursement);
			return ReimbursementServiceAlpha.getInstance().submitRequest(reimbursement);}
	}

	@Override
	public Object singleRequest(HttpServletRequest request) {
		Employee loggedEmployee = (Employee) request.getSession().getAttribute("loggedEmployee");
		if(loggedEmployee == null) {
			return "login.html";
		}
		if(loggedEmployee.getEmployeeRole().getId() == 2) {
			if (request.getParameter("reimbursementID")==null) {
				return "single-employee-info.html";
			}
		}
		if (request.getParameter("reimbursementID")==null) {
			return "reimbursements.html";
		} 

		Reimbursement reimbursement= new Reimbursement();
		reimbursement.setId(Integer.parseInt(request.getParameter("reimbursementID")));
		System.out.println("this is the reimbursement"+reimbursement.getId());
		reimbursement = ReimbursementRepositoryjdbc.getInstance().select(reimbursement.getId());
		System.out.println("This is my reimbursement "+reimbursement);
		System.out.println(ReimbursementServiceAlpha.getInstance().getSingleRequest(reimbursement).getRequester().getUsername());
		System.out.println(loggedEmployee.getId());
		if(ReimbursementServiceAlpha.getInstance().getSingleRequest(reimbursement).getRequester().getId() !=loggedEmployee.getId() ) {
			return new ClientMessage("Access Denied");
		}

		return ReimbursementServiceAlpha.getInstance().getSingleRequest(reimbursement);

	}

	@Override
	public Object multipleRequests(HttpServletRequest request) {
		Employee loggedEmployee = (Employee) request.getSession().getAttribute("loggedEmployee");
		Employee requestedEmployee = new Employee();
		if(loggedEmployee == null) {
			return "login.html";
		}
		if (request.getParameter("fetch")==null && loggedEmployee.getEmployeeRole().getId() != 2) {
			return "reimbursements.html";
		}else if(request.getMethod().equals("POST")){
			System.out.println("POST SUCCESS");
			return ReimbursementControllerAlpha.getInstance().finalizeRequest(request);
		}else if(request.getParameter("fetch")==null) {
			return "all-reimbursements.html";
		}
		if(request.getParameter("fetch").equals("finalized")) {

			return ReimbursementServiceAlpha.getInstance().getUserFinalizedRequests(loggedEmployee);

		}else if (request.getParameter("fetch").equals("pending")){

			return ReimbursementServiceAlpha.getInstance().getUserPendingRequests(loggedEmployee);

		}else if(request.getParameter("fetch").equals("allPending")) {
			if(loggedEmployee.getEmployeeRole().getId() != 2) {
				return "oops.html";
			}else
				return ReimbursementServiceAlpha.getInstance().getAllPendingRequests();

		}else if(request.getParameter("fetch").equals("allFinalized")) {
			if(loggedEmployee.getEmployeeRole().getId() != 2) {
				return "oops.html";
			}else
				return ReimbursementServiceAlpha.getInstance().getAllResolvedRequests();
		}else if(request.getParameter("fetch").equals("allUserPending")) {
			if(loggedEmployee.getEmployeeRole().getId() != 2) {
				return "oops.html";
			}else {
				requestedEmployee.setId(Integer.parseInt(request.getParameter("userID")));
				return ReimbursementServiceAlpha.getInstance().getUserPendingRequests(requestedEmployee);
			}
		}else if(request.getParameter("fetch").equals("allUserFinalized")) {
			if(loggedEmployee.getEmployeeRole().getId() != 2) {
				System.out.println("4");
				return "oops.html";
			}else {
				requestedEmployee.setId(Integer.parseInt(request.getParameter("userID")));
				return ReimbursementServiceAlpha.getInstance().getUserFinalizedRequests(requestedEmployee);
			}
		}else if(loggedEmployee.getEmployeeRole().getId() != 2) {
			return "reimbursements.html";
		}
		else { 
		}
		return "all-reimbursements.html";

	}

	@Override
	public Object finalizeRequest(HttpServletRequest request) {
		Employee loggedEmployee = (Employee) request.getSession().getAttribute("loggedEmployee");
		if(loggedEmployee == null) {
			return "login.html";
		}else if(loggedEmployee.getEmployeeRole().getId() != 2) {
			return "oops.html";
		}
		Reimbursement reimbursement = new Reimbursement();
		ReimbursementStatus reimbursementStatus = new ReimbursementStatus();
		Employee requester= new Employee();
		requester.setId(Integer.parseInt(request.getParameter("employeeID")));
		reimbursementStatus.setId(Integer.parseInt(request.getParameter("reimbursementStatus")));
		reimbursement.setId(Integer.parseInt(request.getParameter("rID")));
		reimbursement.setStatus(reimbursementStatus);
		reimbursement.setApprover(loggedEmployee);
		//reimbursement.setRequester();
		System.out.println(reimbursement);
		return ReimbursementServiceAlpha.getInstance().finalizeRequest(reimbursement);
	}

	@Override
	public Object getRequestTypes(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
