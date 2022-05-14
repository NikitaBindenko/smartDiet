package Controller;

import Model.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class QuestionServlet extends HttpServlet {
    
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {
        response.setContentType("text/html");
        request.getRequestDispatcher("front/questionnaire.html").include(request, response);
    }
}
