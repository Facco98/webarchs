package it.unitn.disi.webarch.facchinetti.chat.ChatApp.model;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Stack;
import java.util.UUID;

public class Room implements Serializable {

    // Once set they will never be updated
    private final String name;
    private final String id;
    private final Stack<Message> messages;

    public Room(@NotNull String name, @NotNull String id, @NotNull Stack<Message> messages) {
        this.name = name;
        this.id = id;
        this.messages = messages;
    }

    public Room(@NotNull String name){

        this.name = name;
        this.id = UUID.randomUUID().toString();
        this.messages = new Stack<>();

    }

    public String getName() {
        return name;
    }

    public Stack<Message> getMessages() {
        return messages;
    }

    public boolean addMessage(Message message){
        return this.messages.add(message);
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        return this.name.equals(room.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result;
        return result;
    }
}
