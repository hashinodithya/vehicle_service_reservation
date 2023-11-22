<%
   String csrfToken = (String) session.getAttribute("csrfToken");
%>
<nav>
      <div class="navbar">
        <div class="container nav-container">
            <input class="checkbox" type="checkbox" name="" id="" />
            <div class="hamburger-lines">
              <span class="line line1"></span>
              <span class="line line2"></span>
              <span class="line line3"></span>
            </div>  
          <div class="logo">
            <h1>Service Reservation</h1>
          </div>
          <div class="menu-items">
			<li><a href="home.jsp">Home</a></li>
            <li><a href="form.jsp">Book reservation </a></li>
            <li><a href="profile.jsp">Profile</a></li>
            <li><a href="logout?SAML2.HTTPBinding=HTTP-POST&csrfToken=<%= csrfToken %>">Logout</a></li>
          </div>
        </div>
      </div>
</nav>
<link rel="stylesheet" href="css/navbar.css">

