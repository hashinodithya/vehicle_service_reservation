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
		String c_date = request.getParameter("date");
		String c_time = request.getParameter("time");
		String location = request.getParameter("location");
		String vehicle_no = request.getParameter("vehicle_no");
		String mileage = request.getParameter("mileage");	
		String message = request.getParameter("message");
		String username = request.getParameter("user_name");

		RequestDispatcher dispatcher = null;
		Connection con = null;
		
//		if(uname==null || uname.equals("")) {
//			request.setAttribute("status", "invalidName");
//			dispatcher = request.getRequestDispatcher("registration.jsp");
//			dispatcher.forward(request, response);
//		}
//		if(uemail==null || uemail.equals("")) {
//			request.setAttribute("status", "invalidEmail");
//			dispatcher = request.getRequestDispatcher("registration.jsp");
//			dispatcher.forward(request, response);
//		}
//		if(upwd==null || upwd.equals("")) {
//			request.setAttribute("status", "invalidUpwd");
//			dispatcher = request.getRequestDispatcher("registration.jsp");
//			dispatcher.forward(request, response);
//		}else if(!upwd.equals(Reupwd)){
//			request.setAttribute("status", "invalidComfpwd");
//			dispatcher = request.getRequestDispatcher("registration.jsp");
//			dispatcher.forward(request, response);
//		}
//		if(umobile==null || umobile.equals("")) {
//			request.setAttribute("status", "invalidUmobile");
//			dispatcher = request.getRequestDispatcher("registration.jsp");
//			dispatcher.forward(request, response);
//		}else if(umobile.length()==10) {
//			request.setAttribute("status", "invalidUmobileLength");
//			dispatcher = request.getRequestDispatcher("registration.jsp");
//			dispatcher.forward(request, response);
//		}
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3307/isec_assessment2?useSSL=false","root","1234");
			PreparedStatement pst = con.prepareStatement("insert into vehicle_service(c_date,c_time,location,vehicle_no,mileage,message,username) values(?,?,?,?,?,?,?) ");
			pst.setString(1, c_date);
			pst.setString(2, c_time);
			pst.setString(3, location);
			pst.setString(4, vehicle_no);
			pst.setString(5, mileage);
			pst.setString(6, message);
			pst.setString(7, username);
			
			int rowCount = pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("index.jsp");
			
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
