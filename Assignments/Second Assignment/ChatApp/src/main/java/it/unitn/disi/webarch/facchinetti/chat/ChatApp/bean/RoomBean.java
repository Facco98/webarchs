package it.unitn.disi.webarch.facchinetti.chat.ChatApp.bean;

public class RoomBean {

    private String name;
    private MessageListBean messages;

    public RoomBean() {
    }

    public RoomBean(String name, MessageListBean messages) {
        this.name = name;
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MessageListBean getMessages() {
        return messages;
    }

    public void setMessages(MessageListBean messages) {
        this.messages = messages;
    }
}
