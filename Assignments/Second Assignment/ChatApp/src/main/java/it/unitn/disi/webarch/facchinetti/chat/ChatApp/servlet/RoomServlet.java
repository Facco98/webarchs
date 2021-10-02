package it.unitn.disi.webarch.facchinetti.chat.ChatApp.servlet;

import it.unitn.disi.webarch.facchinetti.chat.ChatApp.bean.MessageBean;
import it.unitn.disi.webarch.facchinetti.chat.ChatApp.bean.MessageListBean;
import it.unitn.disi.webarch.facchinetti.chat.ChatApp.bean.RoomBean;
import it.unitn.disi.webarch.facchinetti.chat.ChatApp.model.Message;
import it.unitn.disi.webarch.facchinetti.chat.ChatApp.model.Room;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class RoomServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roomId = req.getParameter("id");
        String messageContent = req.getParameter("messageContent");
        Map<String, Room> roomMap = (Map<String, Room>) this.getServletContext().getAttribute("ROOMS");
        Room room = null;
        if( roomId != null && messageContent != null && (room = roomMap.get(roomId)) != null ) {

            String author = (String) req.getSession(false).getAttribute("USER");
            Message m = new Message(messageContent, author, LocalDateTime.now());
            room.addMessage(m);
            resp.sendRedirect(req.getRequestURL().toString()+"?id="+roomId);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roomId = req.getParameter("id");
        Map<String, Room> roomMap = (Map<String, Room>) this.getServletContext().getAttribute("ROOMS");
        Room room = null;
        if( roomId != null && (room = roomMap.get(roomId)) != null ){

            MessageListBean listBean = new MessageListBean();
            room.getMessages().forEach(m -> {
                MessageBean messageBean = new MessageBean(m.getContent(), m.getAuthor(), m.getPublicationTime().toString());
                listBean.add(messageBean);
            });

            RoomBean roomInfo = new RoomBean(room.getName(), listBean);
            req.setAttribute("roomInfo", roomInfo);
            req.getRequestDispatcher("/user/room/index.jsp").forward(req, resp);
        } else {

            resp.sendError(HttpServletResponse.SC_NOT_FOUND);

        }
    }
}
