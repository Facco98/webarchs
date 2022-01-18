package it.unitn.disi.webarchs.facchinetti.booker.dto;

import java.io.Serializable;

public class ApartmentDTO extends AccommodationDTO implements Serializable {

    private final static long serialVersionUID = 1L;

    private Integer maxNumberOfPeople;

    public ApartmentDTO() {
    }

    public ApartmentDTO(Integer id, String name, Integer maxNumberOfPeople) {
        super(id, name);
        this.maxNumberOfPeople = maxNumberOfPeople;
    }

    public Integer getMaxNumberOfPeople() {
        return maxNumberOfPeople;
    }

    public void setMaxNumberOfPeople(Integer maxNumberOfPeople) {
        this.maxNumberOfPeople = maxNumberOfPeople;
    }

    @Override
    public String getType() {
        return "Apartment";
    }

}
