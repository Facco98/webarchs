package it.unitn.disi.webarchs.claudiofacchinetti.memory.servlet.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.Constants;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.bean.ClickRequestBean;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.bean.GameBean;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.bean.UserBean;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.builder.GameBeanBuilder;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.model.Game;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.model.Ranking;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.model.RankingEntry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class GameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Game> games = (Map<String, Game>) this.getServletContext().getAttribute(Constants.GAMES);
        HttpSession session = req.getSession(false);
        UserBean userBean = (UserBean) session.getAttribute(Constants.SESSION_USER_BEAN);
        Game game = null;
        if( userBean.getGameId() == null || (game = games.get(userBean.getGameId())) == null ){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {

            game.getGameState().checkLast();
            GameBean gameBean = new GameBeanBuilder().setGame(game).build();

            resp.setStatus(200);
            resp.setContentType("application/json");
            PrintWriter pw = resp.getWriter();
            pw.write(gameBean.toJSON());

        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            ObjectMapper om = new ObjectMapper();
            ClickRequestBean requestBean = om.readValue(req.getReader(), ClickRequestBean.class);
            HttpSession session = req.getSession(false);
            UserBean ub = (UserBean) session.getAttribute(Constants.SESSION_USER_BEAN);
            Map<String, Game> games = (Map<String, Game>) this.getServletContext().getAttribute(Constants.GAMES);
            Game g = games.getOrDefault(ub.getGameId(), null);
            g.getGameState().checkLast();

            if( g.getGameState().clicked(requestBean.getRowIndex(), requestBean.getColIndex()) ) {
                Boolean failed = g.getGameState().getToFlipNext() != null;
                GameBean gameBean = new GameBeanBuilder().setGame(g).setFailed(failed).build();

                resp.setStatus(200);
                resp.setContentType("application/json");
                PrintWriter pw = resp.getWriter();
                pw.write(gameBean.toJSON());

                if (g.getGameState().getFinished()) {
                    this.updateRanking(g, ub.getUsername());
                    ub.setGameId(null);
                }
            } else {

                resp.sendError(HttpServletResponse.SC_NOT_FOUND);

            }

        } catch ( NumberFormatException | NullPointerException ex ){
            this.log(Arrays.toString(ex.getStackTrace()));
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception ex){

            this.log(ex.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        }


    }

    private void updateRanking(Game g, String username){

        Ranking ranking = (Ranking) this.getServletContext().getAttribute(Constants.RANKING);
        RankingEntry entry = new RankingEntry(username, g);
        synchronized (this){
            ranking.add(entry);
        }

    }
}
