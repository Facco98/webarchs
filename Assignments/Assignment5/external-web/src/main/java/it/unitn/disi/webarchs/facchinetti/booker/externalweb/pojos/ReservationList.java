package it.unitn.disi.webarchs.facchinetti.booker.externalweb.pojos;

import it.unitn.disi.webarchs.facchinetti.booker.dto.AccommodationDTO;
import it.unitn.disi.webarchs.facchinetti.booker.dto.ReservationDTO;

import java.util.List;

public class ReservationList {

    private List<ReservationDTO> reservations;

    public List<ReservationDTO> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationDTO> reservations) {
        this.reservations = reservations;
    }
}
