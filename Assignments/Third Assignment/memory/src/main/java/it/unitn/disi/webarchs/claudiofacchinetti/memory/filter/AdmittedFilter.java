package it.unitn.disi.webarchs.claudiofacchinetti.memory.filter;

import it.unitn.disi.webarchs.claudiofacchinetti.memory.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdmittedFilter implements Filter {

    private FilterConfig filterConfig;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpRequest.getSession(false);
        if( session == null || session.getAttribute(Constants.SESSION_USER_BEAN) == null ){
            HttpServletResponse httpResp = (HttpServletResponse) servletResponse;
            httpResp.sendRedirect(this.filterConfig.getServletContext().getContextPath() + "/admit");

        } else {
            filterChain.doFilter(servletRequest, servletResponse);

        }
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
        Filter.super.destroy();
    }
}
