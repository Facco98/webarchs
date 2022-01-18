package it.unitn.disi.webarchs.facchinetti.booker.externalweb.servlet;

import it.unitn.disi.webarchs.facchinetti.booker.dto.AccommodationDTO;
import it.unitn.disi.webarchs.facchinetti.booker.externalweb.RemoteServiceLoader;
import it.unitn.disi.webarchs.facchinetti.booker.facade.ReservationManager;
import it.unitn.disi.webarchs.facchinetti.booker.externalweb.pojos.AccommodationsList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "BookServlet", value = {"/book", "/"})
public class BookServlet extends HttpServlet {

    private ReservationManager reservationManager;

    public void init() {
        this.reservationManager = RemoteServiceLoader.getInstance().getReservationManager();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String startDateString = request.getParameter("startDate");
        String leavingDateString = request.getParameter("leavingDate");
        String numberOfPeopleString = request.getParameter("numberPeople");
        String accommodationType = request.getParameter("accommodationType");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date startDate = null;
        Date endDate = null;
        Integer numberOfPeople = null;

        try {
            startDate = formatter.parse(startDateString);
            endDate =  formatter.parse(leavingDateString);
            numberOfPeople = Integer.parseInt(numberOfPeopleString);

        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }


        if( startDate == null || endDate == null || endDate.before(startDate) || numberOfPeople == null || numberOfPeople <= 0 || accommodationType == null){
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } else {

            HttpSession session = request.getSession();
            session.setAttribute("startDate", startDate);
            session.setAttribute("endDate", endDate);
            session.setAttribute("numberOfPeople", numberOfPeople);

            List<AccommodationDTO> available = new ArrayList<>();
            switch(accommodationType){
                case "all":
                    available = this.reservationManager.getAccommodations(startDate, endDate, numberOfPeople);
                    break;
                case "hotels":
                    available = this.reservationManager.getHotels(startDate, endDate, numberOfPeople);
                    break;
                case "apartments":
                    available = this.reservationManager.getApartments(startDate, endDate, numberOfPeople);
                    break;
            }

            AccommodationsList list = new AccommodationsList();
            list.setAccommodations(available);
            session.setAttribute("accommodations", list);
            request.getRequestDispatcher("/list.jsp").forward(request, response);


        }

    }

    public void destroy() {
    }
}
