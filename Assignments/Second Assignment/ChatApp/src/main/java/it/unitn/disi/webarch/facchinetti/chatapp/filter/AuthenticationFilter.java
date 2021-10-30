package it.unitn.disi.webarch.facchinetti.chatapp.filter;


import it.unitn.disi.webarch.facchinetti.chatapp.bean.UserSessionBean;
import it.unitn.disi.webarch.facchinetti.chatapp.model.Role;
import it.unitn.disi.webarch.facchinetti.chatapp.model.User;
import it.unitn.disi.webarch.facchinetti.chatapp.util.CsvUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AuthenticationFilter extends FilterImplementation {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        Optional<HttpSession> httpSession = Optional.ofNullable(httpRequest.getSession(false));

        UserSessionBean user = null;
        boolean authorized = false;
        if( httpSession.isPresent() ){
            user = (UserSessionBean)httpSession.get().getAttribute("USER");
            authorized = user != null;
        }

        if( authorized ) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.sendRedirect(this.filterConfig.getServletContext().getContextPath() + "/login");
        }

    }

}
