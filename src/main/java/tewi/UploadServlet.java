package tewi;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet({"/Admin", "/Main"})
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 777L;
	private static final int NUM_OF_SQUARES = 10;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("numOfSquares", NUM_OF_SQUARES);
        String path = request.getServletPath();
        RequestDispatcher dispatcher = request.getRequestDispatcher(path + ".jsp");
        dispatcher.forward(request, response);
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String url = "jdbc:mysql://194.163.35.1:3306/u797587982_husnap";
	    String username = "u797587982_husnap";
	    String password = "tewi^uOWl&c[z62&O";
	    Connection conn = null;

	    try {
	        // Register JDBC driver
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        // Open a connection
	        conn = DriverManager.getConnection(url, username, password);

	        // Create SQL query
	        String sql = "INSERT INTO user_uploads (user_id, image, square_id) values (?, ?, ?)";

	        // Create PrepareStatement object
	        PreparedStatement pstmt = conn.prepareStatement(sql);

	        // Get the user_id somehow, for now it's hardcoded
	        String userId = "example_user_id";

	        // Get the square_id from the request
	        String squareId = request.getParameter("square_id");

	        // This assumes you have an input with name 'file' in your form
	        String image = request.getParameter("image");
	        
	        System.out.println("squareId: " + squareId);
	        System.out.println("image: " + image);

            // Sets the values in the PrepareStatement
            pstmt.setString(1, userId);
            pstmt.setString(2, image);
            pstmt.setString(3, squareId);

            // Executes the SQL statement
            pstmt.executeUpdate();
	    } catch (Exception e) {
	        // handle exception
	        System.out.println("Exception: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        try {
	            if (conn != null) {
	            	System.out.println("Closing database");
	                conn.close();
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	// Helper method to get the file name from the part
	private String getFileName(Part part) {
	    String contentDisp = part.getHeader("content-disposition");
	    String[] items = contentDisp.split(";");
	    for (String s : items) {
	        if (s.trim().startsWith("filename")) {
	            return s.substring(s.indexOf("=") + 2, s.length() - 1);
	        }
	    }
	    return "";
	}
}