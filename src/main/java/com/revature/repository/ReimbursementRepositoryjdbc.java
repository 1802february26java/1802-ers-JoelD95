package com.revature.repository;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.model.Employee;
import com.revature.model.EmployeeRole;
import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementStatus;
import com.revature.model.ReimbursementType;
import com.revature.util.ConnectionUtil;

public class ReimbursementRepositoryjdbc implements ReimbursementRepository {
	private static Logger logger = Logger.getLogger(ReimbursementRepositoryjdbc.class);
	private static ReimbursementRepository reimbursementRepository;

	private ReimbursementRepositoryjdbc () {}

	public static ReimbursementRepository getInstance() {
		if(reimbursementRepository == null) {
			reimbursementRepository = new ReimbursementRepositoryjdbc();
		}
		return reimbursementRepository;
	}

	@Override
	public boolean insert(Reimbursement reimbursement) {
		try(Connection connection = ConnectionUtil.getConnection()){
			int parameterIndex = 0;
			String sql = "INSERT INTO REIMBURSEMENT(R_ID, R_REQUESTED, R_AMOUNT, R_DESCRIPTION, EMPLOYEE_ID, MANAGER_ID,RS_ID,RT_ID) VALUES(?,CURRENT_TIMESTAMP,?,?,?,?,?,?)";
			logger.trace("Getting statement object in insert reimbursement");
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(++parameterIndex, reimbursement.getId());
			statement.setDouble(++parameterIndex, reimbursement.getAmount());
			statement.setString(++parameterIndex, reimbursement.getDescription());
			statement.setInt(++parameterIndex, reimbursement.getRequester().getId());
			statement.setInt(++parameterIndex, reimbursement.getApprover().getId());
			statement.setInt(++parameterIndex, reimbursement.getStatus().getId());
			statement.setInt(++parameterIndex, reimbursement.getType().getId());
			logger.trace("parameters for insertion of reimbursement set.");
			if(statement.executeUpdate() != 0) {
				return true;
			}else {
				logger.error("problem");
				return false;
			}
		}catch (SQLException e) {
			logger.error("Exception thrown while inserting new reimbursement",e);

		}
		return false;
	}

	@Override
	public boolean update(Reimbursement reimbursement) {
		try(Connection connection = ConnectionUtil.getConnection()){
			int parameterIndex = 0;
			String sql = "UPDATE REIMBURSEMENT SET RS_ID = ?, R_RESOLVED = CURRENT_TIMESTAMP WHERE R_ID = ?";
			logger.trace("Getting statement object in update reimbursement");
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(++parameterIndex, reimbursement.getStatus().getId());
			statement.setInt(++parameterIndex, reimbursement.getId());
			logger.trace("parameters for upadating reimbursement set.");
			if(statement.executeUpdate() != 0) {
				return true;
			}else {
				logger.error("problem");
				return false;
			}
		}catch (SQLException e) {
			logger.error("Exception thrown while updating reimbursement",e);

		}
		return false;
	}

	@Override
	public Reimbursement select(int reimbursementId) {
		try(Connection connection = ConnectionUtil.getConnection()){
			int parameterIndex = 0;
			String sql = "SELECT * FROM ((((REIMBURSEMENT INNER JOIN USER_T ON REIMBURSEMENT.EMPLOYEE_ID= USER_T.U_ID) INNER JOIN REIMBURSEMENT_TYPE ON REIMBURSEMENT.RT_ID = REIMBURSEMENT_TYPE.RT_ID) INNER JOIN REIMBURSEMENT_STATUS ON REIMBURSEMENT.RS_ID = REIMBURSEMENT_STATUS.RS_ID) INNER JOIN USER_T ON REIMBURSEMENT.MANAGER_ID = USER_T.U_ID) where R_ID=?";
			logger.trace("Getting statement object in select reimbursement");
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(++parameterIndex, reimbursementId);
			logger.trace("parameters for selecting reimbursement set.");
			ResultSet result= statement.executeQuery();
			if(result.next()) {
				return new Reimbursement(
						result.getInt("R_ID"),
						result.getTimestamp("R_REQUESTED").toLocalDateTime(),
						result.getTimestamp("R_RESOLVED").toLocalDateTime(),
						result.getDouble("R_AMOUNT"),
						result.getString("R_DESCRIPTION"),
						new Employee(
								result.getInt("U_ID"),
								result.getString("U_FIRSTNAME"),
								result.getString("U_LASTNAME"),
								result.getString("U_USERNAME"),
								result.getString("U_PASSWORD"),
								result.getString("U_EMAIL"),
								new EmployeeRole(
										result.getInt("UR_ID")
										)),
						EmployeeRepositoryjdbc.getInstance().select(result.getInt("MANAGER_ID")),
						new ReimbursementStatus(result.getString("RS_STATUS")),
						new ReimbursementType(result.getString("RT_TYPE")));
			}else {
				logger.error("problem");
				return null;
			}
		}catch (SQLException e) {
			logger.error("Exception thrown while selecting reimbursement",e);

		}
		return null;
	}

	@Override
	public Set<Reimbursement> selectPending(int employeeId) {
		try(Connection connection = ConnectionUtil.getConnection()){
			int parameterIndex = 0;
			String sql="SELECT *\n" + 
					"FROM ((REIMBURSEMENT \n" + 
					"INNER JOIN REIMBURSEMENT_STATUS ON REIMBURSEMENT.RS_ID = REIMBURSEMENT_STATUS.RS_ID)\n" + 
					"INNER JOIN REIMBURSEMENT_TYPE ON REIMBURSEMENT.RT_ID = REIMBURSEMENT_TYPE.RT_ID)\n" + 
					"INNER JOIN USER_T ON REIMBURSEMENT.EMPLOYEE_ID= USER_T.U_ID\n" + 
					"WHERE EMPLOYEE_ID= ?";
			logger.trace("Getting statement object in select pending from certain user.");
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(++parameterIndex, employeeId);
			Set<Reimbursement> set = new HashSet<>();
			logger.trace("parameters for selecting pending reimbursements from a certain user.");
			ResultSet result= statement.executeQuery();
			while(result.next()) {
				set.add(new Reimbursement(
						result.getInt("R_ID"),
						result.getTimestamp("R_REQUESTED").toLocalDateTime(),
						//result.getTimestamp("R_RESOLVED").toLocalDateTime(),
						result.getDouble("R_AMOUNT"),
						result.getString("R_DESCRIPTION"),
						new Employee(),
						new Employee(result.getInt("MANAGER_ID")),
						new ReimbursementStatus(result.getString("RS_STATUS")),
						new ReimbursementType(result.getString("RT_TYPE"))));
			}
			return set;
		}catch (SQLException e) {
			logger.error("Exception thrown while selecting pending reimbursements from a certain user",e);

		}
		return null;
	}

	@Override
	public Set<Reimbursement> selectFinalized(int employeeId) {
		try(Connection connection = ConnectionUtil.getConnection()){
			int parameterIndex = 0;
			String sql="SELECT *\n" + 
					"FROM ((REIMBURSEMENT \n" + 
					"INNER JOIN REIMBURSEMENT_STATUS ON REIMBURSEMENT.RS_ID = REIMBURSEMENT_STATUS.RS_ID)\n" + 
					"INNER JOIN REIMBURSEMENT_TYPE ON REIMBURSEMENT.RT_ID = REIMBURSEMENT_TYPE.RT_ID)\n" + 
					"INNER JOIN USER_T ON REIMBURSEMENT.EMPLOYEE_ID= USER_T.U_ID\n" + 
					"WHERE EMPLOYEE_ID= ?";
			logger.trace("Getting statement object in select pending from certain user.");
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(++parameterIndex, employeeId);
			Set<Reimbursement> set = new HashSet<>();
			logger.trace("parameters for selecting pending reimbursements from a certain user.");
			ResultSet result= statement.executeQuery();
			while(result.next()) {
				set.add(new Reimbursement(
						result.getInt("R_ID"),
						result.getTimestamp("R_REQUESTED").toLocalDateTime(),
						//result.getTimestamp("R_RESOLVED").toLocalDateTime(),
						result.getDouble("R_AMOUNT"),
						result.getString("R_DESCRIPTION"),
						new Employee("U_USERNAME"),
						new Employee(result.getInt("MANAGER_ID")),
						new ReimbursementStatus(result.getString("RS_STATUS")),
						new ReimbursementType(result.getString("RT_TYPE"))));
			}
			return set;
		}catch (SQLException e) {
			logger.error("Exception thrown while selecting pending reimbursements from a certain user",e);

		}
		return null;
	}

	@Override
	public Set<Reimbursement> selectAllPending() {
		try(Connection connection = ConnectionUtil.getConnection()){
			int parameterIndex = 0;
			String sql="SELECT *\n" + 
					"FROM ((REIMBURSEMENT \n" + 
					"INNER JOIN REIMBURSEMENT_STATUS ON REIMBURSEMENT.RS_ID = REIMBURSEMENT_STATUS.RS_ID)\n" + 
					"INNER JOIN REIMBURSEMENT_TYPE ON REIMBURSEMENT.RT_ID = REIMBURSEMENT_TYPE.RT_ID)\n" + 
					"INNER JOIN USER_T ON REIMBURSEMENT.EMPLOYEE_ID= USER_T.U_ID\n" + 
					"WHERE REIMBURSEMENT.RS_ID = 1";
			logger.trace("Getting statement object in select pending from certain user.");
			PreparedStatement statement = connection.prepareStatement(sql);
			Set<Reimbursement> set = new HashSet<>();
			logger.trace("parameters for selecting pending reimbursements from a certain user.");
			ResultSet result= statement.executeQuery();
			while(result.next()) {
				set.add(new Reimbursement(
						result.getInt("R_ID"),
						result.getTimestamp("R_REQUESTED").toLocalDateTime(),
						//result.getTimestamp("R_RESOLVED").toLocalDateTime(),
						result.getDouble("R_AMOUNT"),
						result.getString("R_DESCRIPTION"),
						new Employee(result.getString("U_USERNAME")),
						new Employee(result.getInt("MANAGER_ID")),
						new ReimbursementStatus(result.getString("RS_STATUS")),
						new ReimbursementType(result.getString("RT_TYPE"))));
				//WHERE REIMBURSEMENT.RS_ID = 2 OR REIMBURSEMENT.RS_ID= 3
			}
			return set;
		}catch (SQLException e) {
			logger.error("Exception thrown while selecting pending reimbursements from a certain user",e);

		}
		return null;
	}

	@Override
	public Set<Reimbursement> selectAllFinalized() {
			try(Connection connection = ConnectionUtil.getConnection()){
				int parameterIndex = 0;
				String sql="SELECT *\n" + 
						"FROM ((REIMBURSEMENT \n" + 
						"INNER JOIN REIMBURSEMENT_STATUS ON REIMBURSEMENT.RS_ID = REIMBURSEMENT_STATUS.RS_ID)\n" + 
						"INNER JOIN REIMBURSEMENT_TYPE ON REIMBURSEMENT.RT_ID = REIMBURSEMENT_TYPE.RT_ID)\n" + 
						"INNER JOIN USER_T ON REIMBURSEMENT.EMPLOYEE_ID= USER_T.U_ID\n" + 
						"WHERE REIMBURSEMENT.RS_ID = 2 OR REIMBURSEMENT.RS_ID= 3";
				logger.trace("Getting statement object in select pending from certain user.");
				PreparedStatement statement = connection.prepareStatement(sql);
				Set<Reimbursement> set = new HashSet<>();
				logger.trace("parameters for selecting pending reimbursements from a certain user.");
				ResultSet result= statement.executeQuery();
				while(result.next()) {
					set.add(new Reimbursement(
							result.getInt("R_ID"),
							result.getTimestamp("R_REQUESTED").toLocalDateTime(),
							result.getTimestamp("R_RESOLVED").toLocalDateTime(),
							result.getDouble("R_AMOUNT"),
							result.getString("R_DESCRIPTION"),
							new Employee(result.getString("U_USERNAME")),
							new Employee(result.getInt("MANAGER_ID")),
							new ReimbursementStatus(result.getString("RS_STATUS")),
							new ReimbursementType(result.getString("RT_TYPE"))));
				}
				return set;
			}catch (SQLException e) {
				logger.error("Exception thrown while selecting pending reimbursements from a certain user",e);
			}		return null;
	}

	@Override
	public Set<ReimbursementType> selectTypes() {
		try(Connection connection = ConnectionUtil.getConnection()){
			int parameterIndex = 0;
			String sql="SELECT *\n" + 
					"FROM ((REIMBURSEMENT \n" + 
					"INNER JOIN REIMBURSEMENT_STATUS ON REIMBURSEMENT.RS_ID = REIMBURSEMENT_STATUS.RS_ID)\n" + 
					"INNER JOIN REIMBURSEMENT_TYPE ON REIMBURSEMENT.RT_ID = REIMBURSEMENT_TYPE.RT_ID)\n" + 
					"INNER JOIN USER_T ON REIMBURSEMENT.EMPLOYEE_ID= USER_T.U_ID\n";
			logger.trace("Getting statement object in select pending from certain user.");
			PreparedStatement statement = connection.prepareStatement(sql);
			Set<ReimbursementType> set = new HashSet<>();
			logger.trace("parameters for selecting pending reimbursements from a certain user.");
			ResultSet result= statement.executeQuery();
			while(result.next()) {
				set.add(
						new ReimbursementType(result.getString("RT_TYPE")));
			}
			return set;
		}catch (SQLException e) {
			logger.error("Exception thrown while selecting pending reimbursements from a certain user",e);
		}
		return null;
	}
	public static void main(String[] args) {
		ReimbursementRepository repository = new ReimbursementRepositoryjdbc();
		Reimbursement reimbursement = new Reimbursement();
		Employee employee2 = new Employee();
		Employee employee = new Employee("DannyS73");
		EmployeeRole role = new EmployeeRole(1,"employee");
		ReimbursementType type = new ReimbursementType(1,"other");
		ReimbursementStatus status = new ReimbursementStatus(1,"pending");
		employee2.setId(1);
		employee2.setFirstName("Joel");
		employee2.setLastName("DeJesus");
		employee2.setUsername("JoelD95");
		employee2.setPassword("abc123");
		employee2.setEmail("joeldejesus95@gmail.com");
		employee2.setEmployeeRole(role);
		reimbursement.setAmount(55);
		reimbursement.setApprover(employee);
		reimbursement.setRequester(employee2);
		reimbursement.setRequested(LocalDateTime.now());
		reimbursement.setResolved(null);
		reimbursement.setId(4);
		reimbursement.setDescription("testing my method");
		reimbursement.setType(type);
		reimbursement.setStatus(status);
		status.setId(3);
		reimbursement.setStatus(status);
		//logger.trace(repository.insert(reimbursement));
		//System.out.println(reimbursement.getId());
		//System.out.println(reimbursement.getStatus().getId());
		//logger.trace(repository.update(reimbursement));
		logger.trace(repository.select(reimbursement.getId()));
		//logger.trace(repository.selectTypes());
	}

}
