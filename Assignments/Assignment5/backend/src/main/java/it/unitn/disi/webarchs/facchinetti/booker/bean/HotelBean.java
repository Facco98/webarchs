package it.unitn.disi.webarchs.facchinetti.booker.bean;

import it.unitn.disi.webarchs.facchinetti.booker.entity.Accommodation;
import it.unitn.disi.webarchs.facchinetti.booker.entity.Hotel;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@LocalBean
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class HotelBean {

    @PersistenceContext(unitName = "booker")
    private EntityManager entityManager;

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Stream<? extends Hotel> list() {
        TypedQuery<Hotel> q = this.entityManager.createQuery("SELECT h FROM Hotel h", Hotel.class);
        return q.getResultStream();
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Stream<? extends Hotel> list(Predicate<? super Hotel> predicate) {
        return this.list().filter(predicate);
    }


}
