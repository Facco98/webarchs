package it.unitn.disi.webarchs.claudiofacchinetti.memory.model;

import java.util.UUID;

public class Game {

    public static final Integer FIELD_ROWS = 4;
    public static final Integer FIELD_COLS = 4;

    private String id;
    private GameState gameState;


    public Game(boolean random){

        this.id = UUID.randomUUID().toString();
        this.gameState = new GameState(FIELD_ROWS, FIELD_COLS, random);

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
