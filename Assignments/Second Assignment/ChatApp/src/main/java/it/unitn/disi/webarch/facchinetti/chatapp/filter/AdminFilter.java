package it.unitn.disi.webarch.facchinetti.chatapp.filter;

import it.unitn.disi.webarch.facchinetti.chatapp.bean.UserSessionBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminFilter extends FilterImplementation{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpRequest.getSession(false);

        boolean authorized = false;

        // It should always be there, but just in case
        if( session != null ){
            UserSessionBean user = (UserSessionBean) session.getAttribute("USER");
            authorized = user.isAdmin();
        }

        this.filterConfig.getServletContext().log("User is authorized? " + authorized);

        if( authorized ){
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpRequest.getRequestDispatcher("/user").forward(servletRequest, servletResponse);
        }
    }
}
