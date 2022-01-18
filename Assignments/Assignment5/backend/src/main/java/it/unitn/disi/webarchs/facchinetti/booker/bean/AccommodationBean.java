package it.unitn.disi.webarchs.facchinetti.booker.bean;

import it.unitn.disi.webarchs.facchinetti.booker.entity.Accommodation;
import org.jboss.logging.Logger;

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
public class AccommodationBean {

    private final static Logger LOGGER = Logger.getLogger(AccommodationBean.class);

    @PersistenceContext(unitName="booker")
    private EntityManager entityManager;

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Stream<? extends Accommodation> list(){

        TypedQuery<Accommodation> q = this.entityManager.createQuery("SELECT a FROM Accommodation a", Accommodation.class);
        return q.getResultStream();
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Stream<? extends Accommodation> list(Predicate<? super Accommodation> predicate) {
        return this.list().filter(predicate);
    }

    public Accommodation getAccommodation(Integer id){
        TypedQuery<Accommodation> q = this.entityManager.createQuery("SELECT a FROM Accommodation a WHERE a.id = :id ", Accommodation.class);
        q.setParameter("id", id);
        return q.getSingleResult();
    }

}
