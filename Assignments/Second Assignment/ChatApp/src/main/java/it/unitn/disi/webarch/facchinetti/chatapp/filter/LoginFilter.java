package it.unitn.disi.webarch.facchinetti.chatapp.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class LoginFilter extends FilterImplementation {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        Optional<HttpSession> session = Optional.ofNullable(httpRequest.getSession(false));

        this.filterConfig.getServletContext().log(""+session.isPresent());
        if( session.isPresent() && session.get().getAttribute("USER") != null ){
            // If user is already logged in, skip login
            this.filterConfig.getServletContext().log("USER IS LOGGED IN");
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.sendRedirect(this.filterConfig.getServletContext().getContextPath() + "/user");
        } else {
            // Otherwise proceed
            this.filterConfig.getServletContext().log("USER IS NOT LOGGED IN");
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
