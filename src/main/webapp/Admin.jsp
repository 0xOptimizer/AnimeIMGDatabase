<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" type="image/x-icon" href="favicon.ico">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<link rel="stylesheet" href="assets/css/style.css">
<title>HuSnap</title>
</head>

<body>
<div class="card">
  <img class ="HuLogo" src="HuSnap.png" alt="HuSnap Logo">
  <p class="welcomeText">Welcome, [USER]</p>
  
	<!-- Additional squares -->
  <div class="square-container">
    <c:forEach var="i" begin="1" end="${numOfSquares}">
      <input id="square-input_${i}" class="form-control" type="file" data-square_id="${i}" name="photo" accept="image/*" required style="display:none;">
      <div class="square" data-square_id="${i}">
        <div class="delete-btn" style="display: none;">X</div>
      </div>
    </c:forEach>
  </div>
  
  <div class="btn-container">
  	<div class="btn">
  		<a href="Home.jsp">Log Out</a>
  	</div>
  </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<script src="assets/js/upload_helper.js"></script>
<script>
$(document).ready(function() {
	<c:forEach var="i" begin="1" end="${numOfSquares}">
		setupDropZone('.square[data-square_id=${i}]');
	</c:forEach>
});
</script>
    
</body>
</html>