<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1"
http-equiv="Content-Security-Policy"
  content="default-src 'self'; img-src https://*; child-src 'none';style-src 'self' https://fonts.googleapis.com; font-src 'self' https://fonts.googleapi.com;" />
<title>Vehicle Reservation</title>
<link rel="stylesheet" href="css/home.css">
</head>

<body>
<jsp:include page="navbar.jsp" />
	<h1> Index page</h1>
	<div class="main">
	
		<img class="image" src="https://kit8.net/wp-content/uploads/edd/2022/01/annual_car_maintenance_preview.jpg">
	 
	    <form  action="samlsso?SAML2.HTTPBinding=HTTP-POST" method="post" >
      		<div>
         		 <input  class="form-submit" type="submit" value="log in" />
      		</div>
      
  		</form>
  </div>
  <jsp:include page="footer.jsp" />
</body>
</html>