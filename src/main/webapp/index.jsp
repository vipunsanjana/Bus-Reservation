<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/assets/css/style.css">
    <title>Login</title>
</head>

<body>
    <nav>
        <div class="container">
            <div class="brand-logo">
                NCG
            </div>
            <div class="nav-items">
                <div class="profile-icon">
                    <img src="/assets/images/user.png" alt="User Icon" width="20px" height="20px">
                </div>
            </div>
        </div>
    </nav>

    <section id="msg-container">
        <div class="container">
            <div class="msg-body">
                <div class="title">
                    <h1>Welcome to BUS Service Reservation to NCG!</h1>
                </div>

                <div class="btn-group">
                    <a href="/login">
                        <button class="btn" aria-label="Login to Bus Service Reservation">Login</button>
                    </a>
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
            &copy; <%= year %> VIPUN
        </div>
    </footer>
</body>

</html>
