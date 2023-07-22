package tewi;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet({"/Admin", "/Main", "/Register"})
public class RoutingServlet extends HttpServlet {
	private static final long serialVersionUID = 3L;
	private static final int NUM_OF_SQUARES = 10;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("numOfSquares", NUM_OF_SQUARES);
        String path = request.getServletPath();
        RequestDispatcher dispatcher = request.getRequestDispatcher(path + ".jsp");
        dispatcher.forward(request, response);
    }
}