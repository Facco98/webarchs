package it.unitn.disi.webarchs.facchinetti.booker.externalweb.servlet;

import it.unitn.disi.webarchs.facchinetti.booker.dto.ReservationDTO;
import it.unitn.disi.webarchs.facchinetti.booker.externalweb.RemoteServiceLoader;
import it.unitn.disi.webarchs.facchinetti.booker.externalweb.pojos.ReservationList;
import it.unitn.disi.webarchs.facchinetti.booker.facade.ReservationManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@WebServlet(name="ReservationServlet" ,value = {"/reservations"})
public class ReservationServlet extends HttpServlet {

    private ReservationManager reservationManager;

    public void init() {
        this.reservationManager = RemoteServiceLoader.getInstance().getReservationManager();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        try {
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");

            List<ReservationDTO> reservationDTOList = this.reservationManager.listReservations(name, surname);

            ReservationList l = new ReservationList();
            l.setReservations(reservationDTOList);

            request.setAttribute("reservations", l);

            request.getRequestDispatcher("/reservations.jsp").forward(request, response);
        } catch( Exception ex ){
            ex.printStackTrace();
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        HttpSession session = req.getSession(false);
        try{
            String name = req.getParameter("name");
            String surname = req.getParameter("surname");
            String creditCardNumber = req.getParameter("creditCard");

            Date startDate = (Date) session.getAttribute("startDate");
            Date endDate = (Date) session.getAttribute("endDate");
            Integer numberOfPeople = (Integer) session.getAttribute("numberOfPeople");
            Integer id = Integer.parseInt(req.getParameter("id"));

            Boolean halfBoard = Optional.ofNullable(req.getParameter("halfBoard")).isPresent();


            if( name.length() == 0 || surname.length() == 0 || creditCardNumber.length() == 0 )
                throw new InvalidParameterException("Name/surname/creditCard is empty");

            if( startDate == null || endDate == null || numberOfPeople == null || numberOfPeople <= 0 ){
                throw new InvalidParameterException("Invalid parameters");
            }

            boolean reserved = this.reservationManager.reserve(id, startDate, endDate, numberOfPeople, name, surname, halfBoard);
            if( reserved ){
                session.invalidate();
                req.getRequestDispatcher("/confirmation.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("/abort.jsp").forward(req, resp);
            }

        } catch( Exception ex ){
            ex.printStackTrace();
            req.getRequestDispatcher("/abort.jsp").forward(req, resp);

        }

    }

}
