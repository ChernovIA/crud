package servlets.addEditDelete;

import dbService.UserService;
import dbService.dataSets.User;
import dbService.errorsEntity.DBStatusResults;
import dbService.errorsEntity.StatusEntity;
import servlets.ServletURL;
import servlets.ViewURL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AddUser")
public class ServletAddUser extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(ViewURL.ADD_USER_PAGE_URL).forward(req,resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String name = request.getParameter("name");

        if (login == null || login.isEmpty()) {
            response.getWriter().println("login is empty");
        }
        if (password == null || password.isEmpty()) {
            response.getWriter().println("password is empty");
        }

        User user = new User(login, password, name);

        StatusEntity<String> result = userService.addUser(user);
        if (result.getStatusResults() == DBStatusResults.OK) {
            response.sendRedirect(ServletURL.ServletUserControl);
        }
        else{
            request.setAttribute("result", result);
            request.getRequestDispatcher(ViewURL.ERROR_URL).forward(request,response);
        }
    }
}
