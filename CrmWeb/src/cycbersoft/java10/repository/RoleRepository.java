package cycbersoft.java10.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import cycbersoft.java10.connection.DbConnection;
import cycbersoft.java10.entity.Role;

public class RoleRepository {
	public static List<Role> findAll() {
		List<Role> roles = new ArrayList<Role>();
		// kết nối Db
		Connection conn = DbConnection.getConnection();
		try {
			// truy vấn dữ liệu
			PreparedStatement staement = conn.prepareStatement("SELECT * FROM roles");
			// thuc thi cau lenh truy van
			ResultSet resultset = staement.executeQuery();
			// chuyen du lieu qua role entity
			while (resultset.next()) {
				Role entity = new Role();
				entity.setId(resultset.getInt("id"));
				entity.setName(resultset.getString("name"));
				entity.setDescription(resultset.getString("description"));

				roles.add(entity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return roles;
	}
	
	public Role findById(int id) {
		Role entity = new Role();
		String query ="SELECT * From roles WHERE id= ?";
		Connection conn = DbConnection.getConnection();
		try {
			// truy vấn dữ liệu
			PreparedStatement staement = conn.prepareStatement(query);
			staement.setInt(1, id);
			// thuc thi cau lenh truy van
			ResultSet resultset = staement.executeQuery();
			// chuyen du lieu qua role entity
			while (resultset.next()) {
				
				entity.setId(resultset.getInt("id"));
				entity.setName(resultset.getString("name"));
				entity.setDescription(resultset.getString("description"));

				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}
	public int save(Role role) {
		String query ="INSERT INTO roles(name, description) VALUES(?,?)";
		// kết nối Db
		Connection conn = DbConnection.getConnection();
		try {
			// truy vấn dữ liệu
			PreparedStatement staement = conn.prepareStatement(query);
			staement.setString(1, role.getName());
			staement.setString(2, role.getDescription());
			// thuc thi cau lenh truy van
			return staement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	public int edit(Role role) {
		String query = "UPDATE roles SET name = ?, description = ? WHERE id = ?";
		Connection conn = DbConnection.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, role.getName());
			statement.setString(2, role.getDescription());
			statement.setInt(3, role.getId()); 
			
			// Thực thi câu lệnh truy vấn
			return statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	} 
	

	public int delete (int id) {
		String query ="DELETE FROM roles WHERE id= ?";
		Connection conn = DbConnection.getConnection();
		try {
			// truy vấn dữ liệu
			PreparedStatement staement = conn.prepareStatement(query);
			staement.setInt(1, id);	
			return staement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
}

