package it.unitn.disi.webarchs.facchinetti.booker.facade;

import it.unitn.disi.webarchs.facchinetti.booker.dto.AccommodationDTO;
import it.unitn.disi.webarchs.facchinetti.booker.dto.AccommodationPriceDTO;
import it.unitn.disi.webarchs.facchinetti.booker.dto.ReservationDTO;

import java.util.Date;
import java.util.List;

public interface ReservationManager {

    List<AccommodationDTO> getAccommodations(Date startDate, Date endDate, Integer numberOfPeople);

    List<AccommodationDTO> getApartments(Date startDate, Date endDate, Integer numberOfPeople);

    List<AccommodationDTO> getHotels(Date startDate, Date endDate, Integer numberOfPeople);

    boolean reserve(Integer accommodationId, Date startDate, Date endDate, Integer numberOfPeople, String userName, String userSurname, boolean halfBoard);

    AccommodationPriceDTO getPrice(Integer accommodationId, Date startDate, Date endDate, Integer numberOfPeople);

    List<ReservationDTO> listReservations(String userName, String userSurname);


}
