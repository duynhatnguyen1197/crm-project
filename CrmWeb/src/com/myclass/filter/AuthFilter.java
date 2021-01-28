package com.myclass.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = "*")
public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		String action = req.getServletPath();
		if(!action.equals("/login")) {
			// CHECK SESSION
			HttpSession session = req.getSession();
			// KIỂM TRA NẾU CHƯA ĐĂNG NHẬP => QUAY VỀ TRANG ĐĂNG NHẬP KIỂM TRA THÔNG TIN
			if (session.getAttribute("USER_LOGIN") == null) {
				resp.sendRedirect(req.getContextPath() + "/login");
				return;
			}
		}
		chain.doFilter(request, response);

	}
}
