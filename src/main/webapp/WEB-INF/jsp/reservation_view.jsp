<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/assets/css/style.css">
    <title>Bus Reservation</title>
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

    <section id="user-reservation">
        <div class="container">
            <div class="service-form">
                <%
                   HashMap<String, Object> reservation = (HashMap<String, Object>) request.getAttribute("reservationDetails");
                %>
                <div class="header">
                    <h2>Bus Reservation Details</h2>

                    <div class="btn-group">
                        <a href="/reservation/add"><button class="btn">Reserve</button></a>

                        <form action="/reservation/delete" method="POST" class="reservation-actions">
                             <input type="hidden" name="bid" value="<%= reservation != null ? reservation.get("bookingId") : "" %>"/>
                             <input type="hidden" name="token" value="<%= (String) request.getAttribute("csrfToken") %>"/>
                             <button class="btn action-delete" type="submit">Delete</button>
                        </form>
                    </div>
                </div>
                <div class="reservation-info">
                    <h4 class="element title">Booking Id</h4>
                    <h4 class="element"><%= reservation != null && reservation.get("bookingId") != null ? reservation.get("bookingId").toString() : "N/A" %></h4>

                    <h4 class="element">Date</h4>
                    <h4 class="element"><%= reservation != null && reservation.get("date") != null ? reservation.get("date").toString().replace("-", "/") : "N/A" %></h4>

                    <h4 class="element">Time</h4>
                    <h4 class="element"><%= reservation != null && reservation.get("time") != null ? reservation.get("time").toString() : "N/A" %></h4>

                    <h4 class="element">Location</h4>
                    <h4 class="element"><%= reservation != null && reservation.get("location") != null ? reservation.get("location").toString() : "N/A" %></h4>

                    <h4 class="element">Bus No</h4>
                    <h4 class="element"><%= reservation != null && reservation.get("vehicleNo") != null ? reservation.get("vehicleNo").toString() : "N/A" %></h4>

                    <h4 class="element">Mileage</h4>
                    <h4 class="element"><%= reservation != null && reservation.get("mileage") != null ? reservation.get("mileage").toString() : "N/A" %></h4>

                    <h4 class="element">Message</h4>
                    <h4 class="element"><%= reservation != null && reservation.get("message") != null ? reservation.get("message").toString() : "N/A" %></h4>
                </div>
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
