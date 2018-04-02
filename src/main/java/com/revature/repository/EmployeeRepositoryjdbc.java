package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.model.Employee;
import com.revature.model.EmployeeRole;
import com.revature.model.EmployeeToken;
import com.revature.util.ConnectionUtil;

public class EmployeeRepositoryjdbc implements EmployeeRepository {
	private static Logger logger = Logger.getLogger(EmployeeRepositoryjdbc.class);

	private static EmployeeRepository employeeRepository;

	private EmployeeRepositoryjdbc () {}

	public static EmployeeRepository getInstance() {
		if(employeeRepository == null) {
			employeeRepository = new EmployeeRepositoryjdbc();
		}
		return employeeRepository;
	}

	@Override
	public boolean insert(Employee employee) {
		try(Connection connection = ConnectionUtil.getConnection()){
			int parameterIndex = 0;
			String sql = "INSERT INTO USER_T(U_ID, U_FIRSTNAME, U_LASTNAME, U_USERNAME, U_PASSWORD, U_EMAIL, UR_ID) VALUES(?,?,?,?,?,?,?)";
			logger.trace("Getting statement object in insert employee");
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(++parameterIndex, employee.getId());
			statement.setString(++parameterIndex, employee.getFirstName());
			statement.setString(++parameterIndex, employee.getLastName());
			statement.setString(++parameterIndex, employee.getUsername());
			statement.setString(++parameterIndex, employee.getPassword());
			statement.setString(++parameterIndex, employee.getEmail());
			statement.setInt(++parameterIndex, employee.getEmployeeRole().getId());
			logger.trace("parameters for insertion of employee set.");
			if(statement.executeUpdate() != 0) {
				return true;
			}else {
				logger.error("problem");
				return false;
			}
		}catch (SQLException e) {
			logger.error("Exception thrown while inserting new Employee",e);

		}
		return false;
	}

	@Override
	public boolean update(Employee employee) {
		try(Connection connection = ConnectionUtil.getConnection()){
			int parameterIndex = 0;
			String sql = "UPDATE USER_T SET U_FIRSTNAME = ?, U_LASTNAME = ?, U_EMAIL = ? WHERE U_ID = ?";
			logger.trace("Getting statement object in update Employee");
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(++parameterIndex, employee.getFirstName());
			statement.setString(++parameterIndex, employee.getLastName());
			statement.setString(++parameterIndex, employee.getEmail());
			statement.setInt(++parameterIndex, employee.getId());
			logger.trace("parameters for updating an employee.");
			if(statement.executeUpdate() != 0) {
				return true;
			}else {
				logger.error("problem");
				return false;
			}

		}catch (SQLException e) {
			logger.error("Exception thrown while updating Employee",e);

		}
		return false;
	}

	@Override
	public Employee select(int employeeId) {
		try(Connection connection = ConnectionUtil.getConnection()){
			int parameterIndex = 0;
			String sql = "SELECT * FROM USER_T INNER JOIN USER_ROLE ON USER_T.UR_ID = USER_ROLE.UR_ID WHERE U_ID = ?";
			logger.trace("Getting statement object in get user by userID");
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(++parameterIndex, employeeId);
			ResultSet result= statement.executeQuery();
			if(result.next()) {
				return(new Employee(
						result.getInt("U_ID"),
						result.getString("U_FIRSTNAME"),
						result.getString("U_LASTNAME"),
						result.getString("U_USERNAME"),
						result.getString("U_PASSWORD"),
						result.getString("U_EMAIL"),
						new EmployeeRole(
						result.getInt("UR_ID"),
						result.getString("UR_TYPE")
						)));
			}
		}catch (SQLException e) {
			logger.error("Error while selecting user by userID");
		}
		return null;
	}

	@Override
	public Employee select(String username) {
		try(Connection connection = ConnectionUtil.getConnection()){
			int parameterIndex = 0;
			String sql = "SELECT * FROM USER_T INNER JOIN USER_ROLE ON user_t.ur_id = USER_ROLE.UR_ID WHERE U_USERNAME=?";
			logger.trace("Getting statement object in get user by username");
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(++parameterIndex, username);
			ResultSet result= statement.executeQuery();
			if(result.next()) {
				return(new Employee(
						result.getInt("U_ID"),
						result.getString("U_FIRSTNAME"),
						result.getString("U_LASTNAME"),
						result.getString("U_USERNAME"),
						result.getString("U_PASSWORD"),
						result.getString("U_EMAIL"),
						new EmployeeRole(
								result.getInt("UR_ID"),
								result.getString("UR_TYPE")
								))
						);
			}
		}catch (SQLException e) {
			logger.error("Error while selecting specified user",e);
		}
		return null;
	}

	@Override
	public Set<Employee> selectAll() {
		try(Connection connection = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM USER_T INNER JOIN USER_ROLE ON USER_ROLE.UR_ID=USER_T.UR_ID ORDER BY USER_T.U_ID";
			logger.trace("Getting statement object in get all users");
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result= statement.executeQuery();
			Set<Employee> set = new HashSet<>();
			while(result.next()) {
				set.add(new Employee(
						result.getInt("U_ID"),
						result.getString("U_FIRSTNAME"),
						result.getString("U_LASTNAME"),
						result.getString("U_USERNAME"),
						result.getString("U_PASSWORD"),
						result.getString("U_EMAIL"),
						new EmployeeRole(
								result.getInt("UR_ID"),
								result.getString("UR_TYPE")
								)));
			}
			return set;
		}catch (SQLException e) {
			logger.error("Error while selecting all users",e);
		}
		return null;
	}


	@Override
	public String getPasswordHash(Employee employee) {
		try(Connection connection = ConnectionUtil.getConnection()){
			int parameterIndex = 0;
			String sql = "SELECT GET_HASH(?) AS HASH FROM DUAL";
			logger.trace("Getting statement object in get password hash");
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(++parameterIndex, employee.getPassword());
			logger.trace("parameters for insertion of employee set.");
			ResultSet result= statement.executeQuery();
			if(result.next()) {
				return result.getString("HASH");
			}else {
				logger.error("problem");
				return null;
			}

		}catch (SQLException e) {
			logger.error("Exception thrown while getting passhash",e);

		}
		return null;
	}


	@Override
	public boolean insertEmployeeToken(EmployeeToken employeeToken) {
		try(Connection connection = ConnectionUtil.getConnection()){
			int parameterIndex = 0;
			String sql = "INSERT INTO PASSWORD_RECOVERY VALUES(?,'TEST',CURRENT_TIMESTAMP,?)";
			logger.trace("Inserting Employee token");
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(++parameterIndex, employeeToken.getId());
			statement.setInt(++parameterIndex, employeeToken.getRequester().getId());
			logger.trace("parameters for insertion of employee token.");
			if(statement.executeUpdate() != 0) {
				logger.info("token inserted sucessful");
				return true;
			}else {
				logger.error("problem");
				return false;
			}

		}catch (SQLException e) {
			logger.error("Exception thrown while inserting new Token",e);

		}
		return false;
	}

	@Override
	public boolean deleteEmployeeToken(EmployeeToken employeeToken) {
		try(Connection connection = ConnectionUtil.getConnection()){
			int parameterIndex = 0;
			String sql = "DELETE PASSWORD_RECOVERY WHERE PR_ID=?";
			logger.trace("parameters for deleting employee token");
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(++parameterIndex, employeeToken.getId());

			if(statement.executeUpdate() != 0) {
				return true;
			}else {
				logger.error("problem");
				return false;
			}

		}catch (SQLException e) {
			logger.error("Exception thrown while deleting Token",e);

		}
		return false;
	}

	@Override
	public EmployeeToken selectEmployeeToken(EmployeeToken employeeToken) {
		try(Connection connection = ConnectionUtil.getConnection()){
			int parameterIndex = 0;
			String sql = "SELECT * FROM PASSWORD_RECOVERY INNER JOIN USER_T ON PASSWORD_RECOVERY.U_ID = USER_T.U_ID WHERE USER_T.U_ID = ?";
			logger.trace("parameters for selecting employee token");
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(++parameterIndex, employeeToken.getId());
			ResultSet result= statement.executeQuery();
			if(result.next()) {
				return (new EmployeeToken(
						result.getInt("PR_ID"),
						result.getString("PR_TOKEN"),
						result.getTimestamp("PR_TIME").toLocalDateTime(),
						new Employee(
								result.getInt("U_ID"),
								result.getString("U_FIRSTNAME"),
								result.getString("U_LASTNAME"),
								result.getString("U_USERNAME"),
								result.getString("U_PASSWORD"),
								result.getString("U_EMAIL"),
								new EmployeeRole(
										result.getInt("UR_ID"),
										result.getString("UR_TYPE")
										))));
			}else {
				logger.error("problem");
				return null;
			}

		}catch (SQLException e) {
			logger.error("Exception thrown while selecting Token",e);

		}
		return null;
	}

	@Override
	public boolean checkUsername(Employee employee) {
		try(Connection connection = ConnectionUtil.getConnection()){
			int parameterIndex = 0;
			String sql = "SELECT USER_T.U_USERNAME FROM USER_T WHERE U_USERNAME= ?";
			logger.trace("parameters for checking username");
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(++parameterIndex, employee.getUsername());

			if(statement.executeUpdate() != 0) {
				return true;
			}else {
				return false;
			}

		}catch (SQLException e) {

		}
		return false;

	}
	
	@Override
	public boolean updatePassword(Employee employee) {
		try(Connection connection = ConnectionUtil.getConnection()){
			int parameterIndex = 0;
			String sql = "UPDATE USER_T SET U_PASSWORD = ? WHERE U_ID = ?";
			logger.trace("Getting statement object in update Employee");
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(++parameterIndex, employee.getPassword());
			statement.setInt(++parameterIndex, employee.getId());
			logger.trace("parameters for updating an employee.");
			if(statement.executeUpdate() != 0) {
				return true;
			}else {
				logger.error("problem");
				return false;
			}

		}catch (SQLException e) {
			logger.error("Exception thrown while updating Employee",e);

		}
		return false;
	}
	
	public static void main(String[] args) {
		EmployeeRepository repository = new EmployeeRepositoryjdbc();
//		Employee employee = new Employee(0);
//		Employee employee2 = new Employee();
//		EmployeeRole role = new EmployeeRole(1,"employee");
//		employee2.setId(1);
//		employee2.setFirstName("Joel");
//		employee2.setLastName("DeJesus");
//		employee2.setUsername("JoelD95");
//		employee2.setPassword("abc123");
//		employee2.setEmail("joeldejesus95@gmail.com");
//		employee2.setEmployeeRole(role);
		
		logger.trace(repository.selectAll());
	}




}
