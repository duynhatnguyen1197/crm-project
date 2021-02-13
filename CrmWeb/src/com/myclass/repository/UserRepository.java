package com.myclass.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.myclass.connection.DbConnection;
import com.myclass.dto.UserDto;
import com.myclass.entity.User;
import com.myclass.entity.User;

public class UserRepository {

	public List<UserDto> findAllJoin() {
		String query = "SELECT * FROM users u JOIN roles r ON u.role_id = r.id";
		List<UserDto> userDtos = new ArrayList<UserDto>();
		Connection conn = DbConnection.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				UserDto dto = new UserDto();
				dto.setId(resultSet.getInt("id"));
				dto.setEmail(resultSet.getString("email"));
				dto.setPassword(resultSet.getString("password"));
				dto.setFullname(resultSet.getString("fullname"));
				dto.setRoleId(resultSet.getInt("role_id"));
				dto.setRoleDesc(resultSet.getString("description"));
				userDtos.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userDtos;
	}

	public List<User> findAll() {
		List<User> users = new ArrayList<User>();
		Connection conn = DbConnection.getConnection();
		// TRUY VẤN LẤY DỮ LIỆU
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM users");
			// Thực thi câu lệnh truy vấn
			ResultSet resultSet = statement.executeQuery();
			// Chuyển dữ liệu qua User entity
			while (resultSet.next()) {
				User entity = new User();
				entity.setId(resultSet.getInt("id"));
				entity.setEmail(resultSet.getString("email"));
				entity.setPassword(resultSet.getString("password"));
				entity.setFullname(resultSet.getString("fullname"));
				entity.setRoleId(resultSet.getInt("role_id"));
				users.add(entity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

	public User findById(int id) {
		User entity = null;
		Connection conn = DbConnection.getConnection();
		// TRUY VẤN LẤY DỮ LIỆU
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
			statement.setInt(1, id);
			// Thực thi câu lệnh truy vấn
			ResultSet resultSet = statement.executeQuery();
			// Chuyển dữ liệu qua User entity
			while (resultSet.next()) {
				entity = new User();
				entity.setId(resultSet.getInt("id"));
				entity.setEmail(resultSet.getString("email"));
				entity.setPassword(resultSet.getString("password"));
				entity.setFullname(resultSet.getString("fullname"));
				entity.setRoleId(resultSet.getInt("role_id"));
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}

	public int save(User entity) {
		String query = "INSERT INTO users(email, password, fullname, role_id) VALUES (?, ?, ?, ?)";
		Connection conn = DbConnection.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, entity.getEmail());
			statement.setString(2, entity.getPassword());
			statement.setString(3, entity.getFullname());
			statement.setInt(4, entity.getRoleId());
			// Thực thi câu lệnh truy vấn
			return statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int edit(User entity) {
		String query = "UPDATE users SET email = ?, password = ?, fullname = ?, role_id = ? WHERE id  = ?";
		Connection conn = DbConnection.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, entity.getEmail());
			statement.setString(2, entity.getPassword());
			statement.setString(3, entity.getFullname());
			statement.setInt(4, entity.getRoleId());
			statement.setInt(5, entity.getId());
			// Thực thi câu lệnh truy vấn
			return statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public User findByEmail(String email) {
		String query = "SELECT * FROM users WHERE email = ?";
		User entity = null;
		Connection conn = DbConnection.getConnection();
		// TRUY VẤN LẤY DỮ LIỆU
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, email);
			// Thực thi câu lệnh truy vấn
			ResultSet resultSet = statement.executeQuery();
			// Chuyển dữ liệu qua User entity
			while (resultSet.next()) {
				entity = new User();
				entity.setId(resultSet.getInt("id"));
				entity.setEmail(resultSet.getString("email"));
				entity.setPassword(resultSet.getString("password"));
				entity.setFullname(resultSet.getString("fullname"));
				entity.setRoleId(resultSet.getInt("role_id"));
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}

	public void deleteById(int id) {
		String query = "DELETE FROM users WHERE id = ?";
		String removeId = String.valueOf(id) ;
		Connection conn = DbConnection.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1,removeId);
			statement.executeUpdate(); 
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	
}
