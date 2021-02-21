package com.myclass.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myclass.dto.ProjectDto;
import com.myclass.dto.UserDto;
import com.myclass.entity.Project;
import com.myclass.service.ProjectService;

@WebServlet(urlPatterns = {"/project", "/project/add" , "/project/edit" , "/project/delete"})
public class ProjectController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private ProjectService projectService ;
	
	public ProjectController() {
		projectService = new ProjectService();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		
		switch(action) {
		case "/project":
			req.setAttribute("projects", projectService.getAll());
			req.getRequestDispatcher("/WEB-INF/views/project/index.jsp").forward(req, resp);
			break;
			
		case "/project/add":
			req.getRequestDispatcher("/WEB-INF/views/project/add.jsp").forward(req, resp);
			break;
		
		case "project/edit":
			
		}
		}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		UserDto dto = (UserDto) session.getAttribute("USER_LOGIN");
		String action = req.getServletPath();
		String name = req.getParameter("name");
		String startDate =  req.getParameter("start-date");
		String endDate = req.getParameter("end-date");
		int id = Integer.parseInt(req.getParameter("id"));
		int createUser = dto.getRoleId();
		
		switch(action) {
		case "/project/add":
			ProjectDto projectDto = new ProjectDto(name,startDate,endDate,id,createUser);
			if(projectService.insert(projectDto)==-1) {
				req.setAttribute("message", "Them moi that bai");
				req.getRequestDispatcher("/WEB-INF/views/project/add.jsp").forward(req, resp);
			}
			else {
				resp.sendRedirect(req.getContextPath()+"/project");
			}
			break;
		case "project/edit":
			
		}
	}

}
