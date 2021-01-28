package com.myclass.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myclass.dto.UserDto;
import com.myclass.service.AuthService;

@WebServlet(urlPatterns = {"/login", "/logout"})
public class AuthController extends HttpServlet{

	private AuthService authService;
	
	public AuthController() {
		authService = new AuthService();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		switch (action) {
		case "/login":
			req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp")
			.forward(req, resp);
			break;
		case "/logout":
			HttpSession session = req.getSession();
			session.removeAttribute("USER_LOGIN");
			resp.sendRedirect(req.getContextPath() + "/login");
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
		
		UserDto userDto = authService.login(email, pass);
		if(userDto == null) {
			req.setAttribute("message", "Sai thông tin đăng nhập!");
			req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp")
			.forward(req, resp);
		}
		else {
			// B4. LƯU THÔNG TIN USER VÀO SESSION
			HttpSession session = req.getSession();
			session.setAttribute("USER_LOGIN", userDto);
			// B5. CHUYỂN HƯỚNG REQUEST VỀ TRANG CHỦ
			resp.sendRedirect(req.getContextPath() + "/home");
		}
	}
}
