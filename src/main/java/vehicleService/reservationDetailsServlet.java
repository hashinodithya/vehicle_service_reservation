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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class reservationDetailsServlet
 */
@WebServlet("/viewReservation")
public class reservationDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username"); // Change "user_name" to the actual parameter name
        
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/isec_assessment2?useSSL=false", "root", "1234");
            
            // Modify the SQL query to retrieve reservations for the given username
            String sql = "SELECT c_date, c_time, location, vehicle_no, mileage, message FROM vehicle_service WHERE username=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            
            resultSet = pst.executeQuery();
            
            // Store the result set in a list to pass it to the JSP
            List<Map<String, String>> reservationList = new ArrayList<>();
            
            while (resultSet.next()) {
                Map<String, String> reservation = new HashMap<>();
                reservation.put("c_date", resultSet.getString("c_date"));
                reservation.put("c_time", resultSet.getString("c_time"));
                reservation.put("location", resultSet.getString("location"));
                reservation.put("vehicle_no", resultSet.getString("vehicle_no"));
                reservation.put("mileage", resultSet.getString("mileage"));
                reservation.put("message", resultSet.getString("message"));
                reservationList.add(reservation);
            }
            
            request.setAttribute("reservationList", reservationList);
            request.getRequestDispatcher("resDetails.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	
	}


}
