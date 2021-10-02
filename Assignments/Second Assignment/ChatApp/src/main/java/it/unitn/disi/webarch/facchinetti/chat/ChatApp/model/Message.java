package it.unitn.disi.webarch.facchinetti.chat.ChatApp.model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public class Message {

    // Once set they will never be updated
    private final String content;
    private final String author;
    private final LocalDateTime publicationTime;

    public Message(@NotNull String content, String author, @NotNull LocalDateTime publicationTime) {
        this.content = content;
        this.author = author;
        this.publicationTime = publicationTime;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }


    public LocalDateTime getPublicationTime() {
        return publicationTime;
    }

}
