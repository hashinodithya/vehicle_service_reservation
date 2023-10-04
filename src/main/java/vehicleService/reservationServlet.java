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
		
		if(date==null || date.equals("")) {
			request.setAttribute("status", "invaliddate");
			dispatcher = request.getRequestDispatcher("home.jsp");
			dispatcher.forward(request, response);
		}
		else if(!time.equals("10:00:00") && !time.equals("11:00:00") && !time.equals("12:00:00")) {
			request.setAttribute("status", "invalidtime");
			dispatcher = request.getRequestDispatcher("home.jsp");
			dispatcher.forward(request, response);
		}
		else if(!location.equals("Colombo") && !location.equals("Kandy") && !location.equals("Galle")) {
			request.setAttribute("status", "invalidLocation");
			dispatcher = request.getRequestDispatcher("home.jsp");
			dispatcher.forward(request, response);
		}
		else if(vehicle_no==null || vehicle_no.equals("")) {
			request.setAttribute("status", "invalidvehicle_no");
			dispatcher = request.getRequestDispatcher("home.jsp");
			dispatcher.forward(request, response);
		}
		else if(mileage==null || mileage.equals("")) {
			request.setAttribute("status", "invalidmileage");
			dispatcher = request.getRequestDispatcher("home.jsp");
			dispatcher.forward(request, response);
		}
//		else if(umobile.length()==10) {
//			request.setAttribute("status", "invalidUmobileLength");
//			dispatcher = request.getRequestDispatcher("registration.jsp");
//			dispatcher.forward(request, response);
//		}
		else if (username==null || username.equals("")) {
			request.setAttribute("status", "invalidusername");
			dispatcher = request.getRequestDispatcher("home.jsp");
			dispatcher.forward(request, response);
		}
		else {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
			PreparedStatement pst = con.prepareStatement("insert into vehicle_service(date,time,location,vehicle_no,mileage,message,username) values(?,?,?,?,?,?,?) ");
			pst.setString(1, date);
			pst.setString(2, time);
			pst.setString(3, location);
			pst.setString(4, vehicle_no);
			pst.setString(5, mileage);
			pst.setString(6, message);
			pst.setString(7, username);
			
			int rowCount = pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("home.jsp");
			
			if(rowCount >0) {
				request.setAttribute("status", "success");
			}else {
				request.setAttribute("status", "failed");
			}
			dispatcher.forward(request, response);
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		}
	}

}
