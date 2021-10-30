package it.unitn.disi.webarchs.claudiofacchinetti.memory.model;

public class RankingEntry implements Comparable<RankingEntry> {

    private String username;
    private Game game;

    public RankingEntry(String username, Game game) {
        this.username = username;
        this.game = game;
    }

    @Override
    public int compareTo(RankingEntry o) {
        return -this.game.getGameState().getScore().compareTo(o.game.getGameState().getScore());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
