package filter;

import dbService.dataSets.Roles;
import dbService.dataSets.User;
import servlet.property.ServletURL;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "ContextFilter", urlPatterns = "/administration/*")
public class ContextFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse respose = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        if (!user.getRole().equals(Roles.ADMIN)){
            respose.sendRedirect(ServletURL.ServletNoAccess);
        }
        else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) {

    }

}
