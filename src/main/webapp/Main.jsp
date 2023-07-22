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

<script src="assets/js/jquery-3.7.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<script src="assets/js/upload_helper.js"></script>
<script src="assets/js/animations.js"></script>
<script>
$(document).ready(function() {
	<c:forEach var="i" begin="1" end="${numOfSquares}">
		setupDropZone('.square[data-square_id=${i}]');
	</c:forEach>
	$.get('/AnimeIMGDatabase/api/upload', function(images) {
	    if(images != null) {
	    	console.log('Images found');
	        images.forEach(function(imageObj) {
	        	console.log('Parsing object: ', imageObj);
	            if(imageObj != null && imageObj.image != null) {
	            	console.log("Appending image to " + imageObj.squareId);
	            	console.log(typeof imageObj.squareId);
	                var square = $('.square[data-square_id="' + imageObj.squareId + '"]');
	                console.log(square);
	                var img = document.createElement('img');
	                img.src = imageObj.image; // adjust the mime type if necessary
	                square.append(img);
	                
	             	// Set the CSS properties of the image to fill the drop-zone
	                img.style.width = "100%";
	                img.style.height = "100%";
	                img.style.objectFit = "cover";
	                
	                // Add the drop animation
	                img.style.animation = "dropAnimation 0.4s cubic-bezier(.22,.68,0,1.71)";
	                img.parent().css("overflow", "visible");
	                
	             	// Listen for the end of the animation and then set the overflow back to 'hidden'
	                img.addEventListener('animationend', function() {
	                    img.parent().css("overflow", "hidden");
	                });
	            }
	        });
	    }
	});
});
</script>
    
</body>
</html>