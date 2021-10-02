package it.unitn.disi.webarch.facchinetti.chat.ChatApp.filter;

import it.unitn.disi.webarch.facchinetti.chat.ChatApp.model.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            String username = (String) session.getAttribute("USER");
            authorized = this.getUsers().get(username).getRole().isAdmin();
        }

        if( authorized ){
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpRequest.getRequestDispatcher("/user/index.jsp").forward(servletRequest, servletResponse);
        }
    }
}
