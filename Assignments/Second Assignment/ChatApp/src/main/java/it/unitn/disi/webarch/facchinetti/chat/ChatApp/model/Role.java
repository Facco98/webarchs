package it.unitn.disi.webarch.facchinetti.chat.ChatApp.model;

public enum Role {

    USER,
    ADMIN;

    public boolean isAdmin(){

        return this.equals(ADMIN);

    }

}
