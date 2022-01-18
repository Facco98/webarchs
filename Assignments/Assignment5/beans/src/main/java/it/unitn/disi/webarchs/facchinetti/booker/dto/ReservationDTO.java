package it.unitn.disi.webarchs.facchinetti.booker.dto;

import java.io.Serializable;
import java.util.Date;

public class ReservationDTO implements Serializable {

    private final static long serialVersionUID = 1L;

    private Date startDate;
    private Date endDate;
    private AccommodationDTO accommodationDTO;
    private Double price;

    public ReservationDTO() {
    }

    public ReservationDTO(Date startDate, Date endDate, AccommodationDTO accommodationDTO, Double price) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.accommodationDTO = accommodationDTO;
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public AccommodationDTO getAccommodationDTO() {
        return accommodationDTO;
    }

    public void setAccommodationDTO(AccommodationDTO accommodationDTO) {
        this.accommodationDTO = accommodationDTO;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
