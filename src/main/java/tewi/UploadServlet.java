package tewi;

import tewi.SquareImage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Blob;
import java.util.Base64;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/api/upload")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 777L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String url = "jdbc:mysql://194.163.35.1:3306/u797587982_husnap";
	    String username = "u797587982_husnap";
	    String password = "tewi^uOWl&c[z62&O";
	    Connection conn = null;
	    List<SquareImage> images = new ArrayList<>(); // to store image objects

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection(url, username, password);

	        String sql = "SELECT image, square_id FROM user_uploads";

	        PreparedStatement pstmt = conn.prepareStatement(sql);

	        ResultSet rs = pstmt.executeQuery();
	        
	        System.out.println(sql);

	        while(rs.next()) {
	            String base64Image = rs.getString("image");
	            int squareId = rs.getInt("square_id");
	            
	            // If base64Image or squareId is null, continue to next iteration
	            if(base64Image == null || rs.wasNull()) {
	                System.out.println("Null image data or squareId found, skipping...");
	                continue;
	            }

	            images.add(new SquareImage(base64Image, squareId)); // add to list
	        }

	        // Convert list to JSON and write to response
	        String json = "["; // manually creating a JSON array
	        for (SquareImage image : images) {
	            // manually creating a JSON object
	            json += "{\"image\":\"" + image.getImage() + "\", \"squareId\":" + image.getSquareId() + "},";
	        }
	        json = json.substring(0, json.length()-1) + "]"; // removing last comma and adding closing bracket

	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(json);
	    } catch(Exception e) {
	        e.printStackTrace();
	    } finally {
	        if(conn != null) {
	            try {
	                System.out.println("Closing database");
	                conn.close();
	            } catch(SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String url = "jdbc:mysql://194.163.35.1:3306/u797587982_husnap";
	    String username = "u797587982_husnap";
	    String password = "tewi^uOWl&c[z62&O";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        // Register JDBC driver
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        // Open a connection
	        conn = DriverManager.getConnection(url, username, password);

	        // Get the square_id from the request
	        String squareId = request.getParameter("square_id");

	        // Check if the record with the given square_id already exists
	        String checkSql = "SELECT COUNT(*) FROM user_uploads WHERE square_id = ?";
	        pstmt = conn.prepareStatement(checkSql);
	        pstmt.setString(1, squareId);
	        rs = pstmt.executeQuery();

	        int count = 0;
	        if (rs.next()) {
	            count = rs.getInt(1);
	        }

	        String sql;
	        if (count == 0) {
	            // If the record does not exist, create SQL insert query
	            sql = "INSERT INTO user_uploads (user_id, image, square_id) values (?, ?, ?)";
	        } else {
	            // If the record exists, create SQL update query
	            sql = "UPDATE user_uploads SET user_id = ?, image = ? WHERE square_id = ?";
	        }

	        // Close the previous statement
	        pstmt.close();

	        // Create PrepareStatement object
	        pstmt = conn.prepareStatement(sql);

	        // Get the user_id somehow, for now it's hardcoded
	        String userId = "example_user_id";

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
	            if (rs != null) {
	                rs.close();
	            }
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