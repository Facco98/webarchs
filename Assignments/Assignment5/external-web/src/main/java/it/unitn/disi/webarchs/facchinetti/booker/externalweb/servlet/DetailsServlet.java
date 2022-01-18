package it.unitn.disi.webarchs.facchinetti.booker.externalweb.servlet;

import it.unitn.disi.webarchs.facchinetti.booker.dto.AccommodationDTO;
import it.unitn.disi.webarchs.facchinetti.booker.dto.AccommodationPriceDTO;
import it.unitn.disi.webarchs.facchinetti.booker.dto.HotelDTO;
import it.unitn.disi.webarchs.facchinetti.booker.facade.ReservationManager;
import it.unitn.disi.webarchs.facchinetti.booker.externalweb.RemoteServiceLoader;
import it.unitn.disi.webarchs.facchinetti.booker.externalweb.pojos.AccommodationsList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@WebServlet(name="DetailsServlet" ,value = {"/details"})
public class DetailsServlet extends HttpServlet {

    private ReservationManager reservationManager;

    public void init() {
        this.reservationManager = RemoteServiceLoader.getInstance().getReservationManager();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        try {
            HttpSession session = request.getSession();
            String accId = request.getParameter("id");
            Date startDate = (Date) session.getAttribute("startDate");
            Date endDate = (Date) session.getAttribute("endDate");
            Integer numberOfPeople = (Integer) session.getAttribute("numberOfPeople");
            AccommodationsList list = (AccommodationsList) session.getAttribute("accommodations");
            Integer index = Integer.parseInt(accId);

            AccommodationDTO accommodationDTO = list.getAccommodations().get(index);

            AccommodationPriceDTO accommodationPriceDTO = this.reservationManager.getPrice(accommodationDTO.getId(), startDate, endDate, numberOfPeople);
            request.setAttribute("accommodationPrice", accommodationPriceDTO);
            request.setAttribute("accommodation", accommodationDTO );

            if(accommodationDTO instanceof HotelDTO)
                request.getRequestDispatcher("/details_hotel.jsp").forward(request, response);
            else
                request.getRequestDispatcher("/details_ap.jsp").forward(request, response);

        } catch( Exception ex ){
            ex.printStackTrace();
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

}
