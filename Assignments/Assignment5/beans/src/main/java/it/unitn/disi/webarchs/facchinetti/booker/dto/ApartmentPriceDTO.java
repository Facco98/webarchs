package it.unitn.disi.webarchs.facchinetti.booker.dto;

import java.io.Serializable;

public class ApartmentPriceDTO extends AccommodationPriceDTO implements Serializable {

    private final static long serialVersionUID = 1L;

    private Double cleaningCost;

    public ApartmentPriceDTO(){

    }

    public ApartmentPriceDTO(Double finalPrice, Double cleaningCost) {
        super(finalPrice);
        this.cleaningCost = cleaningCost;
    }

    public Double getCleaningCost() {
        return cleaningCost;
    }

    public void setCleaningCost(Double cleaningCost) {
        this.cleaningCost = cleaningCost;
    }
}
