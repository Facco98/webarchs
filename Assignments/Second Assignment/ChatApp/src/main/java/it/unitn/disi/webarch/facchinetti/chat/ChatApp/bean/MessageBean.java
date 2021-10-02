package it.unitn.disi.webarch.facchinetti.chat.ChatApp.bean;

public class MessageBean {

    private String content;
    private String author;
    private String dateTime;

    public MessageBean() {
    }

    public MessageBean(String content, String author, String dateTime) {
        this.content = content;
        this.author = author;
        this.dateTime = dateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
