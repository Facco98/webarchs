package it.unitn.disi.webarch.facchinetti.chat.ChatApp.servlet;

import it.unitn.disi.webarch.facchinetti.chat.ChatApp.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean authorized = false;
        User user = null;
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        this.getServletContext().log("Authenticating user: " + username);

        if( username != null && password != null ){
            Map<String, User> userMap = (Map<String, User>) this.getServletContext().getAttribute("USERS_MAP");
            user = userMap.get(username);
            authorized = user != null && user.getPassword().trim().equals(password);
        }

        if( authorized ){
            HttpSession httpSession = req.getSession(true);
            httpSession.setAttribute("USER", username);
            resp.sendRedirect(resp.encodeRedirectURL(this.getServletContext().getContextPath()+"/user/rooms"));
        } else {
            req.setAttribute("error", true);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}
