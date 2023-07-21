<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
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

  display: flex;
  align-items: center;
  justify-content: center;
  
  min-height: 100vh;
  padding: 30px;
  
  color: #224;
  background:
    url(https://source.unsplash.com/E8Ufcyxz514/2400x1823)
    center / cover no-repeat fixed;
}

.card {
  max-width: 300px;
  min-height: 200px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  max-width: 500px;
  height: 300px;
  padding: 35px;

  border: 1px solid rgba(255, 255, 255, .25);
  border-radius: 20px;
  background-color: rgba(255, 255, 255, 0.45);
  box-shadow: 0 0 10px 1px rgba(0, 0, 0, 0.25);

  backdrop-filter: blur(15px);
}

.card-footer {
  font-size: 0.65em;
  color: #446;
}
</style>
    <title>Anime IMG Database</title>
    <!-- Add your CSS stylesheets and any other necessary external resources here -->
</head>
<body>
    <div class="card">
  <p>A glass-like card to demonstrate the <strong>Glassmorphism UI design</strong> trend.</p>
  <p class="card-footer">Lorem Ipsum</p>
</div>
    <!-- Add any other content or elements as needed -->
</body>
</html>