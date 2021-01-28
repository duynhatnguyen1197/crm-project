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
import com.myclass.service.RoleService;

@WebServlet(urlPatterns = { "/role", "/role/add", "/role/edit", "/role/delete" })
public class RoleController extends HttpServlet {
	private RoleService roleService;

	public RoleController() {
		roleService = new RoleService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		switch (action) {
		case "/role":
			List<RoleDto> roles = roleService.getAll();
			req.setAttribute("listRole", roles);
			req.getRequestDispatcher("/WEB-INF/views/role/index.jsp").forward(req, resp);
			break;
		case "/role/add":
			req.getRequestDispatcher("/WEB-INF/views/role/add.jsp").forward(req, resp);
			break;
		case "/role/edit":
			// B1: LẤY id TỪ URL
			int id = Integer.valueOf(req.getParameter("id"));

			// B2: GỌI HÀM XỬ LÝ LOGIC CỦA TẦNG SERVICE
			RoleDto dto = roleService.getById(id); // Hàm trả v�? đối tượng Role

			// B3: CHUYỂN TIẾP DỮ LIỆU LẤY �?ƯỢC QUA TRANG edit.jsp
			req.setAttribute("role", dto);
			req.getRequestDispatcher("/WEB-INF/views/role/edit.jsp").forward(req, resp);
			break;
//		case "/role/delete":
//			// B1: LẤY id TỪ URL
//			
//			// B2: GỌI HÀM TRUY VẤN XÓA
//			
//			// B3: KIỂM TRA
//				// THẤT BẠI: CHUYỂN TIẾP VỀ TRANG index.jsp => Xuất thông báo
//				// THÀNH CÔNG: CHUYỂN HƯỚNG VỀ TRANG DANH S�?CH
//			break;
		default:
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		

		String action = req.getServletPath();

		// B1: LẤY THÔNG TIN TỪ FORM
		String name = req.getParameter("name");
		String desc = req.getParameter("desc");
		RoleDto roleDto = new RoleDto(name, desc);
		switch (action) {
		case "/role/add":
			if (roleService.insert(roleDto) == -1) {
				req.setAttribute("message", "Thêm mới thất bại!");
				req.getRequestDispatcher("/WEB-INF/views/role/add.jsp").forward(req, resp);
			} else {
				resp.sendRedirect(req.getContextPath() + "/role");
			}
			break;
		case "/role/edit":
			int id = Integer.valueOf(req.getParameter("id"));
			roleDto.setId(id);
			if (roleService.update(roleDto) == -1) {
				req.setAttribute("message", "Cập nhật thất bại!");
				req.getRequestDispatcher("/WEB-INF/views/role/edit.jsp").forward(req, resp);
			} else {
				resp.sendRedirect(req.getContextPath() + "/role");
			}
			break;
		default:
			break;
		}
	}
}
