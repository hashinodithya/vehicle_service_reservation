package vehicleService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class cancelReservationServlet
 */
@WebServlet("/cancelReservation")
public class cancelReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String booking_id = request.getParameter("booking_id");
		
		 Connection con = null;
	        PreparedStatement pst = null;
	        
	        String dbUrl = databaseConfig.getDbUrl();
	        String dbUsername = databaseConfig.getDbUsername();
	        String dbPassword = databaseConfig.getDbPassword();
	        
	        RequestDispatcher dispatcher = null;
	        HttpSession session = request.getSession();
	        
	        String csrfTokenFromRequest = request.getParameter("csrfToken");
	        String csrfTokenFromSession = (String) session.getAttribute("csrfToken");
	        
	        System.out.println("CSRF Token from Request: " + csrfTokenFromRequest);
	        System.out.println("CSRF Token from Session: " + csrfTokenFromSession);
	        //HTTPOnly flag for the csrfToken cookie
	        Cookie csrfTokenCookie = new Cookie("csrfToken", csrfTokenFromSession);
	        csrfTokenCookie.setHttpOnly(true);
	        csrfTokenCookie.setSecure(true);
	        response.addCookie(csrfTokenCookie);
	        

	        if (csrfTokenFromRequest == null || !csrfTokenFromRequest.equals(csrfTokenFromSession)) {
	           
	           request.setAttribute("status", "csrfError");
	           dispatcher = request.getRequestDispatcher("error.jsp");
	           dispatcher.forward(request, response);
	           return;
	        }
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

	            String sql = "DELETE FROM vehicle_service WHERE booking_id=?";
	            pst = con.prepareStatement(sql);
	            pst.setString(1, booking_id);

	            int rowCount = pst.executeUpdate();

	            if (rowCount > 0) {
	                
	                request.setAttribute("cancellationMessage", "success.");
	            } else {
	                
	                request.setAttribute("cancellationMessage", "failed.");
	            }

	            con.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	            
	            request.setAttribute("cancellationMessage", "An error occurred while canceling the reservation.");
	        }

	        response.sendRedirect("home.jsp");
	}

}
