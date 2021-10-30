package it.unitn.disi.webarch.facchinetti.chatapp.model;

public enum Role {

    USER,
    ADMIN;

    public boolean isAdmin(){

        return this.equals(ADMIN);

    }

}
