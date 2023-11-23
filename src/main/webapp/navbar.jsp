<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.Set" %>

<%
   String csrfToken = (String) session.getAttribute("csrfToken");

   // Check if the "navbarCssImported" attribute is not set
   if (pageContext.getAttribute("navbarCssImported") == null) {
       // Import the navbar.css stylesheet
       pageContext.setAttribute("navbarCssImported", true);
%>
       <link rel="stylesheet" href="css/navbar.css">
<%
   }
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
           
  		<li>
    <form action="logoutServlet" method="post" class="logout-form">
        <input type="hidden" name="csrfToken" value="<%= csrfToken %>">
        <button class="form-submit" type="submit">Logout</button>
    </form>
</li>

          </div>
        </div>
      </div>
</nav>
