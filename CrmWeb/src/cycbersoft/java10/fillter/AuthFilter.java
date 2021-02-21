package cycbersoft.java10.fillter;

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

import cycbersoft.java10.dto.UserDto;
import cycbersoft.java10.util.Path;

@WebFilter(urlPatterns = Path.ROOT)
public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		// check sesion
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String action = req.getServletPath();

		if (action.equals(Path.LOGIN)) {
			chain.doFilter(request, response);
			return;
		}
		// kiểm tra đằng nhập
		HttpSession session = req.getSession();
		if (session.getAttribute("USER_LOGIN") == null) {
			resp.sendRedirect(req.getContextPath() + Path.LOGIN);
			return;
		}
		// Phân quyền người dùng
		// phân quyền dựa trên role_name
		// startswith dùng để so sánh không hoàng toàn
		String roleName = UserDto.getRoleName();
		if (action.startsWith(Path.ROLE) && !roleName.equals("ROLE_ADMIN")) {
			resp.sendRedirect(req.getContextPath() + Path.ERROR_403);
			return;
		}
		if (action.startsWith(Path.USER) && !roleName.equals("ROLE_ADMIN") && !roleName.equals("ROLE_MANAGE")) {
			resp.sendRedirect(req.getContextPath() + Path.ERROR_403);
			return;
	}
		chain.doFilter(request, response);
	}
}
