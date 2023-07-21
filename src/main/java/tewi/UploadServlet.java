package tewi;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/Admin") // URL pattern
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 777L;
	private static final int NUM_OF_SQUARES = 10;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("numOfSquares", NUM_OF_SQUARES);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Admin.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}