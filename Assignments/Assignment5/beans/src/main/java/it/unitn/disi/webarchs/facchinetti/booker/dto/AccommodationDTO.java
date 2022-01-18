package it.unitn.disi.webarchs.facchinetti.booker.dto;

import java.io.Serializable;

public abstract class AccommodationDTO implements Serializable {

    private final static long serialVersionUID = 1L;

    private Integer id;
    private String name;

    public AccommodationDTO(){

    }

    public AccommodationDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract String getType();
}
