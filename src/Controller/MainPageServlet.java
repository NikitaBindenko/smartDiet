package Controller;

import Model.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.*;

public class MainPageServlet extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        
        request.getRequestDispatcher("front/hello.html").include(request, response);
        
    	}

}
