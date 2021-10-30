package it.unitn.disi.webarchs.claudiofacchinetti.memory.servlet.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.Constants;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.bean.RankingBean;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.bean.RankingEntryBean;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.builder.RankingBeanBuilder;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.model.Ranking;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.model.RankingEntry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class RankingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Ranking ranking = (Ranking) this.getServletContext().getAttribute(Constants.RANKING);
        RankingBeanBuilder rbb = new RankingBeanBuilder();
        RankingBean rb = rbb.setRanking(ranking).build();

        resp.setStatus(200);
        resp.setContentType("application/json");
        PrintWriter pw = resp.getWriter();
        ObjectMapper om = new ObjectMapper();
        pw.write(om.writeValueAsString(rb));

    }
}
