package it.unitn.disi.webarch.facchinetti.chatapp.filter;

import it.unitn.disi.webarch.facchinetti.chatapp.model.User;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.util.Map;

public abstract class FilterImplementation implements Filter {

    protected FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
        Filter.super.destroy();
    }

    protected void setUsers(Map<String, User> usersMap){

        this.filterConfig.getServletContext().setAttribute("USERS_MAP", usersMap);

    }

}
