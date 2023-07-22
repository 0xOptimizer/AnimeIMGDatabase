<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" type="image/x-icon" href="favicon.ico">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<style>
:root {
  font-size: 20px;
  box-sizing: inherit;
}

*,
*:before,
*:after {
  box-sizing: inherit;
}

p {
  margin: 0;
}

p:not(:last-child) {
  margin-bottom: 1.5em;
}

body {
  font: 1em/1.618 Inter, sans-serif;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  
  min-height: 100vh;
  padding: 30px;
  
  color: #224;
}

body::after {
  content: "";
  background: 
  url(https://wallpaperaccess.com/full/6604090.gif)
    center / cover no-repeat fixed;
  background-size: cover;
  background-repeat: no-repeat;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  position: absolute;
  z-index: -1;   
  transform: scaleX(-1);
}

.btn-container {
  display: flex;
  justify-content: space-between;
}

.card {
  max-width: 300px;
  min-height: 400px;
  right: 500px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  max-width: 500px;
  height: 300px;
  padding: 35px;

  border: 1px solid rgba(255, 255, 255, .25);
  border-radius: 20px;
  background-color: rgba(0, 0, 0, 0.45);
  box-shadow: 0 0 10px 1px rgba(0, 0, 0, 0.25);

  backdrop-filter: blur(15px);
}

.card .btn {
	position: relative;
	top: 0;
	left: 0;
	width: 250px;
	height: 50px;
	margin: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	color: white;
}
.card .btn a {
	position: absolute;
	top: 0;
	left: 0;
	width: 90%;
	height: 100%;
	display: flex;
	justify-content: center;
	align-items: center;
	background: rgba(255, 255, 255, 0.05);
	box-shadow: 0 15px 15px rgba(0, 0, 0, 0.3);
	border-bottom: 1px solid rgba(255, 255, 255, 0.1);
	border-top: 1px solid rgba(255, 255, 255, 0.1);
	border-radius: 30px;
	padding: 10px;
	letter-spacing: 1px;
	text-decoration: none;
	overflow: hidden;
	color: #fff;
	font-weight: 400px;
	z-index: 1;
	transition: 0.5s;
	backdrop-filter: blur(15px);
}
.card .btn:hover a {
	letter-spacing: 3px;
}
.card .btn a::before {
	content: "";
	position: absolute;
	top: 0;
	left: 0;
	width: 50%;
	height: 100%;
	background: linear-gradient(to left, rgba(255, 255, 255, 0.15), transparent);
	transform: skewX(45deg) translate(0);
	transition: 0.5s;
	filter: blur(0px);
}
.card .btn:hover a::before {
	transform: skewX(45deg) translate(200px);
}
.card .btn::before {
	content: "";
	position: absolute;
	left: 50%;
	transform: translatex(-50%);
	bottom: -25px;
	width: 30px;
	height: 10px;
	background: #f00;
	border-radius: 10px;
	transition: 0.5s;
	transition-delay: 0.5;
}
.card .btn:hover::before /*lightup button*/ {
	bottom: 0;
	height: 100%;
	width: 100%;
	border-radius: 30px;
}

.card .btn::after {
	content: "";
	position: absolute;
	overflow: hidden;
	left: 50%;
	transform: translatex(-50%);
	top: -5px;
	width: 30px;
	height: 10px;
	background: #f00;
	border-radius: 10px;
	transition: 0.5s;
	transition-delay: 0.5;
}
.card .btn:hover::after /*lightup button*/ {
	top: 35px;
	height: 10%;
	width: 10%;
	border-radius: 30px;
}
.card .btn:nth-child(1)::before, /*chnage 1*/
.card .btn:nth-child(1)::after {
	background: #ff1f71;
	box-shadow: 0 0 5px #ff1f71, 0 0 15px #ff1f71, 0 0 30px #ff1f71,
		0 0 60px #ff1f71;
}

.welcomeText {
	text-align: center;
	font-size: 30px;
}

p {
	color: white;
}

label {
	color: white;
}

input{
    display: block;
    height: 50px;
    width: 80%;
    background-color: rgba(255,255,255,0.07);
    border-radius: 3px;
    padding: 0 10px;
    margin-left: 10px;
    margin-top: 8px;
    font-size: 14px;
    font-weight: 300;
    color: white;
}
::placeholder{
    color: #e5e5e5;
}


/* .card-footer {
  font-size: 0.65em;
  color: #446;
} */
</style>
    <title>HuSnap Log In</title>
</head>
<body>
    <div class="card">
        <p class="welcomeText"><strong>Log In</strong></p>
        <form action="UserServlet" method="post">
            <label for="email"><strong>Email:</strong></label>
            <input type="email" id="email" name="email" required placeholder="Email">
            <br>
            <label for="password"><strong>Password:</strong></label>
            <input type="password" id="password" name="password" required placeholder="Password">
            <br>
            <input type="hidden" name="action" value="login">
            <div class="btn-container">
                <button type="submit">Log In</button>
            </div>
        </form>
        <p><%= request.getAttribute("message") %></p>
    </div>
    <!-- Add any other content or elements as needed -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>