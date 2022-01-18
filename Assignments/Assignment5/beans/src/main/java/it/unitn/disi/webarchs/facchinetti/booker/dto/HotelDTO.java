package it.unitn.disi.webarchs.facchinetti.booker.dto;

import java.io.Serializable;

public class HotelDTO extends AccommodationDTO implements Serializable {

    private final static long serialVersionUID = 1L;

    private Integer numberOfStarts;

    public HotelDTO() {

    }

    public HotelDTO(Integer id, String name, Integer numberOfStarts) {
        super(id, name);
        this.numberOfStarts = numberOfStarts;
    }

    public Integer getNumberOfStarts() {
        return numberOfStarts;
    }

    public void setNumberOfStarts(Integer numberOfStarts) {
        this.numberOfStarts = numberOfStarts;
    }

    @Override
    public String getType() {
        return "Hotel";
    }

}
