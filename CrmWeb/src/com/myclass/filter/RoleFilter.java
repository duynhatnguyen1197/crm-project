package com.myclass.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myclass.dto.UserDto;

@WebFilter(urlPatterns = {"/user/add" ,"/role"})
public class RoleFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req 		= (HttpServletRequest) request;
		HttpServletResponse resp	= (HttpServletResponse) response;
		HttpSession session =  req.getSession();
		
		UserDto dto = new UserDto();
		dto = (UserDto) session.getAttribute("USER_LOGIN");
		
		 
		if(dto.getRoleId()==1) {
			chain.doFilter(req, resp);
		}
		else {	
			resp.sendRedirect(req.getContextPath()+"/home");
		}
		
	}
	}

