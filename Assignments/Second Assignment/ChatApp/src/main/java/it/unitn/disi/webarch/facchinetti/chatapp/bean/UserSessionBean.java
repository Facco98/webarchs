package it.unitn.disi.webarch.facchinetti.chatapp.bean;

public class UserSessionBean {

    private String username;
    private boolean isAdmin;

    public UserSessionBean() {
    }

    public UserSessionBean(String username, boolean isAdmin) {
        this.username = username;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
