package it.unitn.disi.webarchs.facchinetti.booker.facade;

import it.unitn.disi.webarchs.facchinetti.booker.bean.AccommodationBean;
import it.unitn.disi.webarchs.facchinetti.booker.bean.ApartmentBean;
import it.unitn.disi.webarchs.facchinetti.booker.bean.HotelBean;
import it.unitn.disi.webarchs.facchinetti.booker.bean.ReservationBean;
import it.unitn.disi.webarchs.facchinetti.booker.dto.*;
import it.unitn.disi.webarchs.facchinetti.booker.entity.Accommodation;
import it.unitn.disi.webarchs.facchinetti.booker.entity.Apartment;
import it.unitn.disi.webarchs.facchinetti.booker.entity.Hotel;
import it.unitn.disi.webarchs.facchinetti.booker.entity.Reservation;
import it.unitn.disi.webarchs.facchinetti.booker.factory.AccommodationDTOFactory;
import it.unitn.disi.webarchs.facchinetti.booker.factory.AccommodationPriceDTOFactory;
import it.unitn.disi.webarchs.facchinetti.booker.util.Util;
import org.jboss.logging.Logger;
import org.joda.time.Interval;

import javax.annotation.Resource;
import javax.ejb.*;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Stateless
@Remote(ReservationManager.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ClientManager implements ReservationManager{

    private static final Logger logger = Logger.getLogger(ClientManager.class);

    @EJB
    private AccommodationBean accommodationBean;

    @EJB
    private HotelBean hotelBean;

    @EJB
    private ApartmentBean apartmentBean;

    @EJB
    private ReservationBean reservationBean;

    @EJB
    private Util util;

    @Resource
    private SessionContext ctx;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<AccommodationDTO> getAccommodations(Date startDate, Date endDate, Integer nPeople) {

        logger.info("Retrieving accommodations");
        List <AccommodationDTO> result = this.getApartments(startDate, endDate, nPeople);
        result.addAll(this.getHotels(startDate, endDate, nPeople));
        return result;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<AccommodationDTO> getApartments(Date startDate, Date endDate, Integer nPeople) {

        logger.info("Retrieving apartments");
        Predicate<Apartment> isApartmentAvailable = (apartment) -> {
            logger.info("Evaluating availability for " + apartment.getName());
            if( apartment.getMaximumNumberOfPeople() < nPeople )
                return false;
            return this.availableSpots(apartment, startDate, endDate);
        };
        return this.apartmentBean.list(isApartmentAvailable).map(AccommodationDTOFactory::generate).collect(Collectors.toList());

    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<AccommodationDTO> getHotels(Date startDate, Date endDate, Integer nPeople) {
        Interval i = new Interval(startDate.getTime(), endDate.getTime());
        logger.info("Retrieving hotels");
        Predicate<Hotel> isHotelAvailable = (hotel) -> {
            logger.info("Evaluating availability for " + hotel.getName());
            return this.availableSpots(hotel, startDate, endDate);
        };
        return this.hotelBean.list(isHotelAvailable)
                .map(AccommodationDTOFactory::generate)
                .collect(Collectors.toList());
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public boolean reserve(Integer id, Date startDate, Date endDate, Integer nPeople, String userName, String userSurname, boolean halfBoard) {

        try {
            logger.info("Trying to reserve for accommodation with id: " + id);
            Accommodation a = this.accommodationBean.getAccommodation(id);
            boolean canReserve = false;
            if (a != null) {
                logger.info("Found accommodation: " + a.getName());
                canReserve = this.availableSpots(a, startDate, endDate)
                        && (!(a instanceof Apartment) || nPeople <= ((Apartment) a).getMaximumNumberOfPeople());
            }

            if (canReserve && userName != null && userSurname != null && userName.length() > 0 && userSurname.length() > 0 && !startDate.before(new Date())) {
                Integer nDays = (int) Duration.between(startDate.toInstant(),
                        endDate.toInstant().plus(1, ChronoUnit.DAYS)).toDays();
                AccommodationPriceDTO priceDTO = AccommodationPriceDTOFactory.generate(a, nPeople, nDays);
                this.reservationBean.createReservation(a, userName, userSurname,
                        startDate, endDate, priceDTO.getFinalPrice());
                logger.info("Reservation created with halfboard: " + halfBoard);
            } else {
                ctx.setRollbackOnly();
                logger.info("Could not create reservation");
            }

        } catch ( Exception ex ){
            logger.error("Error in reserving", ex);
            this.ctx.setRollbackOnly();
        }
        return !this.ctx.getRollbackOnly();

    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public AccommodationPriceDTO getPrice(Integer id, Date startDate, Date endDate, Integer nPeople) {

        Accommodation accommodation = this.accommodationBean.getAccommodation(id);
        if( accommodation != null ){
            logger.info("Retrieving price for accommodation: " + accommodation.getName());
            Integer nDays = (int) Duration.between(startDate.toInstant(),
                    endDate.toInstant().plus(1, ChronoUnit.DAYS)).toDays();
            AccommodationPriceDTO accommodationPriceDTO = AccommodationPriceDTOFactory.generate(accommodation, nPeople, nDays);
            return accommodationPriceDTO;
        }
        throw new IllegalArgumentException("Cannot find id: " + id);

    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ReservationDTO> listReservations(String userName, String userSurname) {

        List<ReservationDTO> result = this.reservationBean.list(userName, userSurname).map(reservation -> {

            Accommodation accommodation = reservation.getAccommodation();
            AccommodationDTO accommodationDTO = AccommodationDTOFactory.generate(accommodation);

            ReservationDTO reservationDTO = new ReservationDTO(reservation.getStartDate(),
                    reservation.getEndDate(), accommodationDTO, reservation.getPrice());
            return reservationDTO;
        }).collect(Collectors.toList());

        return result;
    }

    private boolean availableSpots(Accommodation a, Date startDate, Date endDate){
        Interval i = new Interval(startDate.getTime(), endDate.getTime());
        long nOverlaps = a.getReservations().stream().filter((reservation -> {
            Interval i1 = new Interval(reservation.getStartDate().getTime(), reservation.getEndDate().getTime());
            return i1.overlaps(i);
        })).count();

        long maxOverlap = 1;
        if (a instanceof Hotel)
            maxOverlap = ((Hotel) a).getTotalNumberOfRooms();

        return nOverlaps < maxOverlap;
    }
}
