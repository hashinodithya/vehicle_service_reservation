package vehicleService;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class CSPFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.setHeader("Content-Security-Policy", 
        		"default-src 'self' ; style-src 'self' https://fonts.googleapis.com; font-src 'self' https://fonts.googleapi.com; img-src https://*;");

        chain.doFilter(request, response);
    }

}
