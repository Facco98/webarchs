package it.unitn.disi.webarchs.claudiofacchinetti.memory.filter;

import it.unitn.disi.webarchs.claudiofacchinetti.memory.Constants;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.bean.UserBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class NotAdmittedFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
        HttpSession httpSession = httpReq.getSession(false);
        UserBean ub = null;
        if( httpSession != null && (ub = (UserBean) httpSession.getAttribute(Constants.SESSION_USER_BEAN)) != null ){
            HttpServletResponse resp = (HttpServletResponse) servletResponse;
            resp.sendRedirect(this.filterConfig.getServletContext().getContextPath() + "/user/");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
