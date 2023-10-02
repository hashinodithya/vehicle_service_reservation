<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
   <%@ page import="io.asgardeo.java.saml.sdk.util.SSOAgentConstants" %>
   <%@ page import="io.asgardeo.java.saml.sdk.bean.LoggedInSessionBean" %>
   <%@ page import="io.asgardeo.java.saml.sdk.bean.LoggedInSessionBean.SAML2SSO" %>
   <%@ page import="java.util.Map" %>
   
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
   
<head>
<meta charset="ISO-8859-1">
<title>Home Page</title>


<link rel="stylesheet" href="css/style.css">


</head>

<body>
<jsp:include page="navbar.jsp" />



   <%
   		String username= null;
       if (saml2SSOAttributes != null) {
           for (Map.Entry<String, String> entry : saml2SSOAttributes.entrySet()) {
        	   if ("http://wso2.org/claims/username".equals(entry.getKey())) {
                   username = entry.getValue();
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
						<h2 class="form-title">Reserve a Vehicle</h2>
					
						<form method="post" action="reservation" class="register-form"
							id="register-form">
							<div class="form-group">
								<label for="user_name"><i
									class="zmdi zmdi-account material-icons-name"></i></label> <input
									type="email" name="user_name" id="name" placeholder=<%=username %> value=<%=username %> required="required" />
							</div>
							
							<div class="form-group">
								<label for="Date"><i class="zmdi zmdi-lock"></i></label> <input
									type="date" name="date" id="date" placeholder="Date" required="required"/>
							</div>
							<div class="form-group">
							
								<select id="time" name="time"  class="">
                            <option value="10">10 AM</option>
                            <option value="11">11 AM</option>
                            <option value="12">12 PM</option>
                            
                        </select>
							</div>
							<div class="form-group">
								<select id="location" name="location"  class="">
                            <option value="Colombo">Colombo</option>
                            <option value="Galle">Galle</option>
                            <option value="Kandy">Kandy</option>
                            
                        </select>
							</div>
							<div class="form-group">
								<label for="message"><i
									class="zmdi zmdi-account material-icons-name"></i></label> <input
									type="text" name="message" id="name" placeholder="Your Message" required="required" />
							</div>
							<div class="form-group">
								<label for="vehicle_no"><i
									class="zmdi zmdi-account material-icons-name"></i></label> <input
									type="text" name="vehicle_no" id="name" placeholder="Vehicle Number " required="required" />
							</div>
							<div class="form-group">
								<label for="mileage"><i
									class="zmdi zmdi-account material-icons-name"></i></label> <input
									type="Number" name="mileage" id="name" placeholder="Current Mileage" required="required" />
							</div>
							
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
	


</body>
</html>
         

 