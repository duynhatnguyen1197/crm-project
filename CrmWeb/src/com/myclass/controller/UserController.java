package com.myclass.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myclass.dto.RoleDto;
import com.myclass.dto.UserDto;
import com.myclass.entity.Role;
import com.myclass.repository.RoleRepository;
import com.myclass.service.RoleService;
import com.myclass.service.UserService;

@WebServlet(urlPatterns = { "/user", "/user/add", "/user/edit" })
public class UserController extends HttpServlet {

	private RoleService roleService;
	private UserService userService;

	public UserController() {
		roleService = new RoleService();
		userService = new UserService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getServletPath();
		switch (action) {
		case "/user":
			req.setAttribute("users", userService.getAll());
			req.getRequestDispatcher("/WEB-INF/views/user/index.jsp").forward(req, resp);
			break;
		case "/user/add":
			req.setAttribute("roles", roleService.getAll());
			req.getRequestDispatcher("/WEB-INF/views/user/add.jsp").forward(req, resp);
			break;
		case "/user/edit":
			int id = Integer.valueOf(req.getParameter("id"));
			req.setAttribute("user", userService.getById(id));
			req.setAttribute("roles", roleService.getAll());
			req.getRequestDispatcher("/WEB-INF/views/user/edit.jsp").forward(req, resp);
			break;
		default:
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// B1. LẤY THÔNG TIN FORM
		String email = req.getParameter("email");
		String pass = req.getParameter("password");
		String fullname = req.getParameter("fullname");
		String avatar = req.getParameter("avatar");
		int roleId = Integer.valueOf(req.getParameter("roleId"));

		// B2. TẠO ĐỐI TƯỢNG DTO
		UserDto userDto = new UserDto(email, pass, fullname, roleId);

		// B3. GỌI HÀM XỬ LÝ LOGIC THÊM MỚI
		if (userService.insert(userDto) == -1) {
			req.setAttribute("message", "Thêm mới thất bại!");
			req.getRequestDispatcher("/WEB-INF/views/user/add.jsp").forward(req, resp);
		} else {
			resp.sendRedirect(req.getContextPath() + "/user");
		}
	}
}
