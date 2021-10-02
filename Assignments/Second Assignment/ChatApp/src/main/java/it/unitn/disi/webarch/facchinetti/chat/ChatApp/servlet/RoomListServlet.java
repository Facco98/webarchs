package it.unitn.disi.webarch.facchinetti.chat.ChatApp.servlet;

import it.unitn.disi.webarch.facchinetti.chat.ChatApp.bean.RoomListItemBean;
import it.unitn.disi.webarch.facchinetti.chat.ChatApp.bean.RoomListBean;
import it.unitn.disi.webarch.facchinetti.chat.ChatApp.model.Room;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RoomListServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        Map<String, Room> rooms = new ConcurrentHashMap<>();
        Room exampleRoom = new Room("Room 1");
        rooms.put(exampleRoom.getId(), exampleRoom);
        this.getServletContext().setAttribute("ROOMS", rooms);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Room> roomMap = (Map<String, Room>) this.getServletContext().getAttribute("ROOMS");
        RoomListBean rooms = new RoomListBean();
        roomMap.values().forEach( room -> {
            RoomListItemBean roomListItem = new RoomListItemBean();
            String url = "/user/room?id=" + room.getId();
            roomListItem.setUrl(resp.encodeURL(url));
            roomListItem.setName(room.getName());
            rooms.add(roomListItem);
        });
        req.setAttribute("rooms", rooms);
        this.getServletContext().log("DONE");
        req.getRequestDispatcher("/user/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



    }
}
