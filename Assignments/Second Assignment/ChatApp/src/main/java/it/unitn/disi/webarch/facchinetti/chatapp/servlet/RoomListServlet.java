package it.unitn.disi.webarch.facchinetti.chatapp.servlet;

import it.unitn.disi.webarch.facchinetti.chatapp.bean.RoomListItemBean;
import it.unitn.disi.webarch.facchinetti.chatapp.bean.RoomListBean;
import it.unitn.disi.webarch.facchinetti.chatapp.model.Room;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

public class RoomListServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        Map<String, Room> rooms = new ConcurrentHashMap<>();
        this.getServletContext().setAttribute("ROOMS", rooms);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RoomListBean rooms = this.getRooms(null,resp);
        req.setAttribute("rooms", rooms);
        req.getRequestDispatcher("/user/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String roomName = req.getParameter("roomName");

        String message = null;

        if( roomName != null && !roomName.trim().equals("") ){
            Map<String, Room> roomMap = (Map<String, Room>) this.getServletContext().getAttribute("ROOMS");
            boolean alreadyExists = roomMap.getOrDefault(roomName, null) != null;
            if( !alreadyExists ){
                Room r = new Room(roomName);
                roomMap.put(r.getId(), new Room(roomName));
                message = "Room created correctly";
            } else {
                message = "The room already exists";
            }
        } else {
            message = "Please provide a valid room name";
        }

        RoomListBean rooms = this.getRooms(message, resp);
        req.setAttribute("rooms", rooms);
        req.getRequestDispatcher("/user/index.jsp").forward(req, resp);

    }

    private RoomListBean getRooms(String resultMessage, HttpServletResponse resp){

        RoomListBean rooms = new RoomListBean();
        rooms.setRoomList(new Stack<>());
        Map<String, Room> roomMap = (Map<String, Room>) this.getServletContext().getAttribute("ROOMS");

        roomMap.values().forEach( room -> {
            RoomListItemBean roomListItem = new RoomListItemBean();
            String url = "/user/room?id=" + room.getId();
            roomListItem.setUrl(resp.encodeURL(url));
            roomListItem.setName(room.getName());
            rooms.getRoomList().add(roomListItem);
        });
        rooms.setMessage(resultMessage);
        String initialMessage = "Join a room or create a new one";
        if( rooms.getRoomList().size() == 0 )
            initialMessage = "There are no rooms yet, create a new one!";
        rooms.setMessage(initialMessage);

        return rooms;

    }
}
