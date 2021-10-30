package it.unitn.disi.webarchs.claudiofacchinetti.memory.servlet;

import it.unitn.disi.webarchs.claudiofacchinetti.memory.Constants;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.bean.UserBean;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.model.Game;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.model.Ranking;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AdmitServlet extends HttpServlet {


    @Override
    public void init() throws ServletException {
        super.init();
        this.getServletContext().setAttribute(Constants.USER_LIST, new HashSet<>());
        this.getServletContext().setAttribute(Constants.RANKING, new Ranking());
        this.getServletContext().setAttribute(Constants.GAMES, new ConcurrentHashMap<String, Game>());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("name");
        if( username != null && !username.trim().equals("") ) {

            Set<String> knownUsers = (Set<String>) this.getServletContext().getAttribute(Constants.USER_LIST);
            synchronized (this) {
                knownUsers.add(username);
            }
            HttpSession session = req.getSession(true);
            session.setAttribute(Constants.SESSION_USER_BEAN, new UserBean(username, null));
            resp.sendRedirect(this.getServletContext().getContextPath() + "/user/index.jsp");
        } else {
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }

    }
}
