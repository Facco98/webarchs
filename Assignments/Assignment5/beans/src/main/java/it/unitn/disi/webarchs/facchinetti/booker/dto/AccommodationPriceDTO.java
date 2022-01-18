package it.unitn.disi.webarchs.facchinetti.booker.dto;

import java.io.Serializable;

public abstract class AccommodationPriceDTO implements Serializable {

    private final static long serialVersionUID = 1L;

    private Double finalPrice;

    public AccommodationPriceDTO() {
    }

    protected AccommodationPriceDTO(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }
}
