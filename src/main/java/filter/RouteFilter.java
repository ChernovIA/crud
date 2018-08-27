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

@WebFilter(filterName = "RouteFilter", urlPatterns = "/profile")
public class RouteFilter implements Filter {

    public void init(FilterConfig config) {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletResponse respose = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Boolean logged = session.getAttribute("logged") !=null?(Boolean) session.getAttribute("logged"):false;

        if (user != null && logged){

            session.removeAttribute("logged");
            if (user.getRole().equals(Roles.ADMIN)){
                respose.sendRedirect(ServletURL.ServletUserControl);
            }
            else{
                respose.sendRedirect(ServletURL.ServletProfile);
            }
        }
        else {
            chain.doFilter(req, resp);
        }
    }


    public void destroy() {
    }
}
