package it.unitn.disi.webarch.facchinetti.chat.ChatApp.filter;

import it.unitn.disi.webarch.facchinetti.chat.ChatApp.model.User;

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

    protected Map<String, User> getUsers(){
        Map<String, User> userMap = (Map<String, User>) this.filterConfig.getServletContext().getAttribute("USERS_MAP");
        return userMap;
    }

    protected void setUsers(Map<String, User> usersMap){

        this.filterConfig.getServletContext().setAttribute("USERS_MAP", usersMap);

    }

}
