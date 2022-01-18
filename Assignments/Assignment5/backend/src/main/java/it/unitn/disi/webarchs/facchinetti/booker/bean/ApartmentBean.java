package it.unitn.disi.webarchs.facchinetti.booker.bean;

import it.unitn.disi.webarchs.facchinetti.booker.entity.Apartment;

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
public class ApartmentBean {

    @PersistenceContext(unitName = "booker")
    private EntityManager entityManager;


    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Stream<? extends Apartment> list(){
        TypedQuery<Apartment> q = this.entityManager.createQuery("SELECT a FROM Apartment a", Apartment.class);
        return q.getResultStream();
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Stream<? extends Apartment> list(Predicate<? super Apartment> predicate) {
        return this.list().filter(predicate);
    }


}
