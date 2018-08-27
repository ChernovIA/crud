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

@WebServlet("/EditUser")
public class ServletEditUser extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long userID = Long.parseLong(req.getParameter("id"));
        StatusEntity<User> result = userService.getUser(userID);
        if (result.getStatusResults() == DBStatusResults.OK) {
            req.setAttribute("user", result.getData());
            req.getRequestDispatcher(ViewURL.EDIT_USER_PAGE_URL).forward(req,resp);
        }
        else {
            req.setAttribute("result", result);
            req.getRequestDispatcher(ViewURL.ERROR_URL).forward(req, resp);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        long userID = Long.parseLong(request.getParameter("id"));

        if (login == null || login.isEmpty()) {
            response.getWriter().println("login is empty");
        }
        if (password == null || password.isEmpty()) {
            response.getWriter().println("password is empty");
        }
        if (userID == 0) {
            response.getWriter().println("userID is empty");
        }

        User user = new User(userID,login,password,name);
        StatusEntity result = userService.upDateUser(user);

        if (result.getStatusResults() == DBStatusResults.OK) {
            response.sendRedirect(ServletURL.ServletUserControl);
        }
        else {
            request.setAttribute("result", result);
            request.getRequestDispatcher(ViewURL.ERROR_URL).forward(request, response);
        }
    }

}
