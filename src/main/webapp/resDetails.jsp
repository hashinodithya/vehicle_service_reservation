<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.util.Base64" %>

<%
   String csrfToken = (String) session.getAttribute("csrfToken");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1"
      http-equiv="Content-Security-Policy"
      content="default-src 'self'; img-src https://*; child-src 'none'; style-src 'self' https://fonts.googleapis.com; font-src 'self' https://fonts.googleapis.com https://fonts.gstatic.com;">
<title>Reservation Details</title>
<link rel="stylesheet" href="css/resDetails.css">
</head>

<body>
<input type="hidden" id="status" value="<%= request.getAttribute("cancellationMessage") %>">
<jsp:include page="navbar.jsp" />
	<h2>Reservation Table</h2>
    <div class="table-wrapper">
        <table class="fl-table">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Time</th>
                    <th>Location</th>
                    <th>Vehicle Number</th>
                    <th>Current Mileage</th>
                    <th>Message</th>
                    <th>Cancel Reservation</th>
                </tr>
            </thead>
            <tbody>
<%
List<Map<String, String>> reservationList = (List<Map<String, String>>) request.getAttribute("reservationList");
if (reservationList != null) {
    
    Collections.sort(reservationList, new Comparator<Map<String, String>>() {
        @Override
        public int compare(Map<String, String> r1, Map<String, String> r2) {
           
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date1 = dateFormat.parse(r1.get("date"));
                Date date2 = dateFormat.parse(r2.get("date"));
                return date2.compareTo(date1);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        }
    });

    for (Map<String, String> reservation : reservationList) {
        String reservationDateStr = reservation.get("date");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date reservationDate = dateFormat.parse(reservationDateStr);
        Date currentDate = new Date();
        
       
        boolean isFutureReservation = reservationDate.after(currentDate);
%>
               
        <tr>
            <td><%= reservation.get("date") %></td>
            <td><%= reservation.get("time") %></td>
            <td><%= reservation.get("location") %></td>
            <td><%= reservation.get("vehicle_no") %></td>
            <td><%= reservation.get("mileage") %></td>
            <td><%= reservation.get("message") %></td>
            <td>
                <%
                if (isFutureReservation) {
                %>
                <form id="cancel-form-<%= reservation.get("booking_id") %>" action="cancelReservation" method="post">
                    <input type="hidden" name="booking_id" value="<%= reservation.get("booking_id") %>"/>
                    <input type="hidden" name="csrfToken" value="<%= csrfToken %>" />
                    <Button form="cancel-form-<%= reservation.get("booking_id") %>" type="submit">Cancel</Button>
                </form>
                <%
                } else {
                %>
                <span>Cannot cancel</span>
                <%
                }
                %>
            </td>
        </tr>
<%
    }
}
%>
            </tbody>
        </table>
    </div>
</script>
	<script type = "text/javascript">
		var status = document.getElementById("cancellationMessage").value;
		if(status == "success"){
			alert("Reservation canceled successfully ");
		}
		else if(status == "failed"){
			alert("Failed to cancel the reservation");
		}
		

		
	
	</script>
	<jsp:include page="footer.jsp" />
</body>
</html>