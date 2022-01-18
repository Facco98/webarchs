package it.unitn.disi.webarchs.facchinetti.booker.factory;

import it.unitn.disi.webarchs.facchinetti.booker.dto.AccommodationPriceDTO;
import it.unitn.disi.webarchs.facchinetti.booker.dto.ApartmentPriceDTO;
import it.unitn.disi.webarchs.facchinetti.booker.dto.HotelPriceDTO;
import it.unitn.disi.webarchs.facchinetti.booker.entity.Accommodation;
import it.unitn.disi.webarchs.facchinetti.booker.entity.Apartment;
import it.unitn.disi.webarchs.facchinetti.booker.entity.Hotel;

import java.util.function.Function;

public class AccommodationPriceDTOFactory {

    public static AccommodationPriceDTO generate(Accommodation accommodation, Integer numberOfPeople, Integer nDays){

        if( accommodation instanceof Hotel )
            return generateHotel((Hotel) accommodation, numberOfPeople, nDays);
        else if( accommodation instanceof Apartment )
            return generateApartment((Apartment) accommodation, nDays);
        else
            throw new IllegalArgumentException("Accommodation must be either be a hotel or an apartment");

    }

    private static HotelPriceDTO generateHotel(Hotel hotel, Integer numberOfPeople, Integer nDays){

        Double finalPrice = hotel.getPrice() * numberOfPeople * nDays;
        Double finalPriceWithHalfBoard = ( hotel.getPrice() + hotel.getHalfBoardExtraPrice() ) * numberOfPeople * nDays;
        HotelPriceDTO hotelPriceDTO = new HotelPriceDTO(finalPrice, finalPriceWithHalfBoard, hotel.getHalfBoardExtraPrice());

        return hotelPriceDTO;

    }

    private static ApartmentPriceDTO generateApartment(Apartment apartment, Integer nDays ){

        Double finalPrice = apartment.getPrice() * nDays + apartment.getFinalCleaningPrice();
        ApartmentPriceDTO apartmentPriceDTO = new ApartmentPriceDTO(finalPrice, apartment.getFinalCleaningPrice());

        return apartmentPriceDTO;

    }

}
