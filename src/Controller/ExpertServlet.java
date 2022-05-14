package Controller;

import Model.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class ExpertServlet extends HttpServlet {
    
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {
        response.setContentType("text/html");
        request.getRequestDispatcher("front/expert.html").include(request, response);
    }
}
