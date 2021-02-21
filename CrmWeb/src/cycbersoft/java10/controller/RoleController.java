package cycbersoft.java10.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.batch.BatchCompilerRequestor;

import com.mysql.cj.protocol.Resultset;


import cycbersoft.java10.connection.DbConnection;
import cycbersoft.java10.dto.RoleDto;
import cycbersoft.java10.entity.Role;
import cycbersoft.java10.repository.RoleRepository;
import cycbersoft.java10.service.RoleService;
import cycbersoft.java10.util.Path;
import cycbersoft.java10.util.Url;

@WebServlet(urlPatterns = { Path.ROLE, 
							Path.ROLE_ADD,
							Path.ROLE_EDIT,
							Path.ROLE_DELETE })

public class RoleController extends HttpServlet {
	
	private RoleService roleService;
	
	public RoleController() {
		
		roleService = new RoleService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();

		switch (action) {
		case Path.ROLE:
			List<RoleDto> roles = roleService.getAll();
			req.setAttribute("listRole", roles);
			req.getRequestDispatcher(Url.ROLE_INDEX).forward(req, resp);
			break;
		case Path.ROLE_ADD:
			req.getRequestDispatcher(Url.ROLE_ADD).forward(req, resp);
			break;
		case Path.ROLE_EDIT:
			// lay id
			int id = Integer.valueOf(req.getParameter("id"));
			// goij ham truy van du leu
			RoleDto entity = roleService.getById(id);
			// chuyen du lieu
			req.setAttribute("role", entity);
			req.getRequestDispatcher(Url.ROLE_EDIT).forward(req, resp);
			break;
		case Path.ROLE_DELETE:
			// lấy id 
			int idD = Integer.valueOf(req.getParameter("id"));
			if (roleService.drop(idD)==-1) {
				req.setAttribute("message", "xoa that bai");
				req.getRequestDispatcher(Url.ROLE_INDEX).forward(req, resp);
			} else {
				// + nếu thành công thì => chuyển hướng(load lại trang danh sách) về trang danh sách 	
				resp.sendRedirect(req.getContextPath() + Path.ROLE);
			}
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}
	private void getDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(req.getParameter("id"));
		try {
			// Mở kết nối database
			Connection conn = DbConnection.getConnection();
			String query = "DELETE FROM roles WHERE id = ?";
			// tạo try vấn Sql đến database sử dung preparestemend
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(req.getContextPath() + Path.ROLE);
	}
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String name = req.getParameter("name");
		String description = req.getParameter("desc");
		Role role = new Role();
		RoleDto dto = new RoleDto( name, description);	
		String action = req.getServletPath();
		switch (action) {
		case Path.ROLE_ADD:
			
			// gọi hàm thực thi truy vấn thêm mới của repository
			// kiểm tra kết quả
			// + nếu thất bài thì => chuyển tiêp về trang thêm mới và xuất thông báo thất bại
			if (roleService.insert( dto) == -1 ) {
				req.setAttribute("message", "Them moi that bai");
				req.getRequestDispatcher(Url.ROLE_ADD).forward(req, resp);
			} else {
				// + nếu thành công thì => chuyển hướng(load lại trang danh sách) về trang danh sách 	
				resp.sendRedirect(req.getContextPath() + Path.ROLE);
			}
				break;
		case Path.ROLE_EDIT:
			int id = Integer.valueOf(req.getParameter("id"));
			dto.setId(id);
			if (roleService.update(dto) == -1) {
				req.getRequestDispatcher(Url.ROLE_EDIT).forward(req, resp);
			} else {
				// + nếu thành công thì => chuyển hướng(load lại trang danh sách) về trang danh sách 	
				resp.sendRedirect(req.getContextPath() + Path.ROLE);
			}
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}
}
