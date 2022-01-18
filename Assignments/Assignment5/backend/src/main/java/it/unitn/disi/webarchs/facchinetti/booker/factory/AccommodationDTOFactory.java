package it.unitn.disi.webarchs.facchinetti.booker.factory;

import it.unitn.disi.webarchs.facchinetti.booker.dto.AccommodationDTO;
import it.unitn.disi.webarchs.facchinetti.booker.dto.ApartmentDTO;
import it.unitn.disi.webarchs.facchinetti.booker.dto.HotelDTO;
import it.unitn.disi.webarchs.facchinetti.booker.entity.Accommodation;
import it.unitn.disi.webarchs.facchinetti.booker.entity.Apartment;
import it.unitn.disi.webarchs.facchinetti.booker.entity.Hotel;

public class AccommodationDTOFactory {

    public static AccommodationDTO generate(Accommodation a){
        if( a instanceof Hotel)
            return generateHotel((Hotel)a);
        else
            return generateApartment((Apartment) a);
    }

    private static HotelDTO generateHotel(Hotel hotel){
        return new HotelDTO(hotel.getId(), hotel.getName(), hotel.getNumberOfStars());
    }

    private static ApartmentDTO generateApartment(Apartment a){
        return new ApartmentDTO(a.getId(), a.getName(), a.getMaximumNumberOfPeople());
    }

}
