package it.unitn.disi.webarchs.claudiofacchinetti.memory.filter;

import it.unitn.disi.webarchs.claudiofacchinetti.memory.Constants;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.bean.UserBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GameFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
        HttpSession session = httpReq.getSession(false);
        UserBean ub = (UserBean) session.getAttribute(Constants.SESSION_USER_BEAN);
        if( ub.getGameId() == null ){
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.sendRedirect(this.filterConfig.getServletContext().getContextPath() + "/user");
        }
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
        Filter.super.destroy();
    }
}
