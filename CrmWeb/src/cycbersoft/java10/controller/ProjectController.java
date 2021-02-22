package cycbersoft.java10.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cycbersoft.java10.dto.ProjectDto;
import cycbersoft.java10.dto.UserDto;

import cycbersoft.java10.service.ProjectService;
import cycbersoft.java10.service.UserService;
import cycbersoft.java10.util.Path;
import cycbersoft.java10.util.Url;

@WebServlet(urlPatterns = {Path.PROJECT, Path.PROJECT_ADD , Path.PROJECT_EDIT , Path.PROJECT_DELETE})
public class ProjectController extends HttpServlet{

	private ProjectService projectService ;
	private UserService userService;
	
	public ProjectController() {
		projectService = new ProjectService();
		userService = new UserService();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		switch(action) {
		case "/project":
			
			req.setAttribute("projects", projectService.getAll());
			req.getRequestDispatcher(Url.PROJECT_INDEX).forward(req, resp);
			break;
			
		case Path.PROJECT_ADD:
			List<UserDto> userDtos = userService.getAll();
			req.setAttribute("users", userDtos);
			req.getRequestDispatcher(Url.PROJECT_ADD).forward(req, resp);
			break;
		
		case Path.PROJECT_EDIT:
			int id = Integer.valueOf(req.getParameter("id"));
			req.setAttribute("project", projectService.getById(id));
			req.setAttribute("users", userService.getAll());
			req.getRequestDispatcher(Url.PROJECT_EDIT).forward(req, resp);
			break;
		case Path.PROJECT_DELETE:
			int idD = Integer.valueOf(req.getParameter("id"));
			if(projectService.delete(idD)==-1) {
				req.setAttribute("message", "xóa that bai");
				req.getRequestDispatcher(Url.PROJECT_INDEX).forward(req, resp);
			}
			else {
				resp.sendRedirect(req.getContextPath()+Path.PROJECT);
			}
			break;
			
			default:
			break;
				
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
	//	UserDto dto = (UserDto) session.getAttribute("USER_LOGIN");
		String action = req.getServletPath();
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		String startDate =  req.getParameter("startdate");
		String endDate = req.getParameter("enddate");
		int createUser = Integer.valueOf(req.getParameter("createUser"));
		ProjectDto projectDto = new ProjectDto(name,description,startDate,endDate,createUser);
		switch(action) {
		case Path.PROJECT_ADD:
	//		System.out.println(name+description+startDate+endDate+createUser);
			if(projectService.insert(projectDto)==-1) {
				req.setAttribute("message", "Them moi that bai");
				req.getRequestDispatcher(Url.PROJECT_ADD).forward(req, resp);
			}
			else {
				resp.sendRedirect(req.getContextPath()+Path.PROJECT);
			}
			break;
		case Path.PROJECT_EDIT:
			int id = Integer.valueOf(req.getParameter("id"));
			projectDto.setId(id);
			if(projectService.update(projectDto)==-1) {
			
				req.getRequestDispatcher(Url.PROJECT_EDIT).forward(req, resp);
			}
			else {
				resp.sendRedirect(req.getContextPath()+Path.PROJECT);
			}
			break;
		}
	}
}
