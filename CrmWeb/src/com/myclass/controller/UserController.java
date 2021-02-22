package com.myclass.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import com.myclass.dto.RoleDto;
import com.myclass.dto.UserDto;
import com.myclass.entity.Role;
import com.myclass.repository.RoleRepository;
import com.myclass.service.RoleService;
import com.myclass.service.UserService;
import com.mysql.cj.Session;

@WebServlet(urlPatterns = { "/user", "/user/add", "/user/edit","/user/delete" })
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
			HttpSession session = req.getSession();
			UserDto dto = (UserDto) session.getAttribute("USER_LOGIN");
			if(dto.getRoleId()==1) {
				int id = Integer.valueOf(req.getParameter("id"));
				req.setAttribute("user", userService.getById(id));
				req.setAttribute("roles", roleService.getAll());
				req.getRequestDispatcher("/WEB-INF/views/user/edit.jsp").forward(req, resp);
			}
			else {
				resp.sendRedirect(req.getContextPath() +"/error/403");
			}
			break;
			
		case "/user/delete":
			session = req.getSession();
			dto = (UserDto) session.getAttribute("USER_LOGIN");
			if(dto.getRoleId()==1) {
			userService.delete(Integer.parseInt(req.getParameter("id")));
			req.setAttribute("users", userService.getAll());
			req.getRequestDispatcher("/WEB-INF/views/user/index.jsp").forward(req, resp);
			} else {
				resp.sendRedirect(req.getContextPath() +"/error/403");
			}
			
		default:
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		String email = req.getParameter("email");
		String pass = req.getParameter("password");
		String fullname = req.getParameter("fullname");
		int roleId = Integer.valueOf(req.getParameter("roleId"));
		int id = userService.getByEmail(email).getId();
		
		
		// B2. TẠO ĐỐI TƯỢNG DTO
		
		switch(action) {
		case "/user/edit":
			
			UserDto userDto = new UserDto(id,email, pass, fullname, roleId);
					// B3. GỌI HÀM XỬ LÝ LOGIC THÊM MỚI
			String roleDesc = roleService.getById(roleId).getDesc();
			userDto.setRoleDesc(roleDesc);
			if (userService.update(userDto) == -1) {
				req.setAttribute("message", "Sửa đổi thất bại!");
				req.getRequestDispatcher("/WEB-INF/views/user/edit.jsp").forward(req, resp);
			} else {
				resp.sendRedirect(req.getContextPath() + "/user");
			}
				break;
		
		case "/user/add":
			UserDto userAdd = new UserDto(email,pass,fullname,roleId);
			if(userService.insert(userAdd)== -1 ) {
				req.setAttribute("message", "Thêm mới thất bại");
				req.getRequestDispatcher("WEB-INF/views/user/add.jsp").forward(req, resp);
			} else {
				resp.sendRedirect(req.getContextPath()+ "/user");
			}
		}
		
		
	
					
}
}