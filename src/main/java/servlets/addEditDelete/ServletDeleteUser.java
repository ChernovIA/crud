package servlets.addEditDelete;

import dbService.UserService;
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

@WebServlet("/DeleteUser")
public class ServletDeleteUser extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long userId = Long.parseLong(request.getParameter("id"));
        StatusEntity result = userService.deleteUser(userId);
        if (result.getStatusResults() == DBStatusResults.OK) {
            response.sendRedirect(ServletURL.ServletUserControl);
        } else {
            request.setAttribute("result", result);
            request.getRequestDispatcher(ViewURL.ERROR_URL).forward(request, response);
        }
    }
}
