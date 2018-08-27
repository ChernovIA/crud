package servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/errorPage")
public class ServletErrorPage extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }
}
