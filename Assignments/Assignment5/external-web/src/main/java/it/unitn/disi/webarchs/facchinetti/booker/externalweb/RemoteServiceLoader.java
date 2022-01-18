package it.unitn.disi.webarchs.facchinetti.booker.externalweb;

import it.unitn.disi.webarchs.facchinetti.booker.facade.ReservationManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class RemoteServiceLoader {

    private static RemoteServiceLoader instance;

    private Context context;
    private ReservationManager reservationManager;

    private RemoteServiceLoader(){
        this.context = createInitialContext();
    }

    private Context createInitialContext() {
        try {
            Properties jndiProperties = new Properties();
            jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,
                    "org.jboss.naming.remote.client.InitialContextFactory");
            jndiProperties.put(Context.URL_PKG_PREFIXES,
                    "org.jboss.ejb.client.naming");
            jndiProperties.put(Context.PROVIDER_URL,
                    "http-remoting://localhost:8080");
            jndiProperties.put("jboss.naming.client.ejb.context", true);
            return new InitialContext(jndiProperties);
        } catch (NamingException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public synchronized ReservationManager getReservationManager(){
        if( this.reservationManager == null ){
            try {
                this.reservationManager = (ReservationManager) context.lookup("ejb:/unnamed/ClientManager!it.unitn.disi.webarchs.facchinetti.booker.facade.ReservationManager");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return this.reservationManager;
    }

    public static RemoteServiceLoader getInstance(){

        if( instance == null ){
            instance = new RemoteServiceLoader();
        }
        return instance;

    }
}
