<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
                <form method="post" action="/reservation/add">
                    <label for="date">Date</label>
                    <input type="date" name="date" id="date-picker" required />

                    <label for="time">Preferred Time</label>
                    <select name="time" id="time" required>
                        <option value="10">10 AM</option>
                        <option value="11">11 AM</option>
                        <option value="12">12 PM</option>
                    </select>

                    <label for="location">Preferred Location</label>
                    <select name="location" id="location" required>
                        <option value="Colombo">Colombo</option>
                        <option value="Gampaha">Gampaha</option>
                        <option value="Kalutara">Kalutara</option>
                        <!-- (additional locations omitted for brevity) -->
                        <option value="Kegalle">Kegalle</option>
                    </select>

                    <label for="vehicleno">Vehicle Registration Number</label>
                    <input type="text" name="vehicleno" id="vehicleno" placeholder="e.g., ABC-1234" pattern="[A-Z]{1,3}-\d{3,4}" required title="Format: ABC-1234" />

                    <label for="mileage">Current Mileage</label>
                    <input type="number" name="mileage" id="mileage" min="0" placeholder="Enter current mileage" required />

                    <label for="message">Message</label>
                    <textarea name="message" id="message" placeholder="Additional information" rows="4" cols="50"></textarea>

                    <input type="hidden" name="token" value="<%= (String) request.getAttribute("csrfToken") %>"/>

                    <button type="submit">Reserve</button>
                    <button type="reset">Clear</button>
                </form>
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

    <script src="/assets/js/main.js"></script>
</body>

</html>
