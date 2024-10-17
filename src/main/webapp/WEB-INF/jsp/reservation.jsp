<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.time.LocalTime" %>
<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/assets/css/style.css">
    <title>Dashboard</title>
</head>

<body>
    <nav>
        <div class="container">
            <div class="brand-logo">
                NCG
            </div>

            <div class="nav-items">
                <ul class="nav-items-list">
                    <a href="/reservation">
                        <li class="list-item">Bus Reservation</li>
                    </a>
                    <a href="/profile">
                        <li class="list-item">Profile</li>
                    </a>
                    <a href="/logout">
                        <li class="list-item">Logout</li>
                    </a>
                </ul>
            </div>
        </div>
    </nav>

    <section id="user-dashboard">
        <div class="container">
            <div class="reservations">
                <div class="header">
                    <h1>Bus Reservations</h1>
                    <a href="/reservation/add"><button class="btn">ADD</button></a>
                </div>
                <table>
                    <thead>
                        <tr>
                            <th>Booking ID</th>
                            <th>Date</th>
                            <th>Time</th>
                            <th>Location</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                       <%
                         List<HashMap<String, Object>> reservationsList = (List<HashMap<String, Object>>) request.getAttribute("reservationsDetails");
                         if (reservationsList != null) {
                             for (HashMap<String, Object> reservation : reservationsList) {
                       %>
                       <tr>
                           <td><%= reservation.get("bookingId") != null ? reservation.get("bookingId").toString() : "N/A" %></td>
                           <td><%= reservation.get("date") != null ? reservation.get("date").toString().replace("-", "/") : "N/A" %></td>
                           <td><%= reservation.get("time") != null ? reservation.get("time").toString() : "N/A" %></td>
                           <td><%= reservation.get("location") != null ? reservation.get("location").toString() : "N/A" %></td>
                           <td class="actions">
                               <form action="/reservation/view" method="POST" style="display:inline;">
                                  <input type="hidden" name="bid" value="<%= reservation.get("bookingId") %>"/>
                                  <input type="hidden" name="token" value="<%= (String) request.getAttribute("csrfToken") %>"/>
                                  <button class="btn action-view" type="submit">View</button>
                               </form>
                               <form action="/reservation/delete" method="POST" style="display:inline;">
                                 <input type="hidden" name="bid" value="<%= reservation.get("bookingId") %>"/>
                                 <input type="hidden" name="token" value="<%= (String) request.getAttribute("csrfToken") %>"/>
                                 <button class="btn action-delete" type="submit">Delete</button>
                               </form>
                            </td>
                        </tr>
                       <%
                             }
                         } else {
                       %>
                        <tr>
                            <td colspan="5" style="text-align:center;">No reservations found.</td>
                        </tr>
                       <%
                         }
                       %>
                    </tbody>
                </table>
            </div>
        </div>
    </section>

    <footer>
        <div class="content">
            <% 
                java.util.Calendar cal = java.util.Calendar.getInstance();
                int year = cal.get(java.util.Calendar.YEAR); 
            %>
            &copy; <%= year %> Vipun
        </div>
    </footer>
</body>

</html>
