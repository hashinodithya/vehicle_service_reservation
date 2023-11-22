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
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class reservationDetailsServlet
 */
@WebServlet("/viewReservation")
public class reservationDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String username = request.getParameter("username"); 
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

        
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;
        RequestDispatcher dispatcher = null;
        
        String dbUrl = databaseConfig.getDbUrl();
        String dbUsername = databaseConfig.getDbUsername();
        String dbPassword = databaseConfig.getDbPassword();

        String csrfTokenFromRequest = request.getParameter("csrfToken");
        String csrfTokenFromSession = (String) session.getAttribute("csrfToken");
        
        System.out.println("CSRF Token from Request: " + csrfTokenFromRequest);
        System.out.println("CSRF Token from Session: " + csrfTokenFromSession);


        if (csrfTokenFromRequest == null || !csrfTokenFromRequest.equals(csrfTokenFromSession)) {
           
           request.setAttribute("status", "csrfError");
           dispatcher = request.getRequestDispatcher("error.jsp");
           dispatcher.forward(request, response);
           return;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            
            
            String sql = "SELECT booking_id, date, time, location, vehicle_no, mileage, message FROM vehicle_service WHERE username=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            
            resultSet = pst.executeQuery();
            
            
            List<Map<String, String>> reservationList = new ArrayList<>();
            
            while (resultSet.next()) {
                Map<String, String> reservation = new HashMap<>();
                reservation.put("booking_id", resultSet.getString("booking_id"));
                reservation.put("date", resultSet.getString("date"));
                reservation.put("time", resultSet.getString("time"));
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
