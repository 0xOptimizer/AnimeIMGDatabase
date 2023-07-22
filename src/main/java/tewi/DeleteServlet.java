package tewi;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/api/delete")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 123L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String url = "jdbc:mysql://194.163.35.1:3306/u797587982_husnap";
	    String username = "u797587982_husnap";
	    String password = "tewi^uOWl&c[z62&O";
	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        // Register JDBC driver
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        // Open a connection
	        conn = DriverManager.getConnection(url, username, password);

	        // Get the square_id from the request
	        String squareId = request.getParameter("square_id");

	        // Create SQL delete query
	        String sql = "DELETE FROM user_uploads WHERE square_id = ?";

	        // Create PrepareStatement object
	        pstmt = conn.prepareStatement(sql);

	        // Set the value in the PreparedStatement
	        pstmt.setString(1, squareId);

	        // Executes the SQL statement
	        pstmt.executeUpdate();
	    } catch (Exception e) {
	        // handle exception
	        System.out.println("Exception: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstmt != null) {
	                pstmt.close();
	            }
	            if (conn != null) {
	                System.out.println("Closing database");
	                conn.close();
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	}
}