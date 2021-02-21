package cycbersoft.java10.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cycbersoft.java10.connection.DbConnection;
import cycbersoft.java10.dto.UserDto;
import cycbersoft.java10.entity.Role;
import cycbersoft.java10.entity.User;

public class UserRepository {
	
	public static  List<User> findUser(){
		List<User> users = new ArrayList<User>();
		Connection conn = DbConnection.getConnection();
		try {
			// truy vấn dữ liệu
			PreparedStatement staement = conn.prepareStatement("select * from users,roles where users.role_id=roles.id");
			// thuc thi cau lenh truy van
			ResultSet resultset = staement.executeQuery();
			// chuyen du lieu qua user entity
			while (resultset.next()) {
				User entity = new User();
				entity.setId(resultset.getInt("id"));
				entity.setEmail(resultset.getString("email"));
				entity.setFullname(resultset.getString("fullname"));
				entity.setPassword(resultset.getString("password"));
				entity.setAvatar(resultset.getString("avatar"));
		//		entity.setRole_id(resultset.getInt("role_id"));
				entity.setDescription(resultset.getString("description"));
				users.add(entity);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

	public static int save(User user) {
		String query= "INSERT INTO users(email,password,fullname,avatar,role_id) VALUE(?,?,?,?,?)";
		Connection conn = DbConnection.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFullname());
			statement.setString(4, user.getAvatar());
			statement.setInt(5, user.getRole_id());
			return statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static User findById(int id) {
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
				entity.setAvatar(resultSet.getString("avatar"));
				entity.setRole_id(resultSet.getInt("role_id"));
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}
	public static User findByEmail(String email) {
		User entity = null;
		Connection conn = DbConnection.getConnection();
		String query = "SELECT * FROM users WHERE email = ?";
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
				entity.setAvatar(resultSet.getString("avatar"));
				entity.setRole_id(resultSet.getInt("role_id"));
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}
	public static User findRoleName(String roleName) {
		User entity = null;
		Connection conn = DbConnection.getConnection();
		String query = "SELETE r.name from users u join roles r WHERE u.role_id=r.id";;
		// TRUY VẤN LẤY DỮ LIỆU
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, roleName);
			// Thực thi câu lệnh truy vấn
			ResultSet resultSet = statement.executeQuery();
			// Chuyển dữ liệu qua User entity
			while (resultSet.next()) {
				entity = new User();
				entity.setId(resultSet.getInt("id"));
				entity.setEmail(resultSet.getString("email"));
				entity.setPassword(resultSet.getString("password"));
				entity.setFullname(resultSet.getString("fullname"));
				entity.setAvatar(resultSet.getString("avatar"));
				entity.setRole_id(resultSet.getInt("role_id"));
			
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}
	
	
	

	public static List<User> findAll() {
		List<User> users = new ArrayList<User>();
		// kết nối Db
		Connection conn = DbConnection.getConnection();
		try {
			// truy vấn dữ liệu
			PreparedStatement staement = conn.prepareStatement("SELECT * FROM users");
			// thuc thi cau lenh truy van
			ResultSet resultset = staement.executeQuery();
			// chuyen du lieu qua role entity
			while (resultset.next()) {
				User entity = new User();
				entity.setId(resultset.getInt("id"));
				entity.setEmail(resultset.getString("email"));
				entity.setPassword(resultset.getString("password"));
				entity.setFullname(resultset.getString("fullname"));
				entity.setAvatar(resultset.getString("avatar"));
				
				entity.setRole_id(resultset.getInt("role_id"));

				users.add(entity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	
	}

	public static int edit(User user) {
		String query= "UPDATE users set email=?,password=?,fullname=?,avatar=?,role_id=? WHERE id=?";
		Connection conn = DbConnection.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(query);
		
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFullname());
			statement.setString(4, user.getAvatar());
			statement.setInt(5, user.getRole_id());
			statement.setInt(6, user.getId());
			return statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public List<UserDto> findAllJoin() {
		String query = "SELETE* from users u join roles r WHERE u.role_id=r.id";
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
				dto.setAvatar(resultSet.getString("avatar"));
				dto.setRole_id(resultSet.getInt("role_id"));
				dto.setDescription(resultSet.getString("description"));
				userDtos.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDtos;
	}

	public static int delete(int idD) {
		// TODO Auto-generated method stub
		String query ="DELETE FROM users WHERE id= ?";
		Connection conn = DbConnection.getConnection();
		try {
			// truy vấn dữ liệu
			PreparedStatement staement = conn.prepareStatement(query);
			staement.setInt(1, idD);	
			return staement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	

	
	
}

