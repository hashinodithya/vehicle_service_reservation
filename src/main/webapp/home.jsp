<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1"
      http-equiv="Content-Security-Policy"
      content="default-src 'self'; img-src https://*; child-src 'none'; style-src 'self' https://fonts.googleapis.com; font-src 'self' https://fonts.googleapis.com https://fonts.gstatic.com;">

<title>Home Page</title>
<link rel="stylesheet" href="css/home.css">
</head>
   <%@ page import="io.asgardeo.java.saml.sdk.util.SSOAgentConstants" %>
   <%@ page import="io.asgardeo.java.saml.sdk.bean.LoggedInSessionBean" %>
   <%@ page import="io.asgardeo.java.saml.sdk.bean.LoggedInSessionBean.SAML2SSO" %>
   <%@ page import="java.util.Map" %>
   <%@ page import="java.security.SecureRandom" %>
   <%@ page import="java.util.Base64" %>
   
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
   
      
   <%
   // Generate CSRF token
   byte[] token = new byte[32];
   new SecureRandom().nextBytes(token);
   String csrfToken = Base64.getEncoder().encodeToString(token);

   // Store CSRF token in session
   session.setAttribute("csrfToken", csrfToken);
   %>
<body>
<jsp:include page="navbar.jsp" />
	
	<div class="main">
	<h1> Home page</h1>
	<div>
		<img class="image" src="https://yi-files.s3.eu-west-1.amazonaws.com/products/788000/788969/1344208-full.jpg">
	 
  		<button class="button-link"><a href="form.jsp" > Book a reservation</a></button>
  		<button class="button-link"><a href="profile.jsp" > User Profile</a></button>
  		
  		<form action="logoutServlet" method="post" class="logout-form">
        <input type="hidden" name="csrfToken" value="<%= csrfToken %>">
        <button class="form-submit" type="submit">Logout</button>
        </form>
  		<form  action="viewReservation" method="post" >
      		<div>
      			<input type="hidden" name="csrfToken" value="<%= csrfToken %>" />
      			<input type="hidden" name="userName" value="<%= username %>" />
         		<input class="form-submit" type="submit" value="View Reservation" />
      		</div>
      
  		</form>
  	</div>
   
  </div>


<jsp:include page="footer.jsp" />
</body>
</html>