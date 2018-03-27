package com.revature.service;

import java.util.Set;

import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementType;
import com.revature.repository.ReimbursementRepositoryjdbc;

public class ReimbursementServiceAlpha implements ReimbursementService {
	private static ReimbursementService reimbursementService = new ReimbursementServiceAlpha();

	private ReimbursementServiceAlpha() {}

	public static ReimbursementService getInstance(){
		return reimbursementService;
	}
	@Override
	public boolean submitRequest(Reimbursement reimbursement) {
		return ReimbursementRepositoryjdbc.getInstance().insert(reimbursement);
	}

	@Override
	public boolean finalizeRequest(Reimbursement reimbursement) {
		return ReimbursementRepositoryjdbc.getInstance().update(reimbursement);
	}

	@Override
	public Reimbursement getSingleRequest(Reimbursement reimbursement) {

		return ReimbursementRepositoryjdbc.getInstance().select(reimbursement.getId());
	}

	@Override
	public Set<Reimbursement> getUserPendingRequests(Employee employee) {
		Set<Reimbursement> reimbursements= ReimbursementRepositoryjdbc.getInstance().selectPending(employee.getId());
		return reimbursements;
	}

	@Override
	public Set<Reimbursement> getUserFinalizedRequests(Employee employee) {
		Set<Reimbursement> reimbursements= ReimbursementRepositoryjdbc.getInstance().selectFinalized(employee.getId());
		return reimbursements;
	}

	@Override
	public Set<Reimbursement> getAllPendingRequests() {
		Set<Reimbursement> reimbursements= ReimbursementRepositoryjdbc.getInstance().selectAllPending();
		return reimbursements;
	}

	@Override
	public Set<Reimbursement> getAllResolvedRequests() {
		Set<Reimbursement> reimbursements= ReimbursementRepositoryjdbc.getInstance().selectAllFinalized();
		return reimbursements;
	}

	@Override
	public Set<ReimbursementType> getReimbursementTypes() {
		Set<ReimbursementType> reimbursements= ReimbursementRepositoryjdbc.getInstance().selectTypes();
		return reimbursements;
	}

}
