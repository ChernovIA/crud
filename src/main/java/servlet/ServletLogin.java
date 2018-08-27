package servlet;

import dbService.UserService;
import dbService.dataSets.User;
import dbService.errorsEntity.DBStatusResults;
import dbService.errorsEntity.StatusEntity;
import servlet.property.ServletURL;
import servlet.property.ViewURL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/login", loadOnStartup = 1)
public class ServletLogin extends HttpServlet {

    private static final int SESSION_LENGTH = 300; //5 min
    private static final String INCORRECT_LOGIN = "Login or password incorrect! Try again!"; //5 min

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login    = request.getParameter("login");
        String password = request.getParameter("password");

        if (login == null || login.isEmpty()) {
            response.getWriter().println("login is empty");
        }
        if (password == null || password.isEmpty()) {
            response.getWriter().println("password is empty");
        }

        StatusEntity<User> result = userService.getUser(login);
        User user = result.getData();

        if (result.getStatusResults() == DBStatusResults.OK && user.getPassword().equals(password)) {

            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(SESSION_LENGTH);
            session.setAttribute("logged", true);
            response.sendRedirect(ServletURL.ServletProfile);
        }
        else{
            request.setAttribute("message", INCORRECT_LOGIN);
            request.setAttribute("loginText", login);
            request.setAttribute("passText", "");
            request.setAttribute("result", result);
            request.getRequestDispatcher(ViewURL.LOGIN_URL).forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("loginText", "");
        request.setAttribute("passText", "");
        request.getRequestDispatcher(ViewURL.LOGIN_URL).forward(request,response);
    }
}
