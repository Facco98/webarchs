package it.unitn.disi.webarchs.facchinetti.booker.dto;

import java.io.Serializable;

public class HotelPriceDTO extends AccommodationPriceDTO implements Serializable {

    private final static long serialVersionUID = 1L;

    private Double finalPriceWithHalfBoard;
    private Double pricePerDayOfHalfBoard;

    public HotelPriceDTO(){

    }

    public HotelPriceDTO(Double finalPrice, Double finalPriceWithHalfBoard, Double pricePerDayOfHalfBoard) {
        super(finalPrice);
        this.finalPriceWithHalfBoard = finalPriceWithHalfBoard;
        this.pricePerDayOfHalfBoard = pricePerDayOfHalfBoard;
    }

    public Double getPricePerDayOfHalfBoard() {
        return pricePerDayOfHalfBoard;
    }

    public void setPricePerDayOfHalfBoard(Double pricePerDayOfHalfBoard) {
        this.pricePerDayOfHalfBoard = pricePerDayOfHalfBoard;
    }

    public Double getFinalPriceWithHalfBoard() {
        return finalPriceWithHalfBoard;
    }

    public void setFinalPriceWithHalfBoard(Double finalPriceWithHalfBoard) {
        this.finalPriceWithHalfBoard = finalPriceWithHalfBoard;
    }
}
