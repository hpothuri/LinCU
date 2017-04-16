package com.linCu.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthFilter implements Filter {
    private FilterConfig _filterConfig = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        _filterConfig = filterConfig;
    }

    public void destroy() {
        _filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                                     ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response; 
        
        String requestURL = req.getRequestURI();
        if((requestURL != null) && (requestURL.endsWith("index"))){
            HttpSession session = req.getSession(false); 
            
            if(session == null){
                res.sendRedirect(req.getContextPath()+"/faces/LoginPage");
                return;
            }else{
                if(session.getAttribute("user") == null){
                    res.sendRedirect(req.getContextPath()+"/faces/LoginPage");
                    return; 
                }
            }
            
        }
        chain.doFilter(request, response);
    }
}
