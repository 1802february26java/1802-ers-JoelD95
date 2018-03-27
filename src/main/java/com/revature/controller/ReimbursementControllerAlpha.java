package com.revature.controller;

import javax.servlet.http.HttpServletRequest;

import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.repository.ReimbursementRepositoryjdbc;
import com.revature.service.ReimbursementServiceAlpha;

public class ReimbursementControllerAlpha implements ReimbursementController {
	private static ReimbursementController reimbursementController = new ReimbursementControllerAlpha() ;

	private ReimbursementControllerAlpha () {}

	public static ReimbursementController getInstance() {
		return reimbursementController;
	}

	@Override
	public Object submitRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object singleRequest(HttpServletRequest request) {
		Employee loggedEmployee = (Employee) request.getSession().getAttribute("loggedEmployee");
				if(loggedEmployee == null) {
					return "login.html";
				}
		if (request.getMethod().equals("GET")) {
			return "reimbursements.html";
		} 
		Reimbursement reimbursement= new Reimbursement();
		reimbursement.setId(Integer.parseInt(request.getParameter("reimbursementID")));
		reimbursement = ReimbursementRepositoryjdbc.getInstance().select(reimbursement.getId());
		System.out.println("This is my reimbursement "+reimbursement);
		//Reimbursement reimbursement= ReimbursementRepositoryjdbc.getInstance().select(Integer.parseInt(request.getParameter("reimbursementID")));
		return ReimbursementServiceAlpha.getInstance().getSingleRequest(reimbursement);

	}

	@Override
	public Object multipleRequests(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object finalizeRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getRequestTypes(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
