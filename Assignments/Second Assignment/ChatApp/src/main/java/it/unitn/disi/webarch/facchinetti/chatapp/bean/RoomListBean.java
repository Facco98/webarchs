package it.unitn.disi.webarch.facchinetti.chatapp.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

public class RoomListBean {

    private Collection<RoomListItemBean> roomList;
    private String message;
    private String resultMessage;

    public RoomListBean() {
    }

    public RoomListBean(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Collection<RoomListItemBean> getRoomList() {
        return roomList;
    }

    public void setRoomList(Collection<RoomListItemBean> roomList) {
        this.roomList = roomList;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
