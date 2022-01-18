package it.unitn.disi.webarchs.facchinetti.booker.externalweb.pojos;

import it.unitn.disi.webarchs.facchinetti.booker.dto.AccommodationDTO;

import java.util.List;

public class AccommodationsList {

    private List<AccommodationDTO> accommodations;

    public List<AccommodationDTO> getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(List<AccommodationDTO> accommodations) {
        this.accommodations = accommodations;
    }
}
