package it.unitn.disi.webarchs.claudiofacchinetti.memory.model;


import it.unitn.disi.webarchs.claudiofacchinetti.memory.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameState {

    private Boolean finished;
    private Card[][] field;
    private Integer score;
    private Integer attempts;
    private Card lastFlipped;
    private Pair<Card, Card> toFlipNext;

    public GameState(Integer rows, Integer cols, boolean random){
        this.field = new Card[rows][cols];
        this.initField(random);
        this.finished = false;
        this.attempts = 4;
        this.score = 0;
        this.toFlipNext = null;
    }

    public boolean clicked(int r, int c){

        boolean toContinue = !this.field[r][c].isFaceUp() && !this.finished;
        if( !toContinue )
            return false;

        if( lastFlipped == null ){
            this.lastFlipped = this.field[r][c];
            this.lastFlipped.flip();
        } else {
            Card selected = this.field[r][c];
            selected.flip();
            if( selected.equals(this.lastFlipped) ){
                this.score += 2 * selected.getValue();
            } else {
                this.score -= 1;
                this.score = Math.max(this.score, 0);
                this.toFlipNext = new Pair<>(selected, lastFlipped);
            }
            this.attempts--;
            this.finished = this.attempts <= 0;
            this.lastFlipped = null;
        }

        return true;


    }

    private void initField(boolean random){

        List<Integer> values = new ArrayList<>();
        int nValues = this.field.length * this.field[0].length / 2;
        for( int i = 0; i < nValues; i++ ){
            values.add(i+1);
            values.add(i+1);
        }

        if( random ) Collections.shuffle(values);

        for( int i = 0; i < this.field.length; i++ ){
            for( int j = 0; j < this.field[0].length; j++ ){
                Card c = new Card(values.get(i * this.field[0].length + j));
                this.field[i][j] = c;
            }
        }

    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Card[][] getField() {
        return field;
    }

    public void setField(Card[][] field) {
        this.field = field;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public Card getLastFlipped() {
        return lastFlipped;
    }

    public void setLastFlipped(Card lastFlipped) {
        this.lastFlipped = lastFlipped;
    }

    public void checkLast(){

        if( this.toFlipNext != null ){

            this.toFlipNext.getFirst().flip();
            this.toFlipNext.getSecond().flip();
            this.toFlipNext = null;

        }

    }
}
