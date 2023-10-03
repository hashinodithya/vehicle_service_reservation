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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	        
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

	            String sql = "DELETE FROM vehicle_service WHERE booking_id=?";
	            pst = con.prepareStatement(sql);
	            pst.setString(1, booking_id);

	            int rowCount = pst.executeUpdate();

	            if (rowCount > 0) {
	                
	                request.setAttribute("cancellationMessage", "Reservation canceled successfully.");
	            } else {
	                
	                request.setAttribute("cancellationMessage", "Failed to cancel reservation.");
	            }

	            con.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	            
	            request.setAttribute("cancellationMessage", "An error occurred while canceling the reservation.");
	        }

	        response.sendRedirect("index.jsp");
	}

}
