package cycbersoft.java10.fillter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import cycbersoft.java10.util.Path;

@WebFilter(urlPatterns = Path.ROOT)
public class Utf8Filter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		// code request
		request.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
		// code reponse
		response.setCharacterEncoding("UTF-8");
	}

}
