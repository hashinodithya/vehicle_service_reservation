<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User profile</title>
<link rel="stylesheet" href="css/resDetails.css">
</head>

<body>
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
                        for (Map<String, String> reservation : reservationList) {
                %>
               
                            <tr>
                                <td><%= reservation.get("c_date") %></td>
                                <td><%= reservation.get("c_time") %></td>
                                <td><%= reservation.get("location") %></td>
                                <td><%= reservation.get("vehicle_no") %></td>
                                <td><%= reservation.get("mileage") %></td>
                                <td><%= reservation.get("message") %></td>
                                <td><button>Cancel</button></td>
                            </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>