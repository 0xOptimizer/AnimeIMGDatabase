package tewi;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/api/*")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 2L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "jdbc:mysql://194.163.35.1:3306/u797587982_husnap";
        String username = "u797587982_husnap";
        String password = "tewi^uOWl&c[z62&O";
        Connection conn = null;
        String action = request.getParameter("action"); // Get the action parameter directly
        String contextPath = request.getContextPath(); // Get the context path

        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection(url, username, password);

            if ("register".equals(action)) {
                String email = request.getParameter("email");
                String regPassword = request.getParameter("password"); // Renamed to regPassword

                // Check if the email is already taken or is the reserved email
                if ("tewi@tewi.club".equals(email) || isEmailTaken(email, conn)) {
                    response.getWriter().write("The email is already taken");
                } else {
                    String message = registerUser(email, regPassword, conn);
                    response.getWriter().write(message); // Send the message as a response
                }
            } else if ("login".equals(action)) {
                String email = request.getParameter("email");
                String loginPassword = request.getParameter("password"); // Renamed to loginPassword
                loginUser(email, loginPassword, conn, response, contextPath); // Pass contextPath to loginUser
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Database error occurred. Please try again later.");
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private String registerUser(String email, String password, Connection conn) throws SQLException {
        // Insert new user into the database
        if (insertUser(email, password, conn)) {
            return "Registration successful";
        } else {
            return "Registration failed";
        }
    }

    private boolean isEmailTaken(String email, Connection conn) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM user_login WHERE user_email = ?")) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private boolean insertUser(String email, String password, Connection conn) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO user_login (user_email, user_password) VALUES (?, ?)")) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            return pstmt.executeUpdate() > 0;
        }
    }

    private void loginUser(String email, String password, Connection conn, HttpServletResponse response, String contextPath) throws SQLException, IOException {
        // Check if the user email and password match the admin credentials
        if ("tewi@tewi.club".equals(email) && "inaba".equals(password)) {
            // Redirect to Admin.jsp for admin users
            response.sendRedirect(contextPath + "/Admin.jsp");
        } else {
            // Check if the user exists in the database
            if (isUserValid(email, password, conn)) {
                // Redirect to Main.jsp for normal users
                response.sendRedirect(contextPath + "/Main.jsp");
            } else {
                // Invalid credentials, show the error message on the login page
                response.getWriter().write("Invalid email or password");
            }
        }
    }

    private boolean isUserValid(String email, String password, Connection conn) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM user_login WHERE user_email = ? AND user_password = ?")) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}