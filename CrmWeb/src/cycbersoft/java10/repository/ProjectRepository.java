package cycbersoft.java10.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cycbersoft.java10.connection.DbConnection;
import cycbersoft.java10.entity.Project;

public class ProjectRepository {

	public List<Project> findAll(){
		List<Project> projects = new ArrayList<Project>();
		Connection conn = DbConnection.getConnection();
		
		try {
			PreparedStatement statement  = conn.prepareStatement("select * from projects,users where users.id=projects.createUser");
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				Project entity = new Project();
				entity.setId(resultSet.getInt("id"));
				entity.setName(resultSet.getString("name"));
				entity.setStartDate(resultSet.getString("startdate"));
				entity.setEndDate(resultSet.getString("enddate"));
				entity.setFullName(resultSet.getString("fullname"));
			//	entity.setCreateUser(resultSet.getInt("createUser"));
			//	entity.setDescription(resultSet.getString("description"));
				
				projects.add(entity);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return projects;
	}
	public int save(Project project) {
		String query= "INSERT INTO projects(name,description,startDate,endDate,createUser) VALUE(?,?,?,?,?)";
		Connection conn = DbConnection.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, project.getName());
			statement.setString(2, project.getDescription() );
			statement.setString(3, project.getStartDate());
			statement.setString(4, project.getEndDate());
			statement.setInt(5,project.getCreateUser());
			return statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}	
	
	public int edit(Project project) {
		String query = "UPDATE projects SET name = ?, description = ?, startDate = ? , endDate = ? , createUser = ? WHERE id = ?";
		Connection conn = DbConnection.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, project.getName());
			statement.setString(2, project.getDescription());
			statement.setString(3, project.getStartDate());
			statement.setString(4, project.getEndDate());
			statement.setInt(5, project.getCreateUser());
			statement.setInt(6, project.getId());
			return statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	} 
	
	public Project findById(int id) {
		String query = "SELECT * FROM projects WHERE id = ?";
		Connection conn = DbConnection.getConnection();
		Project entity = null;
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			// Thực thi câu lệnh truy vấn
			ResultSet resultSet = statement.executeQuery();
			// Chuyển dữ liệu qua project entity
			while (resultSet.next()) {
				entity = new Project();
				entity.setId(resultSet.getInt("id"));
				entity.setName(resultSet.getString("name"));
				entity.setDescription(resultSet.getString("description"));
				entity.setEndDate(resultSet.getString("endDate"));
				entity.setStartDate(resultSet.getString("startDate"));
				entity.setCreateUser(resultSet.getInt("createUser"));
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}
	
	public int deleteById (int id ) {
		String query =  "DELETE FROM projects WHERE id = ?";
		Connection conn = DbConnection.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
		return	statement.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
