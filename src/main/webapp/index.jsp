<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Vehicle Reservation</title>
<link rel="stylesheet" href="css/style.css">
</head>
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
<body>
<jsp:include page="navbar.jsp" />
	<h1> Index page</h1>
	<div class="main">
	    <form  action="samlsso?SAML2.HTTPBinding=HTTP-POST" method="post" >
      		<div>
         		 <input class="form-submit" type="submit" value="log in" />
      		</div>
      
  		</form>
  		<button class="form-submit"><a href="home.jsp" > Book a car</a></button>
  		<button class="form-submit"><a href="profile.jsp" > User Profile</a></button>
  		
  		
  		<form  action="viewReservation" method="post" >
      		<div>
      		<input  type="email" name="username" value="<%=username%>"/>
         		 <input class="form-submit" type="submit" value="View Reservation" />
      		</div>
      
  		</form>
  	
   
  </div>
</body>
</html>