package it.unitn.disi.webarch.facchinetti.chatapp.servlet;

import it.unitn.disi.webarch.facchinetti.chatapp.bean.UserSessionBean;
import it.unitn.disi.webarch.facchinetti.chatapp.model.Role;
import it.unitn.disi.webarch.facchinetti.chatapp.model.User;
import it.unitn.disi.webarch.facchinetti.chatapp.util.CsvUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LoginServlet extends HttpServlet {

    protected final static String PARAMETER_USERS_FILE = "USER_FILE";
    private final static String PARAMETER_ADMIN_PASSWORD = "ADMIN_PASSWORD";


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        Map<String, User> usersMap = null;
        try {
            usersMap = this.loadUsers();
        } catch (IOException e) {
            e.printStackTrace();
            usersMap = new HashMap<>();
        }
        this.getServletContext().setAttribute("USERS_MAP", usersMap);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        this.getServletContext().log("" + (req.getSession(false) != null));
        req.setAttribute("error", "");
        req.getRequestDispatcher("index.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean authorized = false;
        User user = null;
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        this.getServletContext().log("Authenticating user: " + username);

        if( username != null && password != null ){
            Map<String, User> usersMap = (Map<String, User>) this.getServletContext().getAttribute("USERS_MAP");
            user = usersMap.getOrDefault(username, null);
            authorized = user != null && user.getPassword().trim().equals(password);
        }

        if( authorized ){
            HttpSession httpSession = req.getSession(true);
            httpSession.setAttribute("USER", new UserSessionBean(username, user.getRole().isAdmin()));
            resp.sendRedirect(resp.encodeRedirectURL(this.getServletContext().getContextPath()+"/user/rooms"));
        } else {
            req.setAttribute("error", "Error");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }


    private Map<String, User> loadUsers() throws IOException {

        Map<String, User> users = new ConcurrentHashMap<>();
        InputStream is = new FileInputStream(this.getServletContext().getInitParameter(PARAMETER_USERS_FILE));
        Set<User> fileUsers = CsvUtils.getUsers(is, true);
        String adminPassword = this.getServletConfig().getInitParameter(PARAMETER_ADMIN_PASSWORD).trim();
        fileUsers.add(new User("admin", adminPassword, Role.ADMIN));

        fileUsers.forEach(user -> users.put(user.getUsername(), user));
        return users;

    }
}
