package filter;

import servlet.property.ServletURL;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AuthFilter", urlPatterns = "/*")
public class AuthFilter implements Filter {

    public void init(FilterConfig config) {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletResponse respose = (HttpServletResponse) resp;
        HttpServletRequest  request = (HttpServletRequest) req;
        HttpSession session = request.getSession();

        if (session.getAttribute("user") == null ^ request.getRequestURI().equals(ServletURL.ServletLogin)){
            respose.sendRedirect(ServletURL.ServletLogin);
        }
        else {
            chain.doFilter(req, resp);
        }
    }

    public void destroy() {
    }

}
