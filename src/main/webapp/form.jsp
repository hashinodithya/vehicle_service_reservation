<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
   <%@ page import="io.asgardeo.java.saml.sdk.util.SSOAgentConstants" %>
   <%@ page import="io.asgardeo.java.saml.sdk.bean.LoggedInSessionBean" %>
   <%@ page import="io.asgardeo.java.saml.sdk.bean.LoggedInSessionBean.SAML2SSO" %>
   <%@ page import="java.util.Map" %>
   <%@ page import="java.security.SecureRandom" %>
   <%@ page import="java.util.Base64" %>
   <%@ page import="org.apache.commons.text.StringEscapeUtils" %>
   
   
     <%
    // Retrieve the session bean.
    LoggedInSessionBean sessionBean = (LoggedInSessionBean) session.getAttribute(SSOAgentConstants.SESSION_BEAN_NAME);

    // SAML response
    SAML2SSO samlResponse = sessionBean.getSAML2SSO();

    // Autheticated username
    String subjectId = samlResponse.getSubjectId();

    // Authenticated user's attributes
    Map<String, String> saml2SSOAttributes = samlResponse.getSubjectAttributes();
   %>
	<%
   String csrfToken = (String) session.getAttribute("csrfToken");
%>
   
<head>
<meta charset="ISO-8859-1"
      http-equiv="Content-Security-Policy"
      content="default-src 'self'; img-src https://*; child-src 'none'; style-src 'self' https://fonts.googleapis.com; font-src 'self' https://fonts.googleapis.com https://fonts.gstatic.com;">

<title>Reservation Page</title>


<link rel="stylesheet" href="css/style.css">


</head>

<body>
<jsp:include page="navbar.jsp" />
<input type="hidden" id="status" value="<%= request.getAttribute("status") %>">


   <%
   		String username= null;
       if (saml2SSOAttributes != null) {
           for (Map.Entry<String, String> entry : saml2SSOAttributes.entrySet()) {
        	   if ("http://wso2.org/claims/username".equals(entry.getKey())) {
                   username = entry.getValue();
                   
                   session.setAttribute("username", username);
              }
           }
       }
   %>


	<div class="main">

		<!-- Sign up form -->
		<section class="signup">
			<div class="container">
				<div class="signup-content">
					<div class="signup-form">
						<h2 class="form-title">Book a Reservation</h2>
					
						<form method="post" action="reservation" class="register-form"
							id="register-form">
							
							<div class="form-group">
								<label for="date"><i class="zmdi zmdi-lock"></i></label>Select a Date*<input
									type="date" name="date" id="dateField" placeholder="Date" required="required"/>
							</div>
							
							<div class="form-group">
								<label for="time"><i class="zmdi zmdi-lock"></i></label>Select a Time*
								<select id="time" name="time"  class="">
                            		<option value="10:00:00">10 AM</option>
                            		<option value="11:00:00">11 AM</option>
                            		<option value="12:00:00">12 PM</option>
                            
                        		</select>
							</div>
							<div class="form-group">
							<label for="location"><i class="zmdi zmdi-lock"></i></label>Select a service location*
								<select id="location" name="location"  class="">
                            		<option value="Colombo">Colombo</option>
                           			 <option value="Galle">Galle</option>
                            		<option value="Kandy">Kandy</option>
                            
                        		</select>
							</div>
							<div class="form-group">
								<label for="message"><i
									class="zmdi zmdi-account material-icons-name"></i></label>Add additional information* <input
									type="text" name="message" id="name" placeholder="Your Message" required="required" 
									value="<%= StringEscapeUtils.escapeHtml4(request.getParameter("message") != null ? request.getParameter("message") : "") %>"/>
							</div>
							<div class="form-group">
								<label for="vehicle_no"><i
									class="zmdi zmdi-account material-icons-name"></i></label>Enter vehicle number*(CBB 3321) <input
									type="text" name="vehicle_no" id="name" placeholder="Vehicle Number " required="required" 
									value="<%= StringEscapeUtils.escapeHtml4(request.getParameter("vehicle_no") != null ? request.getParameter("vehicle_no") : "") %>"/>
							</div>
							<div class="form-group">
								<label for="mileage"><i
									class="zmdi zmdi-account material-icons-name"></i></label>Enter Current mileage*<input
									type="Number" name="mileage" id="name" placeholder="Current Mileage" required="required" 
									value="<%= StringEscapeUtils.escapeHtml4(request.getParameter("mileage") != null ? request.getParameter("mileage") : "") %>"/>
							</div>
							
							<input type="hidden" name="csrfToken" value="<%= csrfToken %>" />
							
							<div class="form-group form-button">
								<input type="submit" name="signup" id="signup"
									class="form-submit" value="Reserve" />
							</div>
						</form>
					</div>
					<div class="signup-image">
						<figure>
							<img src="https://media.istockphoto.com/id/1316056191/vector/rental-car-service-abstract-concept-vector-illustration.jpg?s=612x612&w=0&k=20&c=7EnKHoj3Qp0ob5UcvXFamWkLDVhud9_YtalTAqAUspk=" alt="sign up image">
						</figure>
						
					</div>
				</div>
			</div>
		</section>


	</div>
	<script>
  document.addEventListener("DOMContentLoaded", function () {
    var dateField = document.getElementById("dateField");
    
   
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, "0");
    var mm = String(today.getMonth() + 1).padStart(2, "0"); 
    var yyyy = today.getFullYear();
    today = yyyy + "-" + mm + "-" + dd;

    
    dateField.setAttribute("min", today);

    
    dateField.addEventListener("input", function () {
      var selectedDate = dateField.value;
      var selectedDay = new Date(selectedDate).getDay(); 

      
      if (selectedDate <= today) {
        alert("Please select a future date.");
        dateField.value = "";
      }

      // Check if the selected date is a Sunday (day 0)
      if (selectedDay === 0) {
        alert("Sundays are not available for reservations.");
        dateField.value = "";
      }
    });
  });
</script>
	<script type = "text/javascript">
		var status = document.getElementById("status").value;
		if(status == "failed"){
			alert("Sorry, something went wrong ");
		}
		else if(status == "invaliddate"){
			alert("Enter date");
		}
		else if(status == "invalidtime"){
			alert("Sorry wrong time input, select a time");
		}
		else if(status == "invalidLocation"){
			alert("Sorry wrong Location , select correct location ");
		}
		else if(status == "invalidvehicle_no"){
			alert("Sorry, check the vehicle number. Only letters and numbers are allowed ");
		}
		else if(status == "invalidmileage"){
			alert("Sorry wrong mileage , select correct mileage ");
		}
		else if(status == "invalidmessage"){
			alert("Sorry wrong message , select correct message ");
		}
		else if(status == "invalidusername"){
			alert("Problem with the user name ");
		}
		
	
	</script>
	
<jsp:include page="footer.jsp" />

</body>
</html>
         

 