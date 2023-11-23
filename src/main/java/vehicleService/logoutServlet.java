package vehicleService;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class logoutServlet
 */
@WebServlet("/logoutServlet")
public class logoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String csrfTokenFromRequest = request.getParameter("csrfToken");
		   String csrfTokenFromSession = (String) request.getSession().getAttribute("csrfToken");
		   //HTTPOnly flag for the csrfToken cookie
	        Cookie csrfTokenCookie = new Cookie("csrfToken", csrfTokenFromSession);
	        csrfTokenCookie.setHttpOnly(true);
	        csrfTokenCookie.setSecure(true);
	        response.addCookie(csrfTokenCookie);
	        
	        System.out.println("CSRF Token from Request: " + csrfTokenFromRequest);
	        System.out.println("CSRF Token from Session: " + csrfTokenFromSession);
	        
		   if (csrfTokenFromRequest != null && csrfTokenFromRequest.equals(csrfTokenFromSession)) {
		     
		      response.sendRedirect("index.jsp");
		   } else {
		      
		      response.sendRedirect("error.jsp");
		   }
	}

	

}
