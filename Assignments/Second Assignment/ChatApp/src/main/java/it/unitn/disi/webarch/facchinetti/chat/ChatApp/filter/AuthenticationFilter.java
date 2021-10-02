package it.unitn.disi.webarch.facchinetti.chat.ChatApp.filter;


import it.unitn.disi.webarch.facchinetti.chat.ChatApp.model.Role;
import it.unitn.disi.webarch.facchinetti.chat.ChatApp.model.User;
import it.unitn.disi.webarch.facchinetti.chat.ChatApp.util.CsvUtils;

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

    private final static String PARAMETER_USERS_FILE = "USER_FILE";
    private final static String PARAMETER_ADMIN_PASSWORD = "ADMIN_PASSWORD";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        Object o = this.getUsers();
        if(!(o instanceof Map<?, ?>)){
            Map<String, User> usersMap = null;
            try {
                usersMap = this.loadUsers();
            } catch (IOException e) {
                e.printStackTrace();
                usersMap = new HashMap<>();
            }
            this.setUsers(usersMap);
        }

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        Optional<HttpSession> httpSession = Optional.ofNullable(httpRequest.getSession(false));

        String username = null;
        boolean authorized = false;
        if( httpSession.isPresent() ){
            username = (String)httpSession.get().getAttribute("USER");
            authorized = username != null;
        }

        if( authorized ) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.sendRedirect(this.filterConfig.getServletContext().getContextPath() + "/index.jsp");
        }

    }

    private Map<String, User> loadUsers() throws IOException {

        Map<String, User> users = new HashMap<>();
        // this.filterConfig.getServletContext().log(this.filterConfig.getInitParameter(PARAMETER_USERS_FILE));
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(this.filterConfig.getInitParameter(PARAMETER_USERS_FILE));
        Set<User> fileUsers = CsvUtils.getUsers(is, true);
        String adminPassword = this.filterConfig.getInitParameter(PARAMETER_ADMIN_PASSWORD).trim();
        fileUsers.add(new User("admin", adminPassword, Role.ADMIN));

        fileUsers.forEach(user -> users.put(user.getUsername(), user));
        return users;

    }

}
