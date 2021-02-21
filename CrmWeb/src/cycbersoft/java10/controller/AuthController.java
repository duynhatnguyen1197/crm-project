package cycbersoft.java10.controller;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cycbersoft.java10.connection.DbConnection;
import cycbersoft.java10.dto.UserDto;
import cycbersoft.java10.service.AuthService;
import cycbersoft.java10.service.UserService;
import cycbersoft.java10.util.Path;
import cycbersoft.java10.util.Url;

@WebServlet(urlPatterns = {Path.LOGIN,Path.LOGOUT})
public class AuthController extends HttpServlet{

	private AuthService authService;
	
	public AuthController() {
		authService = new AuthService();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		switch (action) {
		case Path.LOGIN: 
			req.getRequestDispatcher(Url.AUTH_LOGIN).forward(req, resp);
		break;
		case Path.LOGOUT:
			HttpSession session = req.getSession();
			session.removeAttribute("USER_LOGIN");
			resp.sendRedirect(req.getContextPath() + Path.LOGIN);
		break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// b1 lấy thông tin form
		String email = req.getParameter("email");
		String pass = req.getParameter("password");
		
		UserDto userDto = AuthService.login(email, pass);
		if(userDto == null) {
			req.setAttribute("message", "Sai thông tin đăng nhập!");
			req.getRequestDispatcher(Url.AUTH_LOGIN)
			.forward(req, resp);
		}
		else {
			// B4. LƯU THÔNG TIN USER VÀO SESSION
			HttpSession session = req.getSession();
			session.setAttribute("USER_LOGIN", userDto);
			// B5. CHUYỂN HƯỚNG REQUEST VỀ TRANG CHỦ
			resp.sendRedirect(req.getContextPath() + Path.HOME);
		}
	}
	}
	

