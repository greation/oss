package com.ekeyfund.oss.core.intercept;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * HttpServletRequest替换过滤器
 */
public class HttpServletRequestReplacedFilter implements Filter {
  
    @Override  
    public void init(FilterConfig filterConfig) throws ServletException {
        //Do nothing  
    }  
  
    @Override  
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        ServletRequest requestWrapper = null;
        if(request instanceof HttpServletRequest) {
            requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);  
        }  
        if(null == requestWrapper) {  
            chain.doFilter(request, response);  
        } else {  
            chain.doFilter(requestWrapper, response);  
        }  
          
    }  
  
    @Override  
    public void destroy() {  
        //Do nothing  
    }  
  
}  