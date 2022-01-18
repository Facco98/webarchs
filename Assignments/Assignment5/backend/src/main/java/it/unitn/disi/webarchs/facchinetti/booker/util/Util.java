package it.unitn.disi.webarchs.facchinetti.booker.util;

import it.unitn.disi.webarchs.facchinetti.booker.bean.AccommodationBean;
import it.unitn.disi.webarchs.facchinetti.booker.bean.ApartmentBean;
import it.unitn.disi.webarchs.facchinetti.booker.bean.HotelBean;
import it.unitn.disi.webarchs.facchinetti.booker.bean.ReservationBean;
import it.unitn.disi.webarchs.facchinetti.booker.dto.AccommodationDTO;
import it.unitn.disi.webarchs.facchinetti.booker.dto.AccommodationPriceDTO;
import it.unitn.disi.webarchs.facchinetti.booker.factory.AccommodationPriceDTOFactory;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PostLoad;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@LocalBean
@Stateless
public class Util {

    private final static Logger logger = Logger.getLogger(Util.class);

    @EJB
    private HotelBean hotelBean;

    @EJB
    private ApartmentBean apartmentBean;

    @EJB
    private ReservationBean reservationBean;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void clearOccupancy(){

        this.reservationBean.list().forEach((reservation -> {
            logger.info("Removed reservation for user: " + reservation.getUserName() + " " + reservation.getUserSurname());
            this.reservationBean.deleteReservation(reservation);
        }));

    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void initDefaultOccupancy(){
        this.clearOccupancy();
        Random random = new Random();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.hotelBean.list().forEach(hotel -> {
            for( int i = 1; i <= 28; i++ ){

                int nOccupied = (int) (random.nextDouble() * hotel.getTotalNumberOfRooms());
                try {
                    Date startDate = sdf.parse("2022-02-" + (i < 10 ? "0"+i : ""+i));
                    Date endDate = startDate;
                    AccommodationPriceDTO priceDTO = AccommodationPriceDTOFactory.generate(hotel, 1, 1);
                    for( int j = 0; j < nOccupied; j++ ){
                        this.reservationBean.createReservation(hotel, "Mock", "Guest", startDate, endDate, priceDTO.getFinalPrice());
                    }
                    logger.info("Reservation created for hotel " + hotel.getName() + " on day: " + i );
                } catch (Exception ex){
                    logger.error("Failed to initialize reservation for hotel: " + hotel.getName() + " for day: " + i, ex);
                }

            }
        });

        this.apartmentBean.list().forEach(apartment -> {
            Set<Integer> notAvailable = new HashSet<>();
            while( notAvailable.size() < 4 ){
                int day = random.nextInt(28) + 1;
                if(notAvailable.add(day)){
                    Date startDate = null;
                    try {
                        startDate = sdf.parse("2022-02-" + (day < 10 ? "0"+day : ""+day));
                        Date endDate = startDate;
                        AccommodationPriceDTO priceDTO = AccommodationPriceDTOFactory.generate(apartment, 1, 1);
                        this.reservationBean.createReservation(apartment, "Mock", "Guest", startDate, endDate, priceDTO.getFinalPrice());
                        logger.info("Created reservation for apartment " + apartment.getName() + " on day: " + day);

                    } catch (Exception e) {
                        logger.error("Failed to create reservation for apartment " + apartment.getName() + " on day: " + day, e);
                    }
                }
            }
        });

    }



}
