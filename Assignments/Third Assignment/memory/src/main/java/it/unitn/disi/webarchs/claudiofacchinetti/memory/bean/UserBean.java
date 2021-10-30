package it.unitn.disi.webarchs.claudiofacchinetti.memory.bean;

public class UserBean {

    private String username;
    private String gameId;

    public UserBean() {
    }

    public UserBean(String username, String gameId) {
        this.username = username;
        this.gameId = gameId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
