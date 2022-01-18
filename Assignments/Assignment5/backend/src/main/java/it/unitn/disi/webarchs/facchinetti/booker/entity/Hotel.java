package it.unitn.disi.webarchs.facchinetti.booker.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Entity
@DiscriminatorValue("HOTEL")
public class Hotel extends Accommodation implements Serializable {

    private Double halfBoardExtraPrice;
    private Integer numberOfStars;
    private Integer totalNumberOfRooms;

    public Hotel() {
    }

    @Basic
    @Column(name="half_board_extra_price")
    public Double getHalfBoardExtraPrice() {
        return halfBoardExtraPrice;
    }

    public void setHalfBoardExtraPrice(Double halfBoardPrice) {
        this.halfBoardExtraPrice = halfBoardPrice;
    }


    @Basic
    @Column(name="number_of_stars")
    public Integer getNumberOfStars() {
        return numberOfStars;
    }

    public void setNumberOfStars(Integer numberOfStars) {
        this.numberOfStars = numberOfStars;
    }


    @Basic
    @Column(name="total_number_of_rooms")
    public Integer getTotalNumberOfRooms() {
        return totalNumberOfRooms;
    }

    public void setTotalNumberOfRooms(Integer maxNumberOfPeople) {
        this.totalNumberOfRooms = maxNumberOfPeople;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Hotel hotel = (Hotel) o;
        return Objects.equals(halfBoardExtraPrice, hotel.halfBoardExtraPrice) && Objects.equals(numberOfStars, hotel.numberOfStars) && Objects.equals(totalNumberOfRooms, hotel.totalNumberOfRooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), halfBoardExtraPrice, numberOfStars, totalNumberOfRooms);
    }
}
