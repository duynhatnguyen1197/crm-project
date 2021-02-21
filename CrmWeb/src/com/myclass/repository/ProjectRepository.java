package com.myclass.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.myclass.connection.DbConnection;
import com.myclass.entity.Project;
import com.myclass.entity.Role;
import com.mysql.cj.xdevapi.Result;

public class ProjectRepository {
	
	public List<Project> findAll(){
		List<Project> projects = new ArrayList<Project>();
		Connection conn = DbConnection.getConnection();
		
		try {
			PreparedStatement statement  = conn.prepareStatement("SELECT * FROM projects");
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				Project entity = new Project();
				entity.setId(resultSet.getInt("id"));
				entity.setName(resultSet.getString("name"));
				entity.setStartDate(resultSet.getString("start_date"));
				entity.setEndDate(resultSet.getString("end_date"));
				entity.setDescription(resultSet.getString("description"));
				
				projects.add(entity);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return projects;
	}
	
	public int save(Project project) {
		String querry = "INSERT INTO projects(id,name,start_date,end_date,createUser) VALUE(?,?,?,?,?)";
		Connection conn = DbConnection.getConnection();
		
		try {
			PreparedStatement statement = conn.prepareStatement(querry);
			statement.setInt(1, project.getId());
			statement.setString(2, project.getName());
			statement.setString(3, project.getStartDate());
			statement.setString(4, project.getEndDate());
			statement.setInt(5,project.getCreateUser());
			
			return statement.executeUpdate();
		} catch(SQLException e ) {
			e.printStackTrace();
		}
		return -1;
	}
	public int edit(Project project) {
		String query = "UPDATE projects SET name = ?, description = ?, start_date = ? , end_date = ? , createUser = ? WHERE id = ?";
		Connection conn = DbConnection.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, project.getName());
			statement.setString(2, project.getDescription());
			statement.setString(3, project.getStartDate());
			statement.setString(4, project.getEndDate());
			statement.setInt(5, project.getCreateUser());
			
			return statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	} 
	
	public Project findById(int id) {
		String query = "SELECT * FROM roles WHERE id = ?";
		Connection conn = DbConnection.getConnection();
		Project entity = null;
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			// Thực thi câu lệnh truy vấn
			ResultSet resultSet = statement.executeQuery();
			// Chuyển dữ liệu qua Role entity
			while (resultSet.next()) {
				entity = new Project();
				entity.setId(resultSet.getInt("id"));
				entity.setName(resultSet.getString("name"));
				entity.setDescription(resultSet.getString("description"));
				entity.setEndDate(resultSet.getString("end_date"));
				entity.setStartDate(resultSet.getString("start_date"));
				entity.setCreateUser(resultSet.getInt("userCreate"));
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}
	
	public void deleteById (int id ) {
		String query =  "DELETE FROM projects WHERE id = ?";
		String removeId = String.valueOf(id);
		Connection conn = DbConnection.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, removeId);
			statement.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
}
