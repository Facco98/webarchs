package it.unitn.disi.webarchs.claudiofacchinetti.memory.servlet;

import it.unitn.disi.webarchs.claudiofacchinetti.memory.Constants;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.bean.UserBean;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.model.Game;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class UserHomeServlet extends HttpServlet {

    boolean useRandomField;

    @Override
    public void init() throws ServletException {
        super.init();
        this.useRandomField = this.getServletContext().getInitParameter(Constants.INIT_MODE).equalsIgnoreCase("production");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/user/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        UserBean userBean = (UserBean) session.getAttribute(Constants.SESSION_USER_BEAN);
        if( userBean.getGameId() == null ){

            Game game = new Game(this.useRandomField);
            Map<String, Game> games = (Map<String, Game>) this.getServletContext().getAttribute(Constants.GAMES);
            games.put(game.getId(), game);
            userBean.setGameId(game.getId());

        }
        req.getRequestDispatcher("/user/game.html").include(req, resp);

    }
}
