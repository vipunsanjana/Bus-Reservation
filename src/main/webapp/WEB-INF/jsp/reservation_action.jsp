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

    <section id="msg-container">
        <div class="container">
            <div class="msg-body">
                <div class="title">
                    <h3>
                        <%
                        String msg = (String) request.getAttribute("msg");
                        if (msg != null) {
                            if (msg.equals("empty")) { %>
                                You have not made any bus reservations yet!
                            <% } else if (msg.equals("success")) { %>
                                Bus Reservation was successful!
                            <% } else { %>
                                Something went wrong, please try again!
                            <% }
                        } else { %>
                            Welcome to the Bus Reservation page!
                        <% } %>
                    </h3>
                </div>

                <div class="btn-group">
                    <a href="/reservation/add"><button class="btn">Make a Reservation</button></a>
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
