package it.unitn.disi.webarch.facchinetti.chatapp.servlet;

import it.unitn.disi.webarch.facchinetti.chatapp.bean.UserInsertionResultBean;
import it.unitn.disi.webarch.facchinetti.chatapp.model.Role;
import it.unitn.disi.webarch.facchinetti.chatapp.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        req.setAttribute("resultPresent", false);
        req.getRequestDispatcher("/user/admin/index.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        boolean success = false;
        String message = null;

        UserInsertionResultBean result = null;
        try {

            if (this.isValid(username) && this.isValid(password)) {
                Map<String, User> userMap = (Map<String, User>) this.getServletContext().getAttribute("USERS_MAP");
                boolean alreadyExists = userMap.getOrDefault(username, null) != null;
                if (!alreadyExists) {
                    User u = new User(username, password, Role.USER);
                    userMap.put(username, new User(username, password, Role.USER));
                    success = true;
                    this.addUserToFile(u);

                } else {
                    message = "User already exists";
                }
            } else {

                message = "You must provide valid username and/or password";

            }

            result = UserInsertionResultBean.success();
            if (!success) {
                result = UserInsertionResultBean.error(message);
            }
        } catch(Exception ex) {
            result = UserInsertionResultBean.error("Failed to insert the user");
            ex.printStackTrace();
        }

        req.setAttribute("resultPresent", true);
        req.setAttribute("result", result);
        req.getRequestDispatcher("/user/admin/index.jsp").forward(req, resp);

    }

    private boolean isValid(String str){

        return str != null && !str.trim().equals("");

    }

    private void addUserToFile(User u) throws IOException {

        FileWriter fw = new FileWriter(this.getServletContext().getInitParameter(LoginServlet.PARAMETER_USERS_FILE), true);
        fw.append(System.lineSeparator()).append(u.getUsername()).append(",").append(u.getPassword());
        fw.close();

    }
}
