package cycbersoft.java10.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Document;

import cycbersoft.java10.connection.DbConnection;
import cycbersoft.java10.dto.RoleDto;
import cycbersoft.java10.dto.UserDto;
import cycbersoft.java10.entity.Role;
import cycbersoft.java10.entity.User;
import cycbersoft.java10.repository.RoleRepository;
import cycbersoft.java10.repository.UserRepository;
import cycbersoft.java10.service.RoleService;
import cycbersoft.java10.service.UserService;
import cycbersoft.java10.util.Path;
import cycbersoft.java10.util.Url;

@WebServlet(urlPatterns = { Path.USER, Path.USER_ADD, Path.USER_EDIT, Path.USER_DELETE })
public class UserController extends HttpServlet {
	
	
	private RoleService roleService;
	private UserService userService;
	
	public UserController() {
		userService = new UserService();
		roleService = new RoleService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		switch (action) {
		case Path.USER:
			
			List<User> users = UserRepository.findUser();
			req.setAttribute("listUsers", users);
			req.getRequestDispatcher(Url.USER_INDEX).forward(req, resp);
			break;
		case Path.USER_ADD:
			// lay danh sach role
			List<RoleDto> roleDtos= roleService.getAll();
			req.setAttribute("roles", roleDtos);
			// ghi chú khi cập nhật sửa selected ở html
			req.getRequestDispatcher(Url.USER_ADD).forward(req, resp);
			break;
		case Path.USER_EDIT:
			int id = Integer.valueOf(req.getParameter("id"));
			req.setAttribute("user", userService.getById(id));
			req.setAttribute("roles", roleService.getAll());
			req.getRequestDispatcher(Url.USER_EDIT).forward(req, resp);
			break;
		case Path.USER_DELETE:
			int idD = Integer.valueOf(req.getParameter("id"));
			if (userService.Delete(idD)==-1) {
				req.setAttribute("message", "xoa that bai");
				req.getRequestDispatcher(Url.USER_INDEX).forward(req, resp);
			} else {
				// + nếu thành công thì => chuyển hướng(load lại trang danh sách) về trang danh sách 	
				resp.sendRedirect(req.getContextPath() + Path.USER);
			}
			break;
		default:
		//	req.getRequestDispatcher(Url.ERROR_404).forward(req, resp);
			throw new IllegalArgumentException("Unexpected value: " + action);
					}
				}
	
	
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//	req.setCharacterEncoding("UTF-8");
		//	resp.setCharacterEncoding("UTF-8");
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			String fullname = req.getParameter("fullname");
			String avatar = req.getParameter("avatar");
			int role_id = Integer.valueOf(req.getParameter("role_id"));
			
			
			UserDto userdto = new UserDto(email, password, fullname, avatar, role_id);
			
			String action = req.getServletPath();
			switch (action) {
			case Path.USER_ADD:
			//	user.setRole_id(role_id);
				// gọi hàm thực thi truy vấn thêm mới của repository
				// kiểm tra kết quả
				// + nếu thất bài thì => chuyển tiêp về trang thêm mới và xuất thông báo thất bại
				if (UserService.insert(userdto) == -1) {
					req.setAttribute("message", "Them moi that bai");
					req.getRequestDispatcher(Url.USER_ADD).forward(req, resp);
				} else {
					// + nếu thành công thì => chuyển hướng (load lại trang danh sách) về trang danh sách 	
					resp.sendRedirect(req.getContextPath() + Path.USER);
				}
				break;
			case Path.USER_EDIT:
				int id = Integer.valueOf(req.getParameter("id"));
				userdto.setId(id);
				if(userService.update(userdto)==-1) {
					req.getRequestDispatcher(Url.USER_EDIT).forward(req, resp);
				} else {
					// + nếu thành công thì => chuyển hướng (load lại trang danh sách) về trang danh sách 	
					resp.sendRedirect(req.getContextPath() + Path.USER);
				}
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + action);
			}
		}

	
}
