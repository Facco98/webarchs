package it.unitn.disi.webarch.facchinetti.chatapp.model;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Room implements Serializable {

    // Once set they will never be updated
    private final String name;
    private final String id;
    private final ArrayDeque<Message> messages;

    public Room(@NotNull String name, @NotNull String id, @NotNull ArrayDeque<Message> messages) {
        this.name = name;
        this.id = id;
        this.messages = messages;
    }

    public Room(@NotNull String name){

        this.name = name;
        this.id = UUID.nameUUIDFromBytes(this.name.getBytes(StandardCharsets.UTF_8)).toString();
        this.messages = new ArrayDeque<>();

    }

    public String getName() {
        return name;
    }

    public Deque<Message> getMessages() {
        return messages;
    }

    public synchronized void addMessage(Message message){
        this.messages.push(message);
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
