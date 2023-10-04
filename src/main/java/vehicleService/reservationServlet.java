package vehicleService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Servlet implementation class reservationServlet
 */
@WebServlet("/reservation")
public class reservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String date = request.getParameter("date");
		String time = request.getParameter("time");
		String location = request.getParameter("location");
		String vehicle_no = request.getParameter("vehicle_no");
		String mileage = request.getParameter("mileage");	
		String message = request.getParameter("message");
		String username = request.getParameter("user_name");

		RequestDispatcher dispatcher = null;
		Connection con = null;
		
		String dbUrl = databaseConfig.getDbUrl();
        String dbUsername = databaseConfig.getDbUsername();
        String dbPassword = databaseConfig.getDbPassword();
		
        if (date == null || date.equals("")) {
            request.setAttribute("status", "invaliddate");
            dispatcher = request.getRequestDispatcher("home.jsp");
            dispatcher.forward(request, response);
        } else if (!time.equals("10:00:00") && !time.equals("11:00:00") && !time.equals("12:00:00")) {
            request.setAttribute("status", "invalidtime");
            dispatcher = request.getRequestDispatcher("home.jsp");
            dispatcher.forward(request, response);
        } else if (!location.equals("Colombo") && !location.equals("Kandy") && !location.equals("Galle")) {
            request.setAttribute("status", "invalidLocation");
            dispatcher = request.getRequestDispatcher("home.jsp");
            dispatcher.forward(request, response);
        } else if (vehicle_no == null || !vehicle_no.matches("^[A-Za-z0-9 ]*$")) {
            request.setAttribute("status", "invalidvehicle_no");
            dispatcher = request.getRequestDispatcher("home.jsp");
            dispatcher.forward(request, response);
        } else if (mileage == null || mileage.equals("")) {
            request.setAttribute("status", "invalidmileage");
            dispatcher = request.getRequestDispatcher("home.jsp");
            dispatcher.forward(request, response);
        } else if (username == null || username.equals("")) {
            request.setAttribute("status", "invalidusername");
            dispatcher = request.getRequestDispatcher("home.jsp");
            dispatcher.forward(request, response);
        }  else if (message == null || !message.matches("^[A-Za-z0-9 ,.@()]*$")) {
            // Check if message contains only alphanumeric characters, spaces, commas, periods, and parentheses
            request.setAttribute("status", "invalidmessage");
            dispatcher = request.getRequestDispatcher("home.jsp");
            dispatcher.forward(request, response); 
        }  else if (!isValidFutureDate(date)) {
            request.setAttribute("status", "invaliddate");
            dispatcher = request.getRequestDispatcher("home.jsp");
            dispatcher.forward(request, response);
            return;
        }
        else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
                
                
                try {
                    int mileageValue = Integer.parseInt(mileage);
                    if (mileageValue <= 0) {
                        request.setAttribute("status", "invalidmileage");
                        dispatcher = request.getRequestDispatcher("home.jsp");
                        dispatcher.forward(request, response);
                        return; 
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("status", "invalidmileage");
                    dispatcher = request.getRequestDispatcher("home.jsp");
                    dispatcher.forward(request, response);
                    return; 
                }
                
               
                PreparedStatement pst = con.prepareStatement("INSERT INTO vehicle_service(date, time, location, vehicle_no, mileage, message, username) VALUES (?, ?, ?, ?, ?, ?, ?)");
                pst.setString(1, date);
                pst.setString(2, time);
                pst.setString(3, location);
                pst.setString(4, vehicle_no);
                pst.setString(5, mileage);
                pst.setString(6, message);
                pst.setString(7, username);


                int rowCount = pst.executeUpdate();
                dispatcher = request.getRequestDispatcher("index.jsp");

                if (rowCount > 0) {
                    request.setAttribute("status", "success");
                } else {
                    request.setAttribute("status", "failed");
                }
                dispatcher.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

	}
	private boolean isValidFutureDate(String dateStr) {
	    try {
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = sdf.parse(dateStr);
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);

	        
	        if (date.before(new Date())) {
	            return false;
	        }

	        
	        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
	            return false;
	        }

	        return true;
	    } catch (ParseException e) {
	        return false; 
	    }
	}

}
