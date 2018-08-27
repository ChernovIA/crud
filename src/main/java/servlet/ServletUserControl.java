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
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/administration/userControl")
public class ServletUserControl extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(ServletURL.ServletUserControl);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StatusEntity<List<User>> result = userService.getUsersDataTable();

        if (result.getStatusResults() == DBStatusResults.OK) {
            req.setAttribute("users", result.getData());
            req.getRequestDispatcher(ViewURL.LIST_USER_URL).forward(req, resp);
        } else {
            req.setAttribute("result", result);
            req.getRequestDispatcher(ViewURL.ERROR_URL).forward(req, resp);
        }
    }

}
