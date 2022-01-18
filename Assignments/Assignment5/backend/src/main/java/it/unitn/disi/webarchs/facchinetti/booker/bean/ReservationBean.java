package it.unitn.disi.webarchs.facchinetti.booker.bean;

import it.unitn.disi.webarchs.facchinetti.booker.entity.Accommodation;
import it.unitn.disi.webarchs.facchinetti.booker.entity.Reservation;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.Date;
import java.util.stream.Stream;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReservationBean {

    @PersistenceContext(unitName = "booker")
    private EntityManager entityManager;

    @Resource
    private SessionContext ctx;


    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Stream<? extends Reservation> list(String userName, String userSurname){
        TypedQuery<Reservation> q = this.entityManager.createQuery("SELECT r FROM Reservation r WHERE r.userName = :name and r.userSurname = :surname", Reservation.class);
        q.setParameter("name", userName);
        q.setParameter("surname", userSurname);
        return q.getResultStream();
    }

    public Stream<? extends Reservation> list(){
        TypedQuery<Reservation> q = this.entityManager.createQuery("SELECT r FROM Reservation r", Reservation.class);
        return q.getResultStream();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createReservation(Accommodation a, String userName, String userSurname, Date startDate, Date endDate, Double price){

        if( startDate.after(endDate) ) {
            throw new IllegalArgumentException("Start date must be before endDate");
        }

        Reservation r = new Reservation();
        r.setAccommodation(a);
        r.setUserName(userName);
        r.setUserSurname(userSurname);
        r.setStartDate(new Timestamp(startDate.getTime()));
        r.setEndDate(new Timestamp(endDate.getTime()));
        r.setPrice(price);
        this.entityManager.persist(r);

    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteReservation(Reservation a){
        a.getAccommodation().getReservations().remove(a);
        this.entityManager.remove(a);
    }


}
