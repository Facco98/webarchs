package it.unitn.disi.webarchs.claudiofacchinetti.memory.builder;

import it.unitn.disi.webarchs.claudiofacchinetti.memory.bean.GameBean;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.model.Card;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.model.Game;


public class GameBeanBuilder {

    private Game game;
    private Boolean failed;

    public GameBeanBuilder(){

    }

    public GameBean build(){

        GameBean gameBean = new GameBean();
        gameBean.setScore(game.getGameState().getScore());
        gameBean.setAttempts(game.getGameState().getAttempts());
        int r = game.getGameState().getField().length;
        int c = game.getGameState().getField()[0].length;
        Integer[][] values = new Integer[r][c];
        for( int i = 0; i < r; i++ ){
            for( int j = 0; j < c; j++ ){
                Card card = game.getGameState().getField()[i][j];
                values[i][j] = card.isFaceUp() ? card.getValue() : -1;
            }
        }
        gameBean.setValues(values);
        gameBean.setFailed(this.failed);

        return gameBean;
    }


    public GameBeanBuilder setGame(Game game) {
        this.game = game;
        return this;
    }

    public GameBeanBuilder setFailed(Boolean failed) {
        this.failed = failed;
        return this;
    }
}
