package it.unitn.disi.webarchs.facchinetti.booker.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Entity
@DiscriminatorValue("APARTMENT")
public class Apartment extends Accommodation implements Serializable {

    private Double finalCleaningPrice;
    private Integer maximumNumberOfPeople;

    public Apartment() {
    }


    @Basic
    @Column(name="final_cleaning_price")
    public Double getFinalCleaningPrice() {
        return finalCleaningPrice;
    }

    public void setFinalCleaningPrice(Double finalCleaningPrice) {
        this.finalCleaningPrice = finalCleaningPrice;
    }


    @Basic
    @Column(name="max_number_of_people")
    public Integer getMaximumNumberOfPeople() {
        return maximumNumberOfPeople;
    }

    public void setMaximumNumberOfPeople(Integer maximumNumberOfPeople) {
        this.maximumNumberOfPeople = maximumNumberOfPeople;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Apartment apartment = (Apartment) o;
        return Objects.equals(finalCleaningPrice, apartment.finalCleaningPrice) && Objects.equals(maximumNumberOfPeople, apartment.maximumNumberOfPeople);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), finalCleaningPrice, maximumNumberOfPeople);
    }
}
