package it.unitn.disi.webarchs.claudiofacchinetti.memory.bean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.model.Game;


public class GameBean {

    private Integer[][] values;
    private Integer score;
    private Integer attempts;
    private Boolean failed;

    public GameBean() {
    }

    public GameBean(Integer[][] values, Integer score, Integer attempts) {
        this.values = values;
        this.score = score;
        this.attempts = attempts;
        this.failed = false;
    }

    public Integer[][] getValues() {
        return values;
    }

    public void setValues(Integer[][] values) {
        this.values = values;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public String toJSON() throws JsonProcessingException {

        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(this);

    }

    public Boolean getFailed() {
        return failed;
    }

    public void setFailed(Boolean failed) {
        this.failed = failed;
    }
}
