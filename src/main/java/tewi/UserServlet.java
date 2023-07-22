package tewi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
    private static final String url = "jdbc:mysql://194.163.35.1:3306/u797587982_husnap";
    private static final String username = "u797587982_husnap";
    private static final String password = "tewi^uOWl&c[z62&O";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            if (action.equals("register")) {
                registerUser(request, response);
            } else if (action.equals("login")) {
                loginUser(request, response);
            }
        }
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String message;

        // Check if the email is already taken
        if (email.equals("tewi@tewi.club") || isEmailTaken(email)) {
            message = "The email has already been taken";
        } else {
            // Insert new user into the database
            if (insertUser(email, password)) {
                message = "Registration successful";
            } else {
                message = "Registration failed";
            }
        }

        request.setAttribute("message", message);
        request.getRequestDispatcher("Register.jsp").forward(request, response);
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Check if the user email and password match the admin credentials
        if (email.equals("tewi@tewi.club") && password.equals("inaba")) {
            response.sendRedirect("Admin.jsp");
        } else {
            // Check if the user exists in the database
            if (isUserValid(email, password)) {
                response.sendRedirect("Main.jsp");
            } else {
                String message = "Invalid email or password";
                request.setAttribute("message", message);
                request.getRequestDispatcher("LogIn.jsp").forward(request, response);
            }
        }
    }

    private boolean isEmailTaken(String email) {
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM user_login WHERE user_email = ?");
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            boolean result = rs.next();
            rs.close();
            pstmt.close();
            conn.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean insertUser(String email, String password) {
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO user_login (user_email, user_password) VALUES (?, ?)");
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isUserValid(String email, String password) {
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM user_login WHERE user_email = ? AND user_password = ?");
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            boolean result = rs.next();
            rs.close();
            pstmt.close();
            conn.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}