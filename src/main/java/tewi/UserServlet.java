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

@WebServlet("/api/register")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 2L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("request: " + request);
    	
        String url = "jdbc:mysql://194.163.35.1:3306/u797587982_husnap";
        String username = "u797587982_husnap";
        String password = "tewi^uOWl&c[z62&O";
        Connection conn = null;
        
        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection(url, username, password);
        
            String action = request.getParameter("action");

            if (action != null) {
                if (action.equals("register")) {
                    registerUser(request, response, conn);
                } else if (action.equals("login")) {
                    loginUser(request, response, conn);
                }
            }
        } catch (Exception e) {
            // handle exception
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response, Connection conn)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String message;

        // Check if the email is already taken
        if (isEmailTaken(email, conn)) {
            message = "The email has already been taken";
        } else {
            // Insert new user into the database
            if (insertUser(email, password, conn)) {
                message = "Registration successful";
            } else {
                message = "Registration failed";
            }
        }

        request.setAttribute("message", message);
        request.getRequestDispatcher("/Register").forward(request, response);
    }

    private boolean isEmailTaken(String email, Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("SELECT * FROM user_login WHERE user_email = ?");
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private boolean insertUser(String email, String password, Connection conn) {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("INSERT INTO user_login (user_email, user_password) VALUES (?, ?)");
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void loginUser(HttpServletRequest request, HttpServletResponse response, Connection conn)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Check if the user email and password match the admin credentials
        if (email.equals("tewi@tewi.club") && password.equals("inaba")) {
            response.sendRedirect("/Admin");
        } else {
            // Check if the user exists in the database
            if (isUserValid(email, password, conn)) {
                response.sendRedirect("/Main");
            } else {
                String message = "Invalid email or password";
                request.setAttribute("message", message);
                request.getRequestDispatcher("/LogIn").forward(request, response);
            }
        }
    }

    private boolean isUserValid(String email, String password, Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("SELECT * FROM user_login WHERE user_email = ? AND user_password = ?");
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}