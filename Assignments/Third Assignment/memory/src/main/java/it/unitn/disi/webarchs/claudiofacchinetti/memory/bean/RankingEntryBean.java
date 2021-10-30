package it.unitn.disi.webarchs.claudiofacchinetti.memory.bean;

public class RankingEntryBean {

    private String username;
    private Integer points;

    public RankingEntryBean(String username, Integer points) {
        this.username = username;
        this.points = points;
    }

    public RankingEntryBean() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
